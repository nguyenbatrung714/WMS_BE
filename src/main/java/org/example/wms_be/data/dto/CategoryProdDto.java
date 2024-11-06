package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryProdDto {
    private Integer sysIdDanhMuc;
    private String tenDanhMuc;
    private String moTa;
    private String maKho;
    private String tenKho;
}
