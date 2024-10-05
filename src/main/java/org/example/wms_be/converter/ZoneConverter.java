package org.example.wms_be.converter;

import org.example.wms_be.data.dto.ZoneDto;
import org.example.wms_be.entity.warehouse.Zone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ZoneConverter {
    Zone toZone(ZoneDto zoneDto);

    ZoneDto toZoneDto(Zone zone);
}
