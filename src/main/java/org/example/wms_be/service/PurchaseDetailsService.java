package org.example.wms_be.service;

import org.example.wms_be.data.request.PurchaseDetailsIbReq;
import org.example.wms_be.entity.inbound.PurchaseDetailsIb;

import java.util.List;

public interface PurchaseDetailsService {
    List<PurchaseDetailsIbReq> getAllPurchaseDetails();
    List<PurchaseDetailsIbReq> getMostIbProducts();

    // Thống kê sản phẩm nhập ít nhất
    List<PurchaseDetailsIbReq> getLeastIbProducts();
}
