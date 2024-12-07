package org.example.wms_be.service;

import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;
import org.example.wms_be.data.response.inbound.PurchaseRequestIbResp;

import java.util.List;

public interface PurchaseDetailsObService {
    List<PurchaseRequestDetailsObResp> getChiTietDonHangByMaPR(String maPR);
    //Thống kê sản phẩm nhập nhiều nhất
    List<PurchaseRequestDetailsObResp> getMostObProducts();

    // Thống kê sản phẩm nhập ít nhất
    List<PurchaseRequestDetailsObResp> getLeastObProducts();
}
