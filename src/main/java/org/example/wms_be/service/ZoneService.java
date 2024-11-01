package org.example.wms_be.service;

import org.example.wms_be.data.dto.ZoneDto;

import java.util.List;

public interface ZoneService {

    List<ZoneDto> getAllZones();

    ZoneDto saveZone(ZoneDto zoneDto);

    ZoneDto getZoneById(String maKhuVuc);

    Void deleteZone(String maKhuVuc);

    void addZone(ZoneDto zoneDto);

    void updateZone(ZoneDto zoneDto);
}
