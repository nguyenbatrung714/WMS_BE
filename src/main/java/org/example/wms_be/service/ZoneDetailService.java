package org.example.wms_be.service;

import com.github.pagehelper.PageInfo;
import org.example.wms_be.data.dto.ZoneDetailDto;

public interface ZoneDetailService {
    PageInfo<ZoneDetailDto> getAllZoneDetails(int page, int size);

    ZoneDetailDto saveZoneDetail(ZoneDetailDto zoneDetailDto);

    ZoneDetailDto getZoneDetailByMaChiTietKhuVuc(String maChiTietKhuVuc);

    void deleteZoneDetail(String maChiTietKhuVuc);

    void addZoneDetail(ZoneDetailDto zoneDetailDto);

    void updateZoneDetail(ZoneDetailDto zoneDetailDto);
}
