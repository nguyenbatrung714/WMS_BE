package org.example.wms_be.data.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ConsignmentReq {
    private Integer sysIdLoHang;
    private String maLo;
    private Integer sysIdSanPham;
    private Double soLuong;
    private String ngaySanXuat;
    private String hanSuDung;
    private Double dungTich;
    private String maChiTietKhuVuc;
    private Integer sysIdChiTietNhapHang;
    private String tenSanPham;
    private String tenChiTietKhuVuc;
}
