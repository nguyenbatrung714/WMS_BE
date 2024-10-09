package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.PurchaseDetailsConverter;
import org.example.wms_be.converter.PurchaseRequestConverter;
import org.example.wms_be.data.dto.PurchaseDetailsDto;
import org.example.wms_be.data.dto.PurchaseRequestDto;
import org.example.wms_be.entity.purchase.PurchaseDetails;
import org.example.wms_be.entity.purchase.PurchaseRequest;
import org.example.wms_be.mapper.purchase.PurchaseDetailsMapper;
import org.example.wms_be.mapper.purchase.PurchaseRequestMapper;
import org.example.wms_be.service.PurchaseRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class PurchaseRequestImpl implements PurchaseRequestService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseRequestImpl.class);
    private final PurchaseRequestMapper purchaseRequestMapper;
    private final PurchaseDetailsMapper purchaseDetailsMapper;
    private final PurchaseRequestConverter purchaseRequestConverter;
    private final PurchaseDetailsConverter purchaseDetailsConverter;

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
                        List<PurchaseDetails> chiTietDonHang = purchaseDetailsMapper.getPurchaseRequestById(pr.getMaPR());
                        // neu chi tiet don hang ko rong thi gan vao doi tuong pr
                        logger.info("Found {} details for PurchaseRequest: {}", chiTietDonHang.size(), pr.getMaPR());
                        pr.setChiTietDonHang(chiTietDonHang);
                    });
            // chuyen doi doi tuong PurchaseRequest thanh PurchaseRequestDto
            return purchaseRequests.stream()
                    .map(purchaseRequestConverter::toPurchaseRequestDto)
                    .toList();
        } catch (Exception e) {
            logger.error("Get all purchase requests failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public void savePurchaseRequestWithDetails(PurchaseRequestDto purchaseRequestDto) {
        // Định dạng cho ngày yêu cầu và ngày nhập
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // Chuyển đổi DTO thành Entity
        PurchaseRequest purchaseRequest = purchaseRequestConverter.toPurchaseRequest(purchaseRequestDto);
        // Chuyển đổi ngày yêu cầu
        try {
            if (purchaseRequestDto.getNgayYeuCau() != null && !purchaseRequestDto.getNgayYeuCau().isEmpty()) {
                LocalDateTime ngayYeuCau = LocalDateTime.parse(purchaseRequestDto.getNgayYeuCau(), inputFormatter);
                purchaseRequest.setNgayYeuCau(ngayYeuCau.format(outputFormatter)); // Định dạng lại trước khi lưu
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Ngày không hợp lệ: " + purchaseRequestDto.getNgayYeuCau(), e);
        }
        // Lặp qua từng chi tiết yêu cầu mua hàng
        for (PurchaseDetailsDto detailDto : purchaseRequestDto.getChiTietDonHang()) {
            // Chuyển đổi DTO thành Entity
            PurchaseDetails detail = purchaseDetailsConverter.toPurchaseDetails(detailDto);
            // Lưu chi tiết yêu cầu mua hàng
            if (purchaseDetailsMapper.existById(detail.getSysIdChiTietDonHang())){
                purchaseDetailsMapper.updatePurchaseDetails(detail);
            }else {
                detail.setMaPR(purchaseRequest.getMaPR());
                purchaseDetailsMapper.insertPurchaseDetails(detail);
            }
            if (purchaseRequestMapper.existById(purchaseRequestDto.getSysIdYeuCauMuaHang())){
                purchaseRequestMapper.updatePurchaseRequest(purchaseRequest);
            }else {
                purchaseRequestMapper.insertPurchaseRequest(purchaseRequest);
            }
            // Chuyển đổi ngày nhập
            try {
                if (detailDto.getNgayNhap() != null && !detailDto.getNgayNhap().isEmpty()) {
                    LocalDateTime ngayNhap = LocalDateTime.parse(detailDto.getNgayNhap(), inputFormatter);
                    detail.setNgayNhap(ngayNhap.format(outputFormatter)); // Định dạng lại trước khi lưu
                }
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Ngày không hợp lệ: " + detailDto.getNgayNhap(), e);
            }
        }
        // Trả về đối tượng PurchaseRequest đã lưu
        logger.info("Saved PurchaseRequest: {}", purchaseRequest);
    }
}

