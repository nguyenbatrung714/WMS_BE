package org.example.wms_be.converter.inbound;

import org.example.wms_be.data.request.PurchaseOrderIbReq;
import org.example.wms_be.entity.inbound.PurchaseOrderIb;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderIbConverter {
    PurchaseOrderIb toPurchaseOrderIb(PurchaseOrderIbReq purchaseOrderIbReq);

    PurchaseOrderIbReq toPurchaseOrderIbReq(PurchaseOrderIb purchaseOrderIb);
}
