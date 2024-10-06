package org.example.wms_be.entity.purchase;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseRequest {
    private Integer sysIdYeuCauMuaHang;
    private String maPR;
    private String ngayYeuCau;
    private Integer nguoiYeuCau;
    private Boolean trangThai;
    List<PurchaseDetails> chiTietDonHang;
}
