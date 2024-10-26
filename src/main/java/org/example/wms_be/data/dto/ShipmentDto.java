package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class ShipmentDto {
    private Integer sysIdLoHang;
    private String maLo;
    private Integer sysIdSanPham;
    private Float soLuong;
    private Date ngaySanXuat;
    private Date hanSuDung;
    private Float dungTich;
    private String maChiTietKhuVuc;
}
