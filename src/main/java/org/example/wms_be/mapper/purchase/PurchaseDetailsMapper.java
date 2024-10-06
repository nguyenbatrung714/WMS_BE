package org.example.wms_be.mapper.purchase;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.purchase.PurchaseDetails;

import java.util.List;

@Mapper
public interface PurchaseDetailsMapper {
 List<PurchaseDetails> getAllPurchaseDetails(); // lấy tất cả chi tiết đơn hàng
 List<PurchaseDetails> getPurchaseRequestById(String maPR); // lấy chi tiết theo mã yêu cầu mua hàng

}
