package org.example.wms_be.converter;

import org.example.wms_be.data.request.PurchaseRequestDetailsObReq;
import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;
import org.example.wms_be.entity.purchase.PurchaseRequestDetailsOb;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseDetailsObConverter {
    PurchaseRequestDetailsOb toPurchaseRequestDeatilsOb(PurchaseRequestDetailsObResp purchaseRequestDetailsObResp);
    PurchaseRequestDetailsObResp toPurchaseRequestDetailsResp(PurchaseRequestDetailsOb purchaseRequestDeatilsOb);
    //
    PurchaseRequestDetailsOb toPurchaseRequestDeatilsOb(PurchaseRequestDetailsObReq purchaseRequestDetailsObReq);
    PurchaseRequestDetailsObReq toPurchaseRequestDetailsObReq(PurchaseRequestDetailsOb purchaseRequestDeatilsOb);

}
