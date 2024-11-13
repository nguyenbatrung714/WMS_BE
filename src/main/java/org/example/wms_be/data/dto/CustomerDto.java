package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.wms_be.entity.customer.Supplier;

import java.util.Set;

@Getter
@Setter
public class CustomerDto {
    private Integer sysIdKhachHang;
    private String tenKhachHang;
    private String tenCongTy;
    private String soDienThoai;
    private String sysIdNhaCungCap;
    private String tenNhaCungCap;
    private String diaChi;

}
