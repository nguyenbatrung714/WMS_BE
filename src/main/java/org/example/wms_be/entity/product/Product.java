package org.example.wms_be.entity.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private Integer sysIdSanPham;
    private String tenSanPham;
    private Double soLuongHienCo;
    private Integer sysIdDanhMuc;
    private String moTa;
    private String hinhAnh;
}
