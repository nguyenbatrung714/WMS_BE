package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.ZoneConverter;
import org.example.wms_be.data.dto.ZoneDto;
import org.example.wms_be.entity.warehouse.Zone;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.warehouse.WarehouseMapper;
import org.example.wms_be.mapper.warehouse.ZoneMapper;
import org.example.wms_be.service.ZoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    private static final Logger logger = LoggerFactory.getLogger(ZoneServiceImpl.class);
    private final ZoneMapper zoneMapper;
    private final ZoneConverter zoneConverter;
    private final WarehouseMapper warehouseMapper;

    @Override
    public List<ZoneDto> getAllZones() {
        return zoneMapper.getAllZones()
                .stream()
                .map(zoneConverter::toZoneDto)
                .toList();
    }

    @Override
    public ZoneDto saveZone(ZoneDto zoneDto) {
        if (!warehouseMapper.checkWarehouseExists(zoneDto.getMaKho())) {
            throw new ResourceNotFoundException("Warehouse", "maKho", zoneDto.getMaKho());
        }

        Zone zone = zoneConverter.toZone(zoneDto);

        try {
            if (zoneMapper.checkZoneExists(zoneDto.getMaKhuVuc())) {
                zoneMapper.updateZone(zone);
                zone = zoneMapper.getZoneByMaKhuVuc(zoneDto.getMaKhuVuc());
            } else {
                zoneMapper.insertZone(zone);
            }
        } catch (Exception e) {
            logger.error("Insert or update zone failed: {}", e.getMessage());
            throw new BadSqlGrammarException("Save zone failed");
        }

        return zoneConverter.toZoneDto(zone);
    }

    @Override
    public ZoneDto getZoneById(String maKhuVuc) {
        if (!zoneMapper.checkZoneExists(maKhuVuc)) {
            throw new ResourceNotFoundException("Zone", "maKhuVuc", maKhuVuc);
        }

        try {
            Zone zone = zoneMapper.getZoneByMaKhuVuc(maKhuVuc);
            return zoneConverter.toZoneDto(zone);
        } catch (Exception e) {
            throw new BadSqlGrammarException("Get zone failed");
        }
    }

    @Override
    public Void deleteZone(String maKhuVuc) {
        if (!zoneMapper.checkZoneExists(maKhuVuc)) {
            throw new ResourceNotFoundException("Zone", "maKhuVuc", maKhuVuc);
        }

        try {
            zoneMapper.deleteZone(maKhuVuc);
            return null;
        } catch (Exception e) {
            logger.error("Delete zone failed: {}", e.getMessage());
            throw new BadSqlGrammarException("Delete zone failed");
        }
    }

    @Override
    public void addZone(ZoneDto zoneDto) {
        if (!warehouseMapper.checkWarehouseExists(zoneDto.getMaKho())) {
            throw new ResourceNotFoundException("Warehouse", "maKho", zoneDto.getMaKho());
        }

        Zone zone = zoneConverter.toZone(zoneDto);

        try {
            zoneMapper.insertZone(zone);
        } catch (Exception e) {
            logger.error("Insert zone failed: {}", e.getMessage());
            throw new BadSqlGrammarException("Insert zone failed");
        }
    }

    @Override
    public void updateZone(ZoneDto zoneDto) {
        if (!warehouseMapper.checkWarehouseExists(zoneDto.getMaKho())) {
            throw new ResourceNotFoundException("Warehouse", "maKho", zoneDto.getMaKho());
        }

        Zone zone = zoneConverter.toZone(zoneDto);

        try {
            zoneMapper.updateZone(zone);
        } catch (Exception e) {
            logger.error("Update zone failed: {}", e.getMessage());
            throw new BadSqlGrammarException("Update zone failed");
        }
    }
}

