package org.example.wms_be.data.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class InventoryReq {
        private Integer sysIdTonKho;
        private Integer sysysIdSanPham;
        private String maKho;
        private String maLo;
        private Float soLuong;
        private Timestamp ngayCapNhat = new Timestamp(System.currentTimeMillis());
        private String tenSanPham;
}
