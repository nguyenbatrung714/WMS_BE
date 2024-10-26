package org.example.wms_be.entity.purchase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRequestDetails {
    private Integer sysIdChiTietDonHang;
    private String  maInBound;
    private String  maPR;
    private String  maPO;
    private Integer  sysIdKhachHang;
    private Integer  sysIdSanPham;
    private Double  soLuong;
    private Double  gia;
    private Double  tongChiPhi;
    private String  ngayNhap;
    private String  tenSanPham;
    private String  tenKhachHang;
}
