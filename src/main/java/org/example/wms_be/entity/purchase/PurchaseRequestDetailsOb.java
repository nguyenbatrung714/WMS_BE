package org.example.wms_be.entity.purchase;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRequestDetailsOb {
    private Integer sysIdChiTietXuatHang;
    private String  maOB;
    private String  maPR;
    private String  maPO;
    private String  maLoHang;
    private Integer  sysIdKhachHang;
    private Integer  sysIdSanPham;
    private Double  soLuong;
    private Double  gia;
    private Double  tongChiPhi;
    private String  ngayXuatDuKien;
}
