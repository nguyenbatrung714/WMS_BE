package org.example.wms_be.mapper.warehouse;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.warehouse.Warehouse;

import java.util.List;

@Mapper
public interface WarehouseMapper {
    List<Warehouse> getAllWarehouses();

    int insertWarehouse(Warehouse warehouse);

    int updateWarehouse(Warehouse warehouse);

    int deleteWarehouse(String maKho);

    boolean checkWarehouseExists(String maKho);

    Warehouse getWarehouseByMaKho(String sysIdKho);
}
