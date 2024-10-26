package org.example.wms_be.converter;

import org.example.wms_be.data.request.PurchaseOrderReq;
import org.example.wms_be.entity.purchase.PurchaseOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseOrderConverter {
    PurchaseOrder toPurchaseOrder(PurchaseOrderReq purchaseOrderReq);

    PurchaseOrderReq toPurchaseOrderReq(PurchaseOrder purchaseOrder);
}
