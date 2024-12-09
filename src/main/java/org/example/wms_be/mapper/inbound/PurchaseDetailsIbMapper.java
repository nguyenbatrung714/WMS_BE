package org.example.wms_be.mapper.inbound;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.data.request.PurchaseDetailsIbReq;
import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;
import org.example.wms_be.data.response.inbound.PurchaseRequestDetailsIbResp;
import org.example.wms_be.entity.inbound.PurchaseDetailsIb;

import java.util.List;

@Mapper
public interface PurchaseDetailsIbMapper {
    List<PurchaseDetailsIb> getAllPurchaseDetails(); // lấy tất cả chi tiết đơn hàng

    List<PurchaseRequestDetailsIbResp> getPurchaseRequestById(String maPR); // lấy chi tiết theo mã yêu cầu mua hàng

    int insertPurchaseDetails(PurchaseDetailsIb purchaseDetailsIb); // lưu chi tiết đơn hàng

    boolean existById(Integer sysIdChiTietNhapHang); // kiểm tra tồn tại theo id);

    int updatePurchaseDetails(PurchaseDetailsIb purchaseDetailsIb); // cập nhật chi tiết đơn hàng

    List<PurchaseDetailsIb> getPRDetailsIbByMaPR(String maPR); // Get detail by maPR

    int updateDetailsIbFromPR(String maPR, String maPO); // update status from PR to PO (Từ đơn hàng yêu cầu mua hàng đến đơn hàng mua hàng)

    List<PurchaseDetailsIb> getPurchaseDetailsIbByMaPO(String maPO);

    int updateDetailsIbFromPO(String maPO, String maInBound);

    List<PurchaseDetailsIb> getPurchaseDetailsIbByMaIb(String maInBound);

    PurchaseDetailsIb getPurchaseDetailsById(Integer sysIdChiTietNhapHang);
    List<PurchaseDetailsIbReq> getMostIbProducts();

    // Thống kê sản phẩm nhập ít nhất
    List<PurchaseDetailsIbReq> getLeastIbProducts();
}
