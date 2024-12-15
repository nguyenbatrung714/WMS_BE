package org.example.wms_be.data.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseDetailConsignmentResp {
    private String maChiTietKhuVuc;
    private String tenChiTietKhuVuc;
    private Integer sysIdSanPham;
    private String tenSanPham;
}
