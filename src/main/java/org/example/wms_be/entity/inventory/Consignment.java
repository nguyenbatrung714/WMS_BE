package org.example.wms_be.entity.inventory;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Consignment {
    private Integer sysIdLoHang;
    private String maLo;
    private Integer sysIdSanPham;
    private Double soLuong;
    private String ngaySanXuat;
    private String hanSuDung;
    private Double dungTich;
    private String maChiTietKhuVuc;
    private Integer sysIdChiTietNhapHang;
    private String tenSanPham;
    private String tenChiTietKhuVuc;
}
