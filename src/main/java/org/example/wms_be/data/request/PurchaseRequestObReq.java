package org.example.wms_be.data.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseRequestObReq {
    private Integer sysIdYeuCauXuatHang;
    private String maPR;
    private String nguoiYeuCau;
    private String trangThai;
    private String loaiYeuCau;
    private String lyDo;
    List<PurchaseRequestDetailsObReq> chiTietXuatHang;
}
