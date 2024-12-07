package org.example.wms_be.data.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class InventoryResp {
    private Integer sysIdTonKho;
    private Integer sysIdSanPham;
    private String maKho;
    private String maLo;
    private Double soLuong;
    private String ngayCapNhat;
    private String tenSanPham;
    private String tenKho;
}
