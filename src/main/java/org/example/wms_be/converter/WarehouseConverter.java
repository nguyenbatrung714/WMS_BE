package org.example.wms_be.converter;

import org.example.wms_be.data.dto.WarehouseDto;
import org.example.wms_be.entity.warehouse.Warehouse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseConverter {
    Warehouse toWarehouse(WarehouseDto warehouseDto);

    WarehouseDto toWarehouseDto(Warehouse warehouse);
}
