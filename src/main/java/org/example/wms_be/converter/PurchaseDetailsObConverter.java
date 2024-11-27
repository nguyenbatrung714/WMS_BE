package org.example.wms_be.converter;

import org.example.wms_be.data.request.PurchaseRequestDetailsObReq;
import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;
import org.example.wms_be.entity.outbound.PurchaseRequestDetailsOb;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseDetailsObConverter {
    PurchaseRequestDetailsOb toPurchaseRequestDeatilsOb(PurchaseRequestDetailsObResp purchaseRequestDetailsObResp);
    PurchaseRequestDetailsObResp toPurchaseRequestDetailsResp(PurchaseRequestDetailsOb purchaseRequestDeatilsOb);
    //
    PurchaseRequestDetailsOb toPurchaseRequestDeatilsOb(PurchaseRequestDetailsObReq purchaseRequestDetailsObReq);
    PurchaseRequestDetailsObReq toPurchaseRequestDetailsObReq(PurchaseRequestDetailsOb purchaseRequestDeatilsOb);

    List<PurchaseRequestDetailsOb> toPurchaseRequestDetailsOb(List<PurchaseRequestDetailsObResp> purchaseRequestDetailsObs);
}
