package org.example.wms_be.data.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConsignmentInbound {
    private Integer sysIdLoHang;
    private String maLo;
    private Integer sysIdSanPham;
    private Double soLuong;
    private String ngaySanXuat;
    private String hanSuDung;
    private Double dungTich;
    private String maChiTietKhuVuc;
    private Integer sysIdChiTietNhapHang;
}
