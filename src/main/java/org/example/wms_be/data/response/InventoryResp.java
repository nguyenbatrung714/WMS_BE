package org.example.wms_be.data.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class InventoryResp {
    private Integer sysIdTonKho;
    private Integer sysysIdSanPham;
    private String maKho;
    private String maLo;
    private String soLuong;
    private Timestamp ngayCapNhat;
    private String tenSanPham;
}
