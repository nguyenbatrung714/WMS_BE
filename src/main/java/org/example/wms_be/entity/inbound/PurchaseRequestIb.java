package org.example.wms_be.entity.inbound;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseRequestIb {
    private Integer sysIdYeuCauMuaHang;
    private String maPR;
    private String ngayYeuCau;
    private Integer nguoiYeuCau;
    private String trangThai;
    private String fullName;
    List<PurchaseDetailsIb> chiTietNhapHang;
}
