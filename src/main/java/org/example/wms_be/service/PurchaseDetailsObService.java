package org.example.wms_be.service;

import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;

import java.util.List;

public interface PurchaseDetailsObService {
    List<PurchaseRequestDetailsObResp> getChiTietDonHangByMaPR(String maPR);
}
