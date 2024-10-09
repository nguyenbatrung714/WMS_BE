package org.example.wms_be.mapper.purchase;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.purchase.PurchaseDetails;

import java.util.List;

@Mapper
public interface PurchaseDetailsMapper {
 List<PurchaseDetails> getAllPurchaseDetails(); // lấy tất cả chi tiết đơn hàng
 List<PurchaseDetails> getPurchaseRequestById(String maPR); // lấy chi tiết theo mã yêu cầu mua hàng
 int insertPurchaseDetails(PurchaseDetails purchaseDetails); // lưu chi tiết đơn hàng
 boolean existById(Integer sysIdChiTietDonHang); // kiểm tra tồn tại theo id);
 int updatePurchaseDetails(PurchaseDetails purchaseDetails); // cập nhật chi tiết đơn hàng

}
