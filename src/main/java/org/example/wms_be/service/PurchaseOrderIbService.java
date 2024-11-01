package org.example.wms_be.service;

import org.example.wms_be.data.request.PurchaseOrderIbReq;
import org.springframework.transaction.annotation.Transactional;

public interface PurchaseOrderIbService {
    @Transactional
    PurchaseOrderIbReq createPurchaseOrder(PurchaseOrderIbReq purchaseOrderIbReq);
}
