package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class WasteProductsDto {
    private Integer id;
    private String maKho;
    private String maLo;
    private String tenSanPham;
    private Float soLuong;
    private Timestamp ngayHetHan;
    private Date ngayPhePham;
    private String lyDo;
}
