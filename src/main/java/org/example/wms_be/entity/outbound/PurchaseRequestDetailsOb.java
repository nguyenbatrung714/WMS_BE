package org.example.wms_be.entity.outbound;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PurchaseRequestDetailsOb {
    private Integer sysIdChiTietXuatHang;
    private String  maOB;
    private String  maPR;
    private String  maPO;
    private Integer  sysIdKhachHang;
    private Integer  sysIdSanPham;
    private String tenSanPham;
    private Double  soLuong;
    private BigDecimal gia;
    private BigDecimal  tongChiPhi;
    private String  ngayXuatDuKien;
}
