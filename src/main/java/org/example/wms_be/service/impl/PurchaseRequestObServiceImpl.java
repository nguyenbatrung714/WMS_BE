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
        PurchaseRequestOb purchaseRequestOb = saveRequest(purchaseRequestObReq);
        String maPR = layMaPR(purchaseRequestOb);
        purchaseRequestOb.setMaPR(maPR);
        logger.info("them ma pr moi: {} with MaPR: {}", purchaseRequestOb, maPR);
        saveRequestDetails(purchaseRequestObReq, maPR);
        handleEmailNotification(purchaseRequestObReq.getTrangThai(), purchaseRequestOb);
    }

    private PurchaseRequestOb saveRequest(PurchaseRequestObReq purchaseRequestObReq) {
        PurchaseRequestOb purchaseRequestOb = purchaseRequestObConverter.toPurchaseRequestObReq(purchaseRequestObReq);
        if (purchaseRequestObMapper.existById(purchaseRequestObReq.getSysIdYeuCauXuatHang())) {
            purchaseRequestObMapper.updatePurchaseRequestOb(purchaseRequestOb);
        } else {
            purchaseRequestObMapper.insertPurchaseRequestOb(purchaseRequestOb);
        }
        return purchaseRequestOb;
    }

    private String layMaPR(PurchaseRequestOb purchaseRequestOb) {
        return purchaseRequestObMapper.getMaPRById(purchaseRequestOb.getSysIdYeuCauXuatHang());
    }

    private void saveRequestDetails(PurchaseRequestObReq purchaseRequestObReq, String maPR) {
        for (PurchaseRequestDetailsObReq detailsReq : purchaseRequestObReq.getChiTietXuatHang()) {
            PurchaseRequestDetailsOb details = purchaseDetailsObConverter.toPurchaseRequestDeatilsOb(detailsReq);
            ngayXuatDuKien(details);
            if (purchaseDetailsObMapper.existById(detailsReq.getSysIdChiTietXuatHang())) {
                purchaseDetailsObMapper.updatePurchaseRequestDetailsOb(details);
            } else {
                details.setMaPR(maPR);
                purchaseDetailsObMapper.insertPurchaseRequestDetailsOb(details);
            }
        }
    }

    private void ngayXuatDuKien(PurchaseRequestDetailsOb details) {
        String ngayXuatDuKien = details.getNgayXuatDuKien();
        if (ngayXuatDuKien != null && !ngayXuatDuKien.isEmpty()) {
            try {
                String dbFormatDate = TimeConverter.toDbFormat(TimeConverter.parseDateFromDisplayFormat(ngayXuatDuKien));
                details.setNgayXuatDuKien(dbFormatDate);
                logger.info("NgayXuatDuKien: {} -> {}", ngayXuatDuKien, dbFormatDate);
            } catch (IllegalArgumentException e) {
                logger.error("khong dung format: {}", ngayXuatDuKien);
            }
        }
    }
    private void handleEmailNotification(String trangThai, PurchaseRequestOb purchaseRequestOb) {
        if (trangThai == null) {
            logger.warn("khong the gui mail vi trang thai null");
            return;
        }
        switch (trangThai) {
            case "approving" -> sendMailForApprove(purchaseRequestOb);
            case "confirm" -> sendMailForConfirm(purchaseRequestOb);
            case "reject" -> sendMailForReject(purchaseRequestOb);
            default -> logger.info("Failed to send email");
        }
    }

    private void sendEmail(String emailToSend, PurchaseRequestOb purchaseRequestOb) {
        if (emailToSend == null || emailToSend.isEmpty()) {
            logger.error("Email to send is empty");
            return;
        }
        if (!userMapper.checkMailExits(emailToSend)) {
            logger.error("Email to send is not exist");
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
        String title;
        String requestInfor;
        String body;
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
                body = TplEmailPrOb.emailBodyReject(title, detailsObTable, reason);
                break;
            default:
                logger.error("Trạng thái không hợp lệ: {}", purchaseRequestOb.getTrangThai());
                return;
        }
        String subject = "Thông báo yêu cầu: phê duyệt yêu cầu xuất hàng";
        // emailToSend
        try {
            emailUtil.sendEmail(emailToSend, subject, body);
            logger.info("Đã gửi email đến: {}", emailToSend);
        } catch (Exception e) {
            logger.error("Lỗi khi gửi email đến {}: {}", emailToSend, e.getMessage());
        }
    }
    public void sendMailForApprove(PurchaseRequestOb purchaseRequestOb) {
        sendEmail(PurchaseRequestConst.DEFAULT_PO_EMAIL, purchaseRequestOb);
    }

    public void sendMailForConfirm(PurchaseRequestOb purchaseRequestOb) {
        sendEmail(PurchaseRequestConst.DEFAULT_PR_EMAIL, purchaseRequestOb);
    }

    public void sendMailForReject(PurchaseRequestOb purchaseRequestOb) {
        sendEmail(PurchaseRequestConst.DEFAULT_PR_EMAIL, purchaseRequestOb);
    }
}
