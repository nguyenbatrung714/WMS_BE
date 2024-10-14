package org.example.wms_be.service;

import org.example.wms_be.data.request.PurchaseOrderReq;
import org.springframework.transaction.annotation.Transactional;

public interface PurchaseOrderService {
    @Transactional
    PurchaseOrderReq createPurchaseOrder(PurchaseOrderReq purchaseOrderReq);
}
