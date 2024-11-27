package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {
    private Integer sysIdKhachHang;
    private String tenKhachHang;
    private String tenCongTy;
    private String soDienThoai;
    private String diaChi;
}
