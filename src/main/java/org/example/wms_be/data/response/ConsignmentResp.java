package org.example.wms_be.data.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ConsignmentResp {
    private Integer sysIdChiTietNhapHang;
    private String maInbound;
    private String maPR;
    private Double gia;
    private Double tongChiPhi;
    private String maPO;
    private String tenKhachHang;
    private String tenNhaCungCap;
    private String tenSanPham;
    private String hinhAnh;
    private Double soLuongTonKho;
    private Timestamp ngaySanXuat;
    private Timestamp hanSuDung;
    private Double soLuongNhap;
    private String tenKho;
    private Timestamp ngayNhap;
    private String maLo;
}
