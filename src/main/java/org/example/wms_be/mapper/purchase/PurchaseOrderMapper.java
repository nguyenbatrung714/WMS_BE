package org.example.wms_be.mapper.purchase;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.purchase.PurchaseOrder;

@Mapper
public interface PurchaseOrderMapper {
    int insertPurchaseOrder(PurchaseOrder purchaseOrder);
}
