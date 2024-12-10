package org.example.wms_be.data.response.thongke;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThongKeSanPhamKhuVucResp {
    private String tenSanPham;
    private String tenKho;
    private String tenKhuVuc;
    private String tenChiTietKhuVuc;
    private Double tongSoLuongTonKho;
}
