package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.WarehouseConverter;
import org.example.wms_be.data.dto.WarehouseDto;
import org.example.wms_be.entity.warehouse.Warehouse;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.account.UserMapper;
import org.example.wms_be.mapper.warehouse.WarehouseMapper;
import org.example.wms_be.service.WarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private static final Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);
    private final WarehouseMapper warehouseMapper;
    private final WarehouseConverter warehouseConverter;
    private final UserMapper userMapper;

    @Override
    public List<WarehouseDto> getAllWarehouses() {
        return warehouseMapper.getAllWarehouses()
                .stream()
                .map(warehouseConverter::toWarehouseDto)
                .toList();
    }

    @Override
    public WarehouseDto saveWarehouse(WarehouseDto warehouseDto) {
        if (!userMapper.checkUserExits(warehouseDto.getSysIdUser())) {
            throw new ResourceNotFoundException("User", "sysIdUser", warehouseDto.getSysIdUser() + "");
        }

        Warehouse warehouse = warehouseConverter.toWarehouse(warehouseDto);

        try {
            if (warehouseMapper.checkWarehouseExists(warehouseDto.getMaKho())) {
                warehouseMapper.updateWarehouse(warehouse);
                warehouse = warehouseMapper.getWarehouseByMaKho(warehouseDto.getMaKho());
            } else {
                warehouseMapper.insertWarehouse(warehouse);
            }
        } catch (Exception e) {
            logger.error("Insert warehouse failed: {}", e.getMessage());
            throw new BadSqlGrammarException("Insert warehouse failed");
        }

        return warehouseConverter.toWarehouseDto(warehouse);
    }

    @Override
    public WarehouseDto getWarehouseById(String maKho) {
        if (!warehouseMapper.checkWarehouseExists(maKho)) {
            throw new ResourceNotFoundException("Warehouse", "maKho", maKho);
        }

        try {
            Warehouse warehouse = warehouseMapper.getWarehouseByMaKho(maKho);
            return warehouseConverter.toWarehouseDto(warehouse);
        } catch (Exception e) {
            throw new BadSqlGrammarException("Get warehouse failed");
        }
    }

    @Override
    public Void deleteWarehouse(String maKho) {
        if (!warehouseMapper.checkWarehouseExists(maKho)) {
            throw new ResourceNotFoundException("Warehouse", "maKho", maKho);
        }

        try {
            warehouseMapper.deleteWarehouse(maKho);
            return null;
        } catch (Exception e) {
            logger.error("Delete warehouse failed: {}", e.getMessage());
            throw new BadSqlGrammarException("Delete warehouse failed");
        }
    }

    @Override
    public void addWarehouse(WarehouseDto warehouseDto) {

    }

    @Override
    public void updateWarehouse(WarehouseDto warehouseDto) {

    }
}
