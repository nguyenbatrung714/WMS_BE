package org.example.wms_be.entity.customer;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Customer {
    private Integer sysIdKhachHang;
    private String tenKhachHang;
    private String tenCongTy;
    private String soDienThoai;
    private String sysIdNhaCungCap;
    private String tenNhaCungCap;
    private String diaChi;
    private Supplier nhaCungCap;

}
