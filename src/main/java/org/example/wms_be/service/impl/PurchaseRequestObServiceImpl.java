package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.constant.PurchaseRequestConst;
import org.example.wms_be.converter.PurchaseDetailsObConverter;
import org.example.wms_be.converter.PurchaseRequestObConverter;
import org.example.wms_be.data.request.PurchaseRequestDetailsObReq;
import org.example.wms_be.data.request.PurchaseRequestObReq;
import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;
import org.example.wms_be.data.response.PurchaseRequestObResp;
import org.example.wms_be.entity.outbound.PurchaseRequestDetailsOb;
import org.example.wms_be.entity.outbound.PurchaseRequestOb;
import org.example.wms_be.constant.Status;
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
            return processPurchaseRequestList(purchaseRequestObResps);
        } catch (Exception e) {
            logger.error("Get all purchase requests failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<PurchaseRequestObResp> getPurchaseRequestObByMaPR(String maPR) {
        try {
            List<PurchaseRequestObResp> purchaseRequestObResps = purchaseRequestObMapper.getAllPurchaseRequestObByMaPR(maPR);
            return processPurchaseRequestList(purchaseRequestObResps);
        } catch (Exception e) {
            logger.error("Get purchase requests by MaPR failed", e);
            return Collections.emptyList();
        }
    }

    private List<PurchaseRequestObResp> processPurchaseRequestList(List<PurchaseRequestObResp> purchaseRequestObResps) {
        purchaseRequestObResps.stream()
                .filter(Objects::nonNull)
                .filter(pr -> pr.getMaPR() != null)
                .forEach(pr -> {
                    if (pr.getNgayYeuCau() != null) {
                        // convert ngayYeuCau from yyyy-MM-dd HH:mm:ss to dd/MM/yyyy
                        String ngayYeuCauFormatted = TimeConverter.formatNgayYeuCau(Timestamp.valueOf(pr.getNgayYeuCau()));
                        pr.setNgayYeuCau(ngayYeuCauFormatted);
                    }
                    List<PurchaseRequestDetailsObResp> chiTietXuatHang = purchaseDetailsObMapper.layDanhSachXuatHangTheoMaPR(pr.getMaPR());
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
    }

    @Override
    @Transactional
    public void savePurchaseRequestOb(PurchaseRequestObReq purchaseRequestObReq) {
        //convert sang entity
        PurchaseRequestOb purchaseRequestOb = purchaseRequestObConverter.toPurchaseRequestObReq(purchaseRequestObReq);
        if (purchaseRequestObMapper.existById(purchaseRequestObReq.getSysIdYeuCauXuatHang())) {
            purchaseRequestObMapper.updatePurchaseRequestOb(purchaseRequestOb);
        } else {
            purchaseRequestObMapper.insertPurchaseRequestOb(purchaseRequestOb);
        }
        // Lấy mã PR
        String maPR = purchaseRequestObMapper.getMaPRById(purchaseRequestOb.getSysIdYeuCauXuatHang());
        purchaseRequestOb.setMaPR(maPR);
        logger.info("Inserted new PurchaseRequestOb: {} with MaPR: {}", purchaseRequestOb, maPR);
        // Lưu chi tiết xuất hàng
        for (PurchaseRequestDetailsObReq purchaseRequestDetailsObReq : purchaseRequestObReq.getChiTietXuatHang()) {
            // Convert sang entity
            PurchaseRequestDetailsOb purchaseRequestDetailsOb = purchaseDetailsObConverter.toPurchaseRequestDeatilsOb(purchaseRequestDetailsObReq);
            String ngayXuatDuKien = purchaseRequestDetailsOb.getNgayXuatDuKien();
            if (ngayXuatDuKien != null && !ngayXuatDuKien.isEmpty()) {
                try {
                    // Convert ngayXuatDuKien from dd/MM/yyyy to yyyy-MM-dd
                    String ngayXuatDuKienDbFormat = TimeConverter.toDbFormat(TimeConverter.parseDateFromDisplayFormat(ngayXuatDuKien));
                    purchaseRequestDetailsOb.setNgayXuatDuKien(ngayXuatDuKienDbFormat);
                    logger.info("NgayXuatDuKien: {} -> {}", ngayXuatDuKien, ngayXuatDuKienDbFormat);
                } catch (IllegalArgumentException e) {
                    logger.error("Invalid date format: {}", ngayXuatDuKien);
                }
            }
            if (purchaseDetailsObMapper.existById(purchaseRequestDetailsObReq.getSysIdChiTietXuatHang())) {
                purchaseDetailsObMapper.updatePurchaseRequestDetailsOb(purchaseRequestDetailsOb);
            } else {
                purchaseRequestDetailsOb.setMaPR(purchaseRequestOb.getMaPR());
                purchaseDetailsObMapper.insertPurchaseRequestDetailsOb(purchaseRequestDetailsOb);
            }
        }
        // Gửi email
        if ("approving".equals(purchaseRequestObReq.getTrangThai())) {
            sendMailForApprove(purchaseRequestOb);
        } else if ("confirm".equals(purchaseRequestObReq.getTrangThai())) {
            sendMailForConfirm(purchaseRequestOb);
        } else if ("reject".equals(purchaseRequestObReq.getTrangThai())) {
            sendMailForReject(purchaseRequestOb);
        }else {
            logger.info("failed to send email");
        }
    }

    private void sendEmail(String emailToSend, PurchaseRequestOb purchaseRequestOb) {
        if (emailToSend == null || emailToSend.isEmpty()) {
            logger.error("Email to send is empty");
            return;
        }

        String nguoiTao = PurchaseRequestConst.DEFAULT_USER_REQUESTING;
        String chucVu = PurchaseRequestConst.DEFAULT_ROLE;
        String daiDienPo = PurchaseRequestConst.DEFAULT_FULL_NAME;
        try {
            Map<String, String> thongTinEmail = userMapper.getEmailByRoles(emailToSend);
            logger.info("Get info from getEmailByRoles: {}", thongTinEmail);
            if (thongTinEmail != null) {
                daiDienPo = thongTinEmail.getOrDefault("fullName", PurchaseRequestConst.DEFAULT_FULL_NAME);
                chucVu = thongTinEmail.getOrDefault("role", PurchaseRequestConst.DEFAULT_ROLE);
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

        String status = Objects.requireNonNull(purchaseRequestOb).getTrangThai();
        String title ;
        String requestInfor ;
        String body ;
        String reason = (purchaseRequestOb.getLyDo());
        List<PurchaseRequestDetailsObResp> chiTietXuatHang = purchaseDetailsObMapper.layDanhSachXuatHangTheoMaPR(purchaseRequestOb.getMaPR());
        chiTietXuatHang.forEach(detail -> {
            if (detail.getNgayXuatDuKien() != null) {
                String ngayXuatDuKienFormatted = TimeConverter.formatNgayXuat(TimeConverter.parseDateOnly(detail.getNgayXuatDuKien()));
                detail.setNgayXuatDuKien(ngayXuatDuKienFormatted);
            }
        });
        String detailsObTable = TplEmailPrOb.bangYeuCauXuatHang(chiTietXuatHang);

        switch (status) {
            case Status.CONFIRM:
                title = TplEmailPrOb.emailTitleConfirm(purchaseRequestOb.getMaPR(), purchaseRequestOb.getTrangThai());
                body = TplEmailPrOb.emailBodyConfirm(title, detailsObTable);
                break;
            case Status.APPROVING:
                title = TplEmailPrOb.emailTitle(purchaseRequestOb.getMaPR(), purchaseRequestOb.getTrangThai());
                requestInfor = TplEmailPrOb.requestInfor(nguoiTao, daiDienPo, chucVu);
                body = TplEmailPrOb.emailBody(title, requestInfor, detailsObTable);
                break;
            case Status.REJECT:
                title = TplEmailPrOb.emailTitleReject(purchaseRequestOb.getMaPR(), purchaseRequestOb.getTrangThai());
                body = TplEmailPrOb.emailBodyReject(title,detailsObTable,reason);
                break;
            default:
                logger.error("Trạng thái không hợp lệ: {}", purchaseRequestOb.getTrangThai());
                return;
        }
        String subject = "Thông báo yêu cầu: phê duyệt yêu cầu mua hàng";
        // emailToSend
        try {
            emailUtil.sendEmail(emailToSend, subject, body);
            logger.info("Đã gửi email đến: {}", emailToSend);
        } catch (Exception e) {
            logger.error("Lỗi khi gửi email đến {}: {}", emailToSend, e.getMessage());
        }
    }
    private void sendMailForApprove(PurchaseRequestOb purchaseRequestOb) {
        if (!userMapper.checkMailExits(PurchaseRequestConst.DEFAULT_PO_EMAIL)) {
            logger.error("Email to send is not exist");
            return;
        }
        String emailToSend = PurchaseRequestConst.DEFAULT_PO_EMAIL;
        sendEmail(emailToSend, purchaseRequestOb);
    }
    private void sendMailForConfirm(PurchaseRequestOb purchaseRequestOb) {
        String emailToSend = PurchaseRequestConst.DEFAULT_PR_EMAIL;
        sendEmail(emailToSend, purchaseRequestOb);
    }
    private void sendMailForReject(PurchaseRequestOb purchaseRequestOb) {
        String emailToSend = PurchaseRequestConst.DEFAULT_PR_EMAIL;
        sendEmail(emailToSend, purchaseRequestOb);
    }
}