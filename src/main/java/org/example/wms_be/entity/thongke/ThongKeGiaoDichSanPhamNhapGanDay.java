package org.example.wms_be.entity.thongke;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ThongKeGiaoDichSanPhamNhapGanDay {
    private List<ThongKeGiaoDichSanPham> thongKeGiaoDichSanPhamNhaps;
    private Integer giaoDichGanDay;
}
