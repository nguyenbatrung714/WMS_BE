package org.example.wms_be.mapper.purchase;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.data.response.PurchaseRequestDetailsResp;
import org.example.wms_be.entity.purchase.PurchaseRequestDetailsOb;

import java.util.List;

@Mapper
public interface PurchaseDetailsObMapper {
    List<PurchaseRequestDetailsResp>layDanhSachXuatHangTheoMaPR(String maPR);
    int insertPurchaseRequestDetailsOb(PurchaseRequestDetailsOb purchaseRequestDetailsOb);
    int updatePurchaseRequestDetailsOb(PurchaseRequestDetailsOb purchaseRequestDetailsOb);
    boolean existById(Integer sysIdChiTietXuatHang);

}
