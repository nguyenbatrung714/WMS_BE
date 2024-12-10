package org.example.wms_be.entity.thongke;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThongKeGiaoDichSanPham {
    private String tenSanPham;
    private Integer soLuongGiaoDich;
    private Double  tongSoLuong;
    private String  loaiGiaoDich;
}
