package org.example.wms_be.service;

import com.github.pagehelper.PageInfo;
import org.example.wms_be.data.dto.ZoneDto;

public interface ZoneService {

    PageInfo<ZoneDto> getAllZones(int page, int size);

    ZoneDto saveZone(ZoneDto zoneDto);

    ZoneDto getZoneById(String maKhuVuc);

    Void deleteZone(String maKhuVuc);

    void addZone(ZoneDto zoneDto);

    void updateZone(ZoneDto zoneDto);
}
