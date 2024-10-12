package org.example.wms_be.entity.purchase;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseRequestDetails {
    private Integer sysIdChiTietDonHang;
    private String  maInBound;
    private String  maPR;
    private String  maPO;
    private Integer  maKhachHang;
    private Integer  maSanPham;
    private Double  soLuong;
    private Double  gia;
    private Double  tongChiPhi;
    private String  ngayNhap;
    private String  tenSanPham;
}
