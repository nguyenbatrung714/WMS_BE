package org.example.wms_be.mapper.inbound;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.inbound.PurchaseOrderIb;

import java.util.List;

@Mapper
public interface PurchaseOrderIbMapper {
    int insertPurchaseOrderIb(PurchaseOrderIb purchaseOrderIb);

    boolean purchaseOrderIbExistByMaPO(String maPO);

    List<PurchaseOrderIb> getAllPuchaseOrderIb();
}
