package org.example.wms_be.converter;

import org.example.wms_be.data.request.PurchaseRequestObReq;
import org.example.wms_be.data.response.PurchaseRequestObResp;
import org.example.wms_be.entity.purchase.PurchaseRequestOb;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseRequestObConverter {
    PurchaseRequestOb toPurchaseRequestObResp(PurchaseRequestObResp purchaseRequestObResp);
    PurchaseRequestObResp toPurchaseRequestObResp(PurchaseRequestOb purchaseRequestOb);
    PurchaseRequestOb toPurchaseRequestObReq(PurchaseRequestObReq purchaseRequestObReq);
    PurchaseRequestObReq toPurchaseRequestObReq(PurchaseRequestOb purchaseRequestOb);
}
