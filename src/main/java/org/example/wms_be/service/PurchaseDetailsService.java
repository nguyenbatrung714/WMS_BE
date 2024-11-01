package org.example.wms_be.service;

import org.example.wms_be.data.request.PurchaseDetailsIbReq;

import java.util.List;

public interface PurchaseDetailsService {
    List<PurchaseDetailsIbReq> getAllPurchaseDetails();
}
