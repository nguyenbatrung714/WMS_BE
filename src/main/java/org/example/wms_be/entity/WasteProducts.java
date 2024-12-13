package org.example.wms_be.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class WasteProducts {
    private Integer id;
    private String maKho;
    private String maLo;
    private String tenSanPham;
    private Float soLuong;
    private Timestamp ngayHetHan;
    private Date ngayPhePham;
    private String lyDo;
    private Integer sysIdSanPham;
}
