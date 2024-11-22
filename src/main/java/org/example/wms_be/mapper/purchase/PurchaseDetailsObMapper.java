package org.example.wms_be.mapper.purchase;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.data.request.PurchaseRequestDetailsObReq;
import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;
import org.example.wms_be.entity.inbound.PurchaseDetailsIb;
import org.example.wms_be.entity.outbound.PurchaseRequestDetailsOb;

import java.util.List;

@Mapper
public interface PurchaseDetailsObMapper {
    List<PurchaseRequestDetailsObResp>layDanhSachXuatHangTheoMaPR(String maPR);
    List<PurchaseRequestDetailsObResp>layDanhSachXuatHangTheoMaOutbound(String maOB);
    int insertPurchaseRequestDetailsOb(PurchaseRequestDetailsOb purchaseRequestDetailsOb);
    int updatePurchaseRequestDetailsOb(PurchaseRequestDetailsOb purchaseRequestDetailsOb);
    boolean existById(Integer sysIdChiTietXuatHang);
    int updateDetailsObFromPO(String maPO, String maOB);
    List<PurchaseRequestDetailsOb> getPurchaseDetailsObByMaPO(String maPO);

}
