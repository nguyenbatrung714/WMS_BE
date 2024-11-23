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
    private Timestamp ngayCapNhat = new Timestamp(System.currentTimeMillis());
}
