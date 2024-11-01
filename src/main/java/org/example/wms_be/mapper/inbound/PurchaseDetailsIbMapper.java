package org.example.wms_be.mapper.inbound;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.data.response.inbound.PurchaseRequestDetailsIbResp;
import org.example.wms_be.entity.inbound.PurchaseDetailsIb;

import java.util.List;

@Mapper
public interface PurchaseDetailsIbMapper {
    List<PurchaseDetailsIb> getAllPurchaseDetails(); // lấy tất cả chi tiết đơn hàng

    List<PurchaseRequestDetailsIbResp> getPurchaseRequestById(String maPR); // lấy chi tiết theo mã yêu cầu mua hàng

    int insertPurchaseDetails(PurchaseDetailsIb purchaseDetails); // lưu chi tiết đơn hàng

    boolean existById(Integer sysIdChiTietDonHang); // kiểm tra tồn tại theo id);

    int updatePurchaseDetails(PurchaseDetailsIb purchaseDetails); // cập nhật chi tiết đơn hàng

    List<PurchaseDetailsIb> getPRDetailsIbByMaPR(String maPR); // Get detail by maPR

    int updateDetailsIbFromPR(String maPR, String maPO); // update status from PR to PO (Từ đơn hàng yêu cầu mua hàng đến đơn hàng mua hàng)
}
