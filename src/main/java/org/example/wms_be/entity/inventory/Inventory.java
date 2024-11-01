package org.example.wms_be.entity.inventory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inventory {
    private Integer sysIdTonKho;
    private Integer sysysIdSanPham;
    private String maKho;
    private String maLo;
    private String soLuong;
    private String ngayCapNhat;
}
