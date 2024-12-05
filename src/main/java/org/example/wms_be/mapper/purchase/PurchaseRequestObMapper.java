package org.example.wms_be.mapper.purchase;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.data.response.PurchaseRequestObResp;
import org.example.wms_be.data.response.thongke.ThongKeGiaoDichSanPhamResp;
import org.example.wms_be.entity.outbound.PurchaseRequestOb;

import java.util.List;

@Mapper
public interface PurchaseRequestObMapper {
    List<PurchaseRequestObResp> getAllPurchaseRequestOb();
    List<PurchaseRequestObResp> getAllPurchaseRequestObByMaPR(String maPR);
    int insertPurchaseRequestOb (PurchaseRequestOb purchaseRequestOb);
    int updatePurchaseRequestOb (PurchaseRequestOb purchaseRequestOb);
    boolean existById(Integer sysIdYeuCauXuatHang);
    String getMaPRById(Integer sysIdYeuCauXuatHang);
    List<ThongKeGiaoDichSanPhamResp> getThongKeGiaoDichSanPhamXuat();
    Integer giaoDichXuatGanDay();
}
