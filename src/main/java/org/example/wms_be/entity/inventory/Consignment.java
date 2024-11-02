package org.example.wms_be.entity.inventory;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Consignment {
    private Integer sysIdLoHang;
    private String maLo;
    private String sysIdInbound;
    private Integer sysIdSanPham;
    private Double soLuong;
    private Timestamp ngaySanXuat;
    private Timestamp hanSuDung;
    private Double dungTich;
    private String maChiTietKhuVuc;
}