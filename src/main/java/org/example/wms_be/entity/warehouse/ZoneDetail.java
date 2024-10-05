package org.example.wms_be.entity.warehouse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZoneDetail {
    private Integer sysIdChiTietKhuVuc;
    private String maChiTietKhuVuc;
    private String tenChiTietKhuVuc;
    private String moTa;
    private Double theTichLuuTru;
    private Double theTichKhaDung;
    private String maKhuVuc;
}
