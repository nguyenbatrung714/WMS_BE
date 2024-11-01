package org.example.wms_be.service;

import jakarta.mail.MessagingException;
import org.example.wms_be.data.dto.PurchaseRequestDto;

import java.util.List;

public interface PurchaseRequestService {
    List<PurchaseRequestDto> getAllPurchaseRequest(); // lấy tất cả yêu cầu mua hàng
    void savePurchaseRequestWithDetails(PurchaseRequestDto purchaseRequestDto) throws MessagingException; // lưu yêu cầu mua hàng với chi tiết
}
