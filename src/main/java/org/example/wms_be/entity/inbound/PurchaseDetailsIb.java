package org.example.wms_be.entity.inbound;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PurchaseDetailsIb {
    private Integer sysIdChiTietNhapHang;
    private String  maInBound;
    private String  maPR;
    private String  maPO;
    private Integer  sysIdKhachHang;
    private Integer  sysIdSanPham;
    private Double soLuong;
    private BigDecimal  gia;
    private BigDecimal  tongChiPhi;
    private String  ngayNhapDuKien;
    private String maLoHang;
    private String tenSanPham;
    private String tenKhachHang;
    private Integer sysIdNhaCungCap;
    private String tenNhaCungCap;
}
