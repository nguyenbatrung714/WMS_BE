package org.example.wms_be.mapper.purchase;
import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.inbound.PurchaseRequestIb;

import java.util.List;

@Mapper
public interface PurchaseRequestMapper {
    List<PurchaseRequestIb> getAllPurchaseRequest();
    int insertPurchaseRequest(PurchaseRequestIb purchaseRequestIb);
    boolean existById(Integer sysIdYeuCauMuaHang);
    int updatePurchaseRequest(PurchaseRequestIb purchaseRequestIb);
}
