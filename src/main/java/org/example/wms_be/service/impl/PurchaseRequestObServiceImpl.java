package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.constant.PrConst;
import org.example.wms_be.converter.PurchaseDetailsObConverter;
import org.example.wms_be.converter.PurchaseRequestObConverter;
import org.example.wms_be.data.request.PurchaseRequestDetailsObReq;
import org.example.wms_be.data.request.PurchaseRequestObReq;
import org.example.wms_be.data.response.PurchaseRequestDetailsResp;
import org.example.wms_be.data.response.PurchaseRequestObResp;
import org.example.wms_be.entity.purchase.PurchaseRequestDetailsOb;
import org.example.wms_be.entity.purchase.PurchaseRequestOb;
import org.example.wms_be.mapper.account.UserMapper;
import org.example.wms_be.mapper.purchase.PurchaseDetailsObMapper;
import org.example.wms_be.mapper.purchase.PurchaseRequestObMapper;
import org.example.wms_be.service.PurchaseRequestObService;
import org.example.wms_be.utils.EmailUtil;
import org.example.wms_be.utils.TimeConverter;
import org.example.wms_be.utils.TplEmailPrOb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseRequestObServiceImpl implements PurchaseRequestObService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseRequestObServiceImpl.class);
    private final PurchaseRequestObConverter purchaseRequestObConverter;
    private final PurchaseDetailsObConverter purchaseDetailsObConverter;
    private final PurchaseRequestObMapper purchaseRequestObMapper;
    private final PurchaseDetailsObMapper purchaseDetailsObMapper;
    private final UserMapper userMapper;
    private final EmailUtil emailUtil;

    @Override
    public List<PurchaseRequestObResp> getAllPurchaseRequestOb() {
        try {
            List<PurchaseRequestObResp> purchaseRequestObResps = purchaseRequestObMapper.getAllPurchaseRequestOb();
            purchaseRequestObResps.stream().filter(Objects::nonNull).filter(pr -> pr.getMaPR() != null).forEach(pr -> {
                if (pr.getNgayYeuCau() != null) {
                    // convert ngayYeuCau from yyyy-MM-dd HH:mm:ss to dd/MM/yyyy
                    String ngayYeuCauFormatted = TimeConverter.formatNgayYeuCau(Timestamp.valueOf(pr.getNgayYeuCau()));
                    pr.setNgayYeuCau(ngayYeuCauFormatted);
                }
                List<PurchaseRequestDetailsResp> chiTietXuatHang = purchaseDetailsObMapper.layDanhSachXuatHangTheoMaPR(pr.getMaPR());
                logger.info("Found {} details for PurchaseRequest: {}", chiTietXuatHang.size(), pr.getMaPR());
                chiTietXuatHang.forEach(detail -> {
                    if (detail.getNgayXuatDuKien() != null) {
                        String ngayXuatDuKienFormatted = TimeConverter.formatNgayXuat(TimeConverter.parseDateOnly(detail.getNgayXuatDuKien()));
                        detail.setNgayXuatDuKien(ngayXuatDuKienFormatted);
                    }
                });
                pr.setChiTietXuatHang(chiTietXuatHang);
            });
            return purchaseRequestObResps;
        } catch (Exception e) {
            logger.error("Get all purchase requests failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public void savePurchaseRequestOb(PurchaseRequestObReq purchaseRequestObReq) {
        try {
            // Convert sang entity
            PurchaseRequestOb purchaseRequestOb = purchaseRequestObConverter.toPurchaseRequestObReq(purchaseRequestObReq);
            if (purchaseRequestObMapper.existById(purchaseRequestObReq.getSysIdYeuCauXuatHang())) {
                purchaseRequestObMapper.updatePurchaseRequestOb(purchaseRequestOb);
            } else {
                purchaseRequestObMapper.insertPurchaseRequestOb(purchaseRequestOb);
            }
            for (PurchaseRequestDetailsObReq purchaseRequestDetailsObReq : purchaseRequestObReq.getChiTietXuatHang()) {
                // Chuyển đổi chi tiết yêu cầu
                PurchaseRequestDetailsOb purchaseRequestDetailsOb = purchaseDetailsObConverter.toPurchaseRequestDeatilsOb(purchaseRequestDetailsObReq);
                String ngayXuatDuKien = purchaseRequestDetailsOb.getNgayXuatDuKien();
                if (ngayXuatDuKien != null && !ngayXuatDuKien.isEmpty()) {
                    try {
                        // convert ngayXuatDuKien from dd/MM/yyyy to yyyy-MM-dd
                        String ngayXuatDuKienDbFormat = TimeConverter.toDbFormat(TimeConverter.parseDateFromDisplayFormat(ngayXuatDuKien));
                        purchaseRequestDetailsOb.setNgayXuatDuKien(ngayXuatDuKienDbFormat);
                        logger.info("NgayXuatDuKien: {} -> {}", ngayXuatDuKien, ngayXuatDuKienDbFormat);
                    } catch (IllegalArgumentException e) {
                        logger.error("Invalid date format: {}", ngayXuatDuKien);
                    }
                }
                //  cập nhật hoặc thêm mới
                if (purchaseDetailsObMapper.existById(purchaseRequestDetailsObReq.getSysIdChiTietXuatHang())) {
                    purchaseDetailsObMapper.updatePurchaseRequestDetailsOb(purchaseRequestDetailsOb);
                } else {
                    purchaseRequestDetailsOb.setMaPR(purchaseRequestOb.getMaPR());
                    purchaseDetailsObMapper.insertPurchaseRequestDetailsOb(purchaseRequestDetailsOb);
                }
            }
            if (purchaseRequestObReq.getSysIdYeuCauXuatHang() == null) {
                sendMailForInsert(purchaseRequestOb);
            } else {
                sendMailForUpdate(purchaseRequestOb);
            }
        } catch (Exception e) {
            logger.error("Save purchase request failed", e);
        }
    }

    private void sendEmail(String emailToSend, PurchaseRequestOb purchaseRequestOb, boolean isUpdate) {
        if (emailToSend == null || emailToSend.isEmpty()) {
            logger.error("Email to send is empty");
            return;
        }
        if (!userMapper.checkMailExits(emailToSend)) {
            logger.error("Email to send is not exist");
            return;
        }
        String nguoiTao = PrConst.DEFAULT_USER_REQUESTING;
        String chucVu = PrConst.DEFAULT_ROLE;
        String daiDienPo = PrConst.DEFAULT_FULL_NAME;
        try {
            Map<String, String> thongTinEmail = userMapper.getEmailByRoles(emailToSend);
            logger.info("Get info from getEmailByRoles: {}", thongTinEmail);
            if (thongTinEmail != null) {
                daiDienPo = thongTinEmail.getOrDefault("fullName", PrConst.DEFAULT_FULL_NAME);
                chucVu = thongTinEmail.getOrDefault("role", PrConst.DEFAULT_ROLE);
            }
        } catch (Exception e) {
            logger.error("Error when get user info", e);
        }
        if (purchaseRequestOb != null) {
            try {
                nguoiTao = userMapper.getFullNameByRoles(purchaseRequestOb.getNguoiYeuCau());
            } catch (Exception e) {
                logger.error("Error when get user info", e);
            }
        }
        String title = Optional
                .ofNullable(purchaseRequestOb)
                .map(ob -> isUpdate ? TplEmailPrOb.emailTitleUpdate(ob.getMaPR()) : TplEmailPrOb.emailTitle(ob.getMaPR()))
                .orElse("Default Title");
        String requestInfor = isUpdate ? TplEmailPrOb.updateInFor(nguoiTao) : TplEmailPrOb.requestInfor(nguoiTao, daiDienPo, chucVu);
        List<PurchaseRequestDetailsResp> chiTietXuatHang = Optional
                .ofNullable(purchaseRequestOb)
                .map(ob -> purchaseDetailsObMapper.layDanhSachXuatHangTheoMaPR(ob.getMaPR()))
                .orElseGet(ArrayList::new);
        String detailsObTable = TplEmailPrOb.bangYeuCauXuatHang(chiTietXuatHang);
        String body = isUpdate ? TplEmailPrOb.emailBodyForUpdate(title, requestInfor, detailsObTable) : TplEmailPrOb.emailBody(title, requestInfor, detailsObTable);
        String subject = isUpdate ? "Thông báo chỉnh sửa yêu cầu: từ phòng purchase request" : "Thông báo yêu cầu: phê duyệt yêu cầu mua hàng";
        try {
            emailUtil.sendEmail(emailToSend, subject, body);
            logger.info("Đã gửi email đến: {}", emailToSend);
        } catch (Exception e) {
            logger.error("Lỗi khi gửi email đến {}: {}", emailToSend, e.getMessage());
        }
    }

    private void sendMailForInsert(PurchaseRequestOb purchaseRequestOb) {
        String emailToSend = PrConst.DEFAULT_PO_EMAIL;
        sendEmail(emailToSend, purchaseRequestOb, false);
    }

    private void sendMailForUpdate(PurchaseRequestOb purchaseRequestOb) {
        String emailToSend = PrConst.DEFAULT_PO_EMAIL;
        sendEmail(emailToSend, purchaseRequestOb, true);
    }
}