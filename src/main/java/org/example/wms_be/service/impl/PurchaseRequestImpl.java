package org.example.wms_be.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.constant.PrConst;
import org.example.wms_be.converter.PurchaseDetailsConverter;
import org.example.wms_be.converter.PurchaseRequestConverter;
import org.example.wms_be.data.dto.PurchaseRequestDetailsDto;
import org.example.wms_be.data.dto.PurchaseRequestDto;
import org.example.wms_be.entity.purchase.PurchaseRequestDetails;
import org.example.wms_be.entity.purchase.PurchaseRequest;
import org.example.wms_be.mapper.account.UserMapper;
import org.example.wms_be.mapper.purchase.PurchaseDetailsMapper;
import org.example.wms_be.mapper.purchase.PurchaseRequestMapper;
import org.example.wms_be.service.PurchaseRequestService;
import org.example.wms_be.utils.EmailUtil;
import org.example.wms_be.utils.TplEmailPR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


@Service
@RequiredArgsConstructor
public class PurchaseRequestImpl implements PurchaseRequestService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseRequestImpl.class);
    private final PurchaseRequestMapper purchaseRequestMapper;
    private final PurchaseDetailsMapper purchaseDetailsMapper;
    private final PurchaseRequestConverter purchaseRequestConverter;
    private final PurchaseDetailsConverter purchaseDetailsConverter;
    private final UserMapper userMapper;
    private final EmailUtil emailUtil;

    @Override
    public List<PurchaseRequestDto> getAllPurchaseRequest() {
        try {
            List<PurchaseRequest> purchaseRequests = purchaseRequestMapper.getAllPurchaseRequest();
            // Sử dụng forEach để xử lý các chi tiết đơn hàng
            purchaseRequests.stream()
                    // Loại bỏ các phần tử null
                    .filter(Objects::nonNull)
                    // Loại bỏ các phần tử không có mã
                    .filter(pr -> pr.getMaPR() != null)
                    // Xử lý từng phần tử
                    .forEach(pr -> {
                        // Lấy chi tiết đơn hàng theo mã yêu cầu mua hàng
                        List<PurchaseRequestDetails> chiTietDonHang = purchaseDetailsMapper.getPurchaseRequestById(pr.getMaPR());
                        // neu chi tiet don hang ko rong thi gan vao doi tuong pr
                        logger.info("Found {} details for PurchaseRequest: {}", chiTietDonHang.size(), pr.getMaPR());
                        pr.setChiTietDonHang(chiTietDonHang);
                    });
            // chuyen doi doi tuong PurchaseRequest thanh PurchaseRequestDto
            return purchaseRequests.stream().map(purchaseRequestConverter::toPurchaseRequestDto).toList();
        } catch (Exception e) {
            logger.error("Get all purchase requests failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public void savePurchaseRequestWithDetails(PurchaseRequestDto purchaseRequestDto) throws MessagingException {
        // Định dạng cho ngày yêu cầu
        DateTimeFormatter inputFormatterNgayYeuCau = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter outputFormatterNgayYeuCau = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Định dạng cho ngày nhập
        DateTimeFormatter inputFormatterNgayNhap = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatterNgayNhap = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Chuyển đổi DTO thành Entity
        PurchaseRequest purchaseRequest = purchaseRequestConverter.toPurchaseRequest(purchaseRequestDto);

        // Chuyển đổi ngày yêu cầu
        try {
            if (purchaseRequestDto.getNgayYeuCau() != null && !purchaseRequestDto.getNgayYeuCau().isEmpty()) {
                LocalDateTime ngayYeuCau = LocalDateTime.parse(purchaseRequestDto.getNgayYeuCau(), inputFormatterNgayYeuCau);
                purchaseRequest.setNgayYeuCau(ngayYeuCau.format(outputFormatterNgayYeuCau));
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Ngày không hợp lệ: " + purchaseRequestDto.getNgayYeuCau(), e);
        }

        // Lưu yêu cầu mua hàng và gửi email
        if (purchaseRequestMapper.existById(purchaseRequestDto.getSysIdYeuCauMuaHang())) {
            purchaseRequestMapper.updatePurchaseRequest(purchaseRequest);
        } else {
            purchaseRequestMapper.insertPurchaseRequest(purchaseRequest);

        }

        // Lặp qua từng chi tiết yêu cầu mua hàng
        for (PurchaseRequestDetailsDto detailDto : purchaseRequestDto.getChiTietDonHang()) {
            // Chuyển đổi DTO thành Entity
            PurchaseRequestDetails detail = purchaseDetailsConverter.toPurchaseDetails(detailDto);
            // Chuyển đổi và định dạng ngày nhập
            try {
                if (detailDto.getNgayNhap() != null && !detailDto.getNgayNhap().isEmpty()) {
                    LocalDate ngayNhap = LocalDate.parse(detailDto.getNgayNhap(), inputFormatterNgayNhap);
                    detail.setNgayNhap(ngayNhap.format(outputFormatterNgayNhap));
                }
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Ngày không hợp lệ: " + detailDto.getNgayNhap(), e);
            }
            // Lưu chi tiết yêu cầu mua hàng
            if (purchaseDetailsMapper.existById(detail.getSysIdChiTietDonHang())) {
                purchaseDetailsMapper.updatePurchaseDetails(detail);
                sendNotificationEmailForUpdate(purchaseRequest);
            } else {
                detail.setMaPR(purchaseRequest.getMaPR());
                purchaseDetailsMapper.insertPurchaseDetails(detail);
                sendNotificationEmailForInsert(purchaseRequestDto, purchaseRequest);
            }
        }
        logger.info("Saved PurchaseRequest: {}", purchaseRequest);
    }

    private void sendNotificationEmailForInsert(PurchaseRequestDto purchaseRequestDto, PurchaseRequest purchaseRequest) {
        // Gửi email đến địa chỉ mặc định của PO
        String emailToSend = PrConst.DEFAULT_PO_EMAIL;
        sendNotificationEmail(emailToSend, purchaseRequestDto, purchaseRequest, false);
    }

    private void sendNotificationEmailForUpdate(PurchaseRequest purchaseRequest) {
        // Gửi email đến địa chỉ mặc định của PO
        String emailToSend = PrConst.DEFAULT_PO_EMAIL;
        PurchaseRequestDto purchaseRequestDto = purchaseRequestConverter.toPurchaseRequestDto(purchaseRequest);
        sendNotificationEmail(emailToSend, purchaseRequestDto, purchaseRequest, true);
    }

    private void sendNotificationEmail(String emailToSend, PurchaseRequestDto purchaseRequestDto, PurchaseRequest purchaseRequest, boolean isUpdate) {
        if (emailToSend == null || emailToSend.isEmpty()) {
            logger.info("Không có địa chỉ email được cung cấp để gửi thông báo.");
            return;
        }

        if (!userMapper.checkMailExits(emailToSend)) {
            logger.info("Địa chỉ email không tồn tại: {}", emailToSend);
            return;
        }

        String userRequesting = PrConst.DEFAULT_USER_REQUESTING;
        String role = PrConst.DEFAULT_ROLE;
        String fullName = PrConst.DEFAULT_FULL_NAME;

        try {
            Map<String, String> userInfo = userMapper.getEmailByRoles(emailToSend);
            logger.info("Thông tin nhận được từ getEmailByRoles: {}", userInfo);
            if (userInfo != null) {
                fullName = userInfo.getOrDefault("fullName", PrConst.DEFAULT_FULL_NAME);
                role = userInfo.getOrDefault("role", PrConst.DEFAULT_ROLE);
            }
        } catch (Exception e) {
            logger.error("Lỗi khi lấy thông tin người dùng: ", e);
        }

        if (purchaseRequestDto != null) {
            try {
                userRequesting = userMapper.getFullNameByRoles(purchaseRequestDto.getNguoiYeuCau());
            } catch (Exception e) {
                logger.error("Lỗi khi lấy tên người yêu cầu: ", e);
            }
        }

        String title = isUpdate ? TplEmailPR.buildEmailTitleUpdate(purchaseRequest.getMaPR()) : TplEmailPR.buildEmailTitle(purchaseRequest.getMaPR());
        String requestInfo = isUpdate ? TplEmailPR.buildUpdateInfo(userRequesting) : TplEmailPR.buildRequestInfo(userRequesting, fullName, role);
        List<PurchaseRequestDetails> chiTietDonHangList = purchaseDetailsMapper.getPurchaseRequestById(purchaseRequest.getMaPR());
        String orderDetailsTable = TplEmailPR.buildOrderDetailsTable(chiTietDonHangList);
        String body = isUpdate ? TplEmailPR.buildEmailBodyForUpdate(title, requestInfo, orderDetailsTable) : TplEmailPR.buildEmailBody(title, requestInfo, orderDetailsTable);
        String subject = isUpdate ? "Thông báo chỉnh sửa yêu cầu: từ phòng purchase request" : "Thông báo yêu cầu: phê duyệt yêu cầu mua hàng";

        try {
            emailUtil.sendEmail(emailToSend, subject, body);
            logger.info("Đã gửi email đến: {}", emailToSend);
        } catch (Exception e) {
            logger.error("Lỗi khi gửi email đến {}: {}", emailToSend, e.getMessage());
        }
    }
}



