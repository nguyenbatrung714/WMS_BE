package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZoneDetailDto {
    private Integer sysIdChiTietKhuVuc;
    private String maChiTietKhuVuc;
    private String tenChiTietKhuVuc;
    private String moTa;
    private Double theTichLuuTru;
    private Double theTichKhaDung;
    private String maKhuVuc;
    private String tenKhuVuc;
}
