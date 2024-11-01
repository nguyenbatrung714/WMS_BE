package org.example.wms_be.mapper.purchase;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.data.response.PurchaseRequestObResp;
import org.example.wms_be.entity.purchase.PurchaseRequestOb;

import java.util.List;

@Mapper
public interface PurchaseRequestObMapper {
    List<PurchaseRequestObResp> getAllPurchaseRequestOb();
    int insertPurchaseRequestOb (PurchaseRequestOb purchaseRequestOb);
    int updatePurchaseRequestOb (PurchaseRequestOb purchaseRequestOb);
    boolean existById(Integer sysIdYeuCauXuatHang);
}
