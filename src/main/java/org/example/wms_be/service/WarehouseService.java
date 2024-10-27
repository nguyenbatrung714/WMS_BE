package org.example.wms_be.service;

import org.example.wms_be.data.dto.WarehouseDto;

import java.util.List;

public interface WarehouseService {
    List<WarehouseDto> getAllWarehouses();

    WarehouseDto saveWarehouse(WarehouseDto warehouseDto);

    WarehouseDto getWarehouseById(String maKho);

    Void deleteWarehouse(String maKho);

    void addWarehouse(WarehouseDto warehouseDto);

    void updateWarehouse(WarehouseDto warehouseDto);
}
