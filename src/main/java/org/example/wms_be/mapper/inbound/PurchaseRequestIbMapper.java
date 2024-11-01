package org.example.wms_be.mapper.inbound;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.data.response.PurchaseRequestObResp;
import org.example.wms_be.data.response.inbound.PurchaseRequestIbResp;

import java.util.List;

@Mapper
public interface PurchaseRequestIbMapper {
    List<PurchaseRequestIbResp> getAllPurchaseRequestIb();
    List<PurchaseRequestIbResp> getAllPurchaseRequestIbByMaPR(String maPR);
}
