package org.example.wms_be.data.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResp {
    private Integer sysIdSanPham;
    private String tenSanPham;
    private Double soLuongHienCo;
    private String moTa;
    private String hinhAnhUrl;
    private Integer sysIdDanhMuc;
}
