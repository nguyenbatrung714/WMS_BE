package org.example.wms_be.entity.inventory;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Inventory {
    private Integer sysIdTonKho;
    private Integer sysIdSanPham;
    private String tenSanPham;
    private String maKho;
    private String maLo;
    private Float soLuong;
    private Timestamp ngayCapNhat;
    private Boolean isNearExpiry;
    private Timestamp hanSuDung;
}
