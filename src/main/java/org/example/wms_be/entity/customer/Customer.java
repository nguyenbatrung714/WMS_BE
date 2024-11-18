package org.example.wms_be.entity.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    private Integer sysIdKhachHang;
    private String tenKhachHang;
    private String tenCongTy;
    private String soDienThoai;
    private String diaChi;
}
