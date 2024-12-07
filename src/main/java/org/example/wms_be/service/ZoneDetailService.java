package org.example.wms_be.service;

import org.example.wms_be.data.dto.ZoneDetailDto;
import org.example.wms_be.data.response.WarehouseDetailConsignmentResp;

import java.util.List;

public interface ZoneDetailService {
    List<ZoneDetailDto> getAllZoneDetails();

    ZoneDetailDto saveZoneDetail(ZoneDetailDto zoneDetailDto);

    ZoneDetailDto getZoneDetailByMaChiTietKhuVuc(String maChiTietKhuVuc);

    void deleteZoneDetail(String maChiTietKhuVuc);

    void addZoneDetail(ZoneDetailDto zoneDetailDto);

    void updateZoneDetail(ZoneDetailDto zoneDetailDto);

    WarehouseDetailConsignmentResp getWarehouseDetailByProduct(Integer sysIdSanPham);
}
