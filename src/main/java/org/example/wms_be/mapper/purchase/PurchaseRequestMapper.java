package org.example.wms_be.mapper.purchase;
import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.purchase.PurchaseRequest;
import java.util.List;

@Mapper
public interface PurchaseRequestMapper {
    List<PurchaseRequest> getAllPurchaseRequest();
    int insertPurchaseRequest(PurchaseRequest purchaseRequest);
    boolean existById(Integer sysIdYeuCauMuaHang);
    int updatePurchaseRequest(PurchaseRequest purchaseRequest);
}
