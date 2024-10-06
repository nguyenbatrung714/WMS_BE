package org.example.wms_be.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@Builder
public class ProductDto {
    private Integer sysIdSanPham;
    private String tenSanPham;
    private Float soLuongHienCo;
    private Integer sysIdDanhMuc;
    private String moTa;
    private MultipartFile hinhAnh;
}
