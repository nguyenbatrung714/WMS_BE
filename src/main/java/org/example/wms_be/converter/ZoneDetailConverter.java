package org.example.wms_be.converter;

import org.example.wms_be.data.dto.ZoneDetailDto;
import org.example.wms_be.entity.warehouse.ZoneDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ZoneDetailConverter {
    ZoneDetail toZoneDetail(ZoneDetailDto zoneDetailDto);

    ZoneDetailDto toZoneDetailDto(ZoneDetail zoneDetail);
}
