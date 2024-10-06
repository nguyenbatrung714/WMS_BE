package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseDetailsDto {
    private Integer sysIdChiTietDonHang;
    private String  maInBound;
    private String  maPR;
    private String  maPO;
    private String  maKhachHang;
    private String  maSanPham;
    private Double  soLuong;
    private Double  gia;
    private Double  tongChiPhi;
    private String  ngayNhap;
}
