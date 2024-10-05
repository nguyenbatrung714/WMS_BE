package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseDto {
    private Integer sysIdKho;
    private String maKho;
    private String tenKho;
    private String moTa;
    private Double dienTich;
    private Integer sysIdUser;
}
