package org.example.wms_be.service;

import com.github.pagehelper.PageInfo;
import org.example.wms_be.data.dto.WarehouseDto;

public interface WarehouseService {
    PageInfo<WarehouseDto> getAllWarehouses(int page, int size);

    WarehouseDto saveWarehouse(WarehouseDto warehouseDto);

    WarehouseDto getWarehouseById(String maKho);

    Void deleteWarehouse(String maKho);

    void addWarehouse(WarehouseDto warehouseDto);

    void updateWarehouse(WarehouseDto warehouseDto);
}
