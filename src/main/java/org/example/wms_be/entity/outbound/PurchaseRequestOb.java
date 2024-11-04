package org.example.wms_be.entity.outbound;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseRequestOb {
    private Integer sysIdYeuCauXuatHang;
    private String  maPR;
    private String  ngayYeuCau;
    private Integer nguoiYeuCau;
    private String trangThai;
    private String loaiYeuCau;
    List<PurchaseRequestDetailsOb> chiTietXuatHang;
}
