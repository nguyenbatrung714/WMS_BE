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
    private Timestamp ngaySanXuat;
    private Timestamp hanSuDung;
    private Double dungTich;
    private String maChiTietKhuVuc;
}
