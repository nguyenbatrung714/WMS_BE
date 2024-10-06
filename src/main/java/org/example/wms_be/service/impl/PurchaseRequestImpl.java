package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.PurchaseRequestConverter;
import org.example.wms_be.data.dto.PurchaseRequestDto;
import org.example.wms_be.entity.purchase.PurchaseDetails;
import org.example.wms_be.entity.purchase.PurchaseRequest;
import org.example.wms_be.mapper.purchase.PurchaseDetailsMapper;
import org.example.wms_be.mapper.purchase.PurchaseRequestMapper;
import org.example.wms_be.service.PurchaseRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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
}
