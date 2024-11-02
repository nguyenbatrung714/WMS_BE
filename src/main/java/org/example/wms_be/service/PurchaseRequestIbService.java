package org.example.wms_be.service;

import jakarta.mail.MessagingException;
import org.example.wms_be.data.dto.PurchaseRequestDto;
import org.example.wms_be.data.request.PurchaseRequestIbReq;
import org.example.wms_be.data.response.PurchaseRequestObResp;
import org.example.wms_be.data.response.inbound.PurchaseRequestIbResp;

import java.util.List;

public interface PurchaseRequestIbService {
    List<PurchaseRequestIbResp> getAllPurchaseRequestIb();
    List<PurchaseRequestIbResp> getPurchaseRequestIbByMaPR(String maPR);
    void savePurchaseRequestWithDetails(PurchaseRequestIbReq purchaseRequestIbReq); // lưu yêu cầu mua hàng với chi tiết
}
