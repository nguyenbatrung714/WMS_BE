package org.example.wms_be.entity.warehouse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Warehouse {
    private Integer sysIdKho;
    private String maKho;
    private String tenKho;
    private String moTa;
    private Double dienTich;
    private Integer sysIdUser;
}
