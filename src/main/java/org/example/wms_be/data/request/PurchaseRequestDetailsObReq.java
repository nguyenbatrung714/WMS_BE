package org.example.wms_be.data.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRequestDetailsObReq {
    private Integer sysIdChiTietXuatHang;
    private String  maPR;
    private String maOB;
    private Integer  sysIdKhachHang;
    private Integer  sysIdSanPham;
    private Double  soLuong;
    private Double  gia;
    private Double  tongChiPhi;
    private String  ngayXuatDuKien;
}
