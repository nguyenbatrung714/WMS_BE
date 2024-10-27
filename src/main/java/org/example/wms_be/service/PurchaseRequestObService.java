package org.example.wms_be.service;


import org.example.wms_be.data.request.PurchaseRequestObReq;
import org.example.wms_be.data.response.PurchaseRequestObResp;

import java.util.List;

public interface PurchaseRequestObService {
    List<PurchaseRequestObResp> getAllPurchaseRequestOb();
    void savePurchaseRequestOb(PurchaseRequestObReq purchaseRequestObReq);
}
