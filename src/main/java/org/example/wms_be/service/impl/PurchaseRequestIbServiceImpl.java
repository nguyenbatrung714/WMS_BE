package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.constant.PurchaseRequestConst;
import org.example.wms_be.constant.Status;
import org.example.wms_be.converter.inbound.PurchaseDetailsIbConverter;
import org.example.wms_be.converter.inbound.PurchaseRequestIbConverter;
import org.example.wms_be.data.request.PurchaseDetailsIbReq;
import org.example.wms_be.data.request.PurchaseRequestIbReq;
import org.example.wms_be.data.response.inbound.PurchaseRequestDetailsIbResp;
import org.example.wms_be.data.response.inbound.PurchaseRequestIbResp;
import org.example.wms_be.entity.inbound.PurchaseDetailsIb;
import org.example.wms_be.entity.inbound.PurchaseRequestIb;
import org.example.wms_be.mapper.account.UserMapper;
import org.example.wms_be.mapper.inbound.PurchaseDetailsIbMapper;
import org.example.wms_be.mapper.inbound.PurchaseRequestIbMapper;
import org.example.wms_be.service.PurchaseRequestIbService;
import org.example.wms_be.utils.EmailUtil;
import org.example.wms_be.utils.TimeConverter;
import org.example.wms_be.utils.TplEmailPR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.*;


@Service
@RequiredArgsConstructor
public class PurchaseRequestIbServiceImpl implements PurchaseRequestIbService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseRequestIbServiceImpl.class);
    private final PurchaseDetailsIbMapper purchaseDetailsIbMapper;
    private final PurchaseRequestIbConverter purchaseRequestIbConverter;
    private final PurchaseDetailsIbConverter purchaseDetailsIbConverter;
    private final UserMapper userMapper;
    private final EmailUtil emailUtil;
    private final PurchaseRequestIbMapper purchaseRequestIbMapper;


    @Override
    public List<PurchaseRequestIbResp> getAllPurchaseRequestIb() {
        try {
            List<PurchaseRequestIbResp> purchaseRequestIbResps = purchaseRequestIbMapper.getAllPurchaseRequestIb();
            return processPurchaseRequestList(purchaseRequestIbResps);
        } catch (Exception e) {
            logger.error("Get all purchase requests failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<PurchaseRequestIbResp> getPurchaseRequestIbByMaPR(String maPR) {
        try {
            List<PurchaseRequestIbResp> purchaseRequestIbResps = purchaseRequestIbMapper.getAllPurchaseRequestIbByMaPR(maPR);
            return processPurchaseRequestList(purchaseRequestIbResps);
        } catch (Exception e) {
            logger.error("Get purchase requests by MaPR failed", e);
            return Collections.emptyList();
        }
    }


    private List<PurchaseRequestIbResp> processPurchaseRequestList(List<PurchaseRequestIbResp> purchaseRequestIbResps) {
        purchaseRequestIbResps.stream()
                .filter(Objects::nonNull)
                .filter(pr -> pr.getMaPR() != null)
                .forEach(pr -> {
                    if (pr.getNgayYeuCau() != null) {
                        // convert ngayYeuCau from yyyy-MM-dd HH:mm:ss to dd/MM/yyyy
                        String ngayYeuCauFormatted = TimeConverter.formatNgayYeuCau(Timestamp.valueOf(pr.getNgayYeuCau()));
                        pr.setNgayYeuCau(ngayYeuCauFormatted);
                    }
                    List<PurchaseRequestDetailsIbResp> chiTietNhapHang = purchaseDetailsIbMapper.getPurchaseRequestById(pr.getMaPR());
                    logger.info("Found {} details for PurchaseRequest: {}", chiTietNhapHang.size(), pr.getMaPR());
                    chiTietNhapHang.forEach(detail -> {
                        if (detail.getNgayNhapDuKien() != null) {
                            String ngayNhapDuKienFormatted = TimeConverter.formatNgayNhap(TimeConverter.parseDateOnly(detail.getNgayNhapDuKien()));
                            detail.setNgayNhapDuKien(ngayNhapDuKienFormatted);
                        }
                    });
                    pr.setChiTietNhapHang(chiTietNhapHang);
                });
        return purchaseRequestIbResps;
    }

    @Override
    @Transactional
    public void savePurchaseRequestWithDetails(PurchaseRequestIbReq purchaseRequestIbReq) {
        PurchaseRequestIb purchaseRequestIb = saveRequestIb(purchaseRequestIbReq);
        String maPR = layMaPR(purchaseRequestIb);
        logger.info("them ma pr moi: {} with MaPR: {}", purchaseRequestIb, maPR);
        saveRequestDetails(purchaseRequestIbReq, maPR);
        handleEmailNotification(purchaseRequestIbReq.getTrangThai(), purchaseRequestIb);
    }

    private void handleEmailNotification(String trangThai, PurchaseRequestIb purchaseRequestIb) {
        if (trangThai == null) {
            logger.warn("khong the gui mail vi trang thai null");
            return;
        }
        switch (trangThai) {
            case "approving" -> sendMailForApprove(purchaseRequestIb);
            case "confirm" -> sendMailForConfirm(purchaseRequestIb);
            case "reject" -> sendMailForReject(purchaseRequestIb);
            default -> logger.info("Failed to send email");
        }
    }

    private void saveRequestDetails(PurchaseRequestIbReq purchaseRequestIbReq, String maPR) {
        for (PurchaseDetailsIbReq detailsReq : purchaseRequestIbReq.getChiTietNhapHang()) {
            PurchaseDetailsIb details = purchaseDetailsIbConverter.toPurchaseDetailsIb(detailsReq);
            ngayNhapDuKien(details);
            if (purchaseDetailsIbMapper.existById(detailsReq.getSysIdChiTietNhapHang())) {
                purchaseDetailsIbMapper.updatePurchaseDetails(details);
            } else {
                details.setMaPR(maPR);
                purchaseDetailsIbMapper.insertPurchaseDetails(details);
            }
        }
    }

    private void ngayNhapDuKien(PurchaseDetailsIb details) {
        String ngayNhapDuKien = details.getNgayNhapDuKien();
        if (ngayNhapDuKien != null && !ngayNhapDuKien.isEmpty()) {
            try {
                String dbFormatDate = TimeConverter.toDbFormat(TimeConverter.parseDateFromDisplayFormat(ngayNhapDuKien));
                details.setNgayNhapDuKien(dbFormatDate);
                logger.info("NgayXuatDuKien: {} -> {}", ngayNhapDuKien, dbFormatDate);
            } catch (IllegalArgumentException e) {
                logger.error("khong dung format: {}", ngayNhapDuKien);
            }
        }
    }

    private String layMaPR(PurchaseRequestIb purchaseRequestIb) {
        return purchaseRequestIbMapper.getMaPrById(purchaseRequestIb.getSysIdYeuCauNhapHang());
    }

    private PurchaseRequestIb saveRequestIb(PurchaseRequestIbReq purchaseRequestIbReq) {
        PurchaseRequestIb purchaseRequestIb = purchaseRequestIbConverter.toPurchaseRequestIb(purchaseRequestIbReq);
        if (purchaseRequestIbMapper.existById(purchaseRequestIbReq.getSysIdYeuCauNhapHang())) {
            purchaseRequestIbMapper.updatePurchaseRequestIb(purchaseRequestIb);
        } else {
            purchaseRequestIbMapper.insertPurchaseRequestIb(purchaseRequestIb);
        }
        return purchaseRequestIb;
    }

    private void sendEmail(String emailToSend, PurchaseRequestIb purchaseRequestIb) {
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
        if (purchaseRequestIb != null) {
            try {
                nguoiTao = userMapper.getFullNameByRoles(purchaseRequestIb.getNguoiYeuCau());
            } catch (Exception e) {
                logger.error("Error when get user info", e);
            }
        }
        String status = Objects.requireNonNull(purchaseRequestIb).getTrangThai();
        String title;
        String requestInfor;
        String body;
        String reason = (purchaseRequestIb.getLyDo());
        List<PurchaseDetailsIb> chiTietNhapHang = purchaseDetailsIbMapper.getPRDetailsIbByMaPR(purchaseRequestIb.getMaPR());
        chiTietNhapHang.forEach(detail -> {
            if (detail.getNgayNhapDuKien() != null) {
                String ngayXuatDuKienFormatted = TimeConverter.formatNgayXuat(TimeConverter.parseDateOnly(detail.getNgayNhapDuKien()));
                detail.setNgayNhapDuKien(ngayXuatDuKienFormatted);
            }
        });
        String detailsIbTable = TplEmailPR.bangYeuCauNhapHang(chiTietNhapHang);
        switch (status) {
            case Status.CONFIRM:
                title = TplEmailPR.emailTitleConfirm(purchaseRequestIb.getMaPR(), purchaseRequestIb.getTrangThai());
                body = TplEmailPR.emailBodyConfirm(title, detailsIbTable);
                break;
            case Status.APPROVING:
                title = TplEmailPR.emailTitle(purchaseRequestIb.getMaPR(), purchaseRequestIb.getTrangThai());
                requestInfor = TplEmailPR.requestInfor(nguoiTao, daiDienPo, chucVu);
                body = TplEmailPR.emailBody(title, requestInfor, detailsIbTable);
                break;
            case Status.REJECT:
                title = TplEmailPR.emailTitleReject(purchaseRequestIb.getMaPR(), purchaseRequestIb.getTrangThai());
                body = TplEmailPR.emailBodyReject(title, detailsIbTable, reason);
                break;
            default:
                logger.error("Trạng thái không hợp lệ: {}", purchaseRequestIb.getTrangThai());
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

    public void sendMailForApprove(PurchaseRequestIb purchaseRequestIb) {
        sendEmail(PurchaseRequestConst.DEFAULT_PO_EMAIL, purchaseRequestIb);
    }

    public void sendMailForConfirm(PurchaseRequestIb purchaseRequestIb) {
        sendEmail(PurchaseRequestConst.DEFAULT_PR_EMAIL, purchaseRequestIb);
    }

    public void sendMailForReject(PurchaseRequestIb purchaseRequestIb) {
        sendEmail(PurchaseRequestConst.DEFAULT_PR_EMAIL, purchaseRequestIb);
    }
}



