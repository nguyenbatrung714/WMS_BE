package org.example.wms_be.mapper.purchase;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.wms_be.data.request.PurchaseRequestDetailsObReq;
import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;
import org.example.wms_be.data.response.inbound.PurchaseRequestIbResp;
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
    void updateDetailsObFromPO(@Param("maPO") String maPO,
                               @Param("maOB") String maOB,
                               @Param("sysIdChiTietXuatHang") Integer sysIdChiTietXuatHang);

    List<PurchaseRequestDetailsOb> getPurchaseDetailsObByMaPO(String maPO);
    double getSoLuongCanXuat( Integer sysIdChiTietXuatHang,Integer sysIdSanPham);
    List<Integer> getSysIdSanPhamByMaPO(String maPO);
    Integer getMaOutbound(Integer sysIdChiTietXuatHang);
    List<PurchaseRequestDetailsObResp> getMostObProducts();

    // Thống kê sản phẩm nhập ít nhất
    List<PurchaseRequestDetailsObResp> getLeastObProducts();

}
