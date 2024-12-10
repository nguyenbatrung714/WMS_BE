package org.example.wms_be.data.response.thongke;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThongKeGiaoDichSanPhamResp {
    private String tenSanPham;
    private Integer soLuongGiaoDich;
    private Double  tongSoLuong;
    private String  loaiGiaoDich;
}
