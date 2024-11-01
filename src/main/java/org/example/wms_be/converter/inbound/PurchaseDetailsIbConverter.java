package org.example.wms_be.converter.inbound;

import org.example.wms_be.data.request.PurchaseDetailsIbReq;
import org.example.wms_be.entity.inbound.PurchaseDetailsIb;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseDetailsIbConverter {
    PurchaseDetailsIb toPurchaseDetailsIb(PurchaseDetailsIbReq purchaseDetailsIbReq);
    PurchaseDetailsIbReq toPurchaseDetailsIbReq(PurchaseDetailsIb purchaseDetails);

}
