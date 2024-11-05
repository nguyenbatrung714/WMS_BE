package org.example.wms_be.service;

import org.example.wms_be.data.request.PurchaseOrderIbReq;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PurchaseOrderIbService {
    @Transactional
    PurchaseOrderIbReq createPurchaseOrder(PurchaseOrderIbReq purchaseOrderIbReq);

    List<PurchaseOrderIbReq> getAllPurchaseOrders();
}
