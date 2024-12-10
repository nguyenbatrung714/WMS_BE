package org.example.wms_be.data.response.thongke;

import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
public class ThongKeGiaoDichSanPhamGanDayResp {
    private List<ThongKeGiaoDichSanPhamResp> thongKeGiaoDichSanPhamResp;
    private Integer giaoDichGanDay;
}
