package org.example.wms_be.data.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductReq {
    private Integer sysIdSanPham;
    private String tenSanPham;
    private Float soLuongHienCo;
    private String moTa;
    private MultipartFile hinhAnh;
    private Integer sysIdDanhMuc;
}
