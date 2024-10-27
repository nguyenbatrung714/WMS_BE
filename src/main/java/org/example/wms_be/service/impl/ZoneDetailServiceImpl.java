package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.ZoneDetailConverter;
import org.example.wms_be.data.dto.ZoneDetailDto;
import org.example.wms_be.entity.warehouse.ZoneDetail;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.warehouse.ZoneDetailMapper;
import org.example.wms_be.mapper.warehouse.ZoneMapper;
import org.example.wms_be.service.ZoneDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneDetailServiceImpl implements ZoneDetailService {
    private static final Logger logger = LoggerFactory.getLogger(ZoneDetailServiceImpl.class);
    private final ZoneMapper zoneMapper;
    private final ZoneDetailMapper zoneDetailMapper;
    private final ZoneDetailConverter zoneDetailConverter;

    @Override
    public List<ZoneDetailDto> getAllZoneDetails() {
        return zoneDetailMapper.getAllZoneDetails()
                .stream()
                .map(zoneDetailConverter::toZoneDetailDto)
                .toList();
    }

    @Override
    public ZoneDetailDto saveZoneDetail(ZoneDetailDto zoneDetailDto) {
        if (!zoneMapper.checkZoneExists(zoneDetailDto.getMaKhuVuc())) {
            throw new ResourceNotFoundException("Zone", "maKhuVuc", zoneDetailDto.getMaKhuVuc());
        }

        ZoneDetail zoneDetail = zoneDetailConverter.toZoneDetail(zoneDetailDto);

        try {
            if (zoneDetailMapper.checkZoneDetailExists(zoneDetailDto.getMaChiTietKhuVuc())) {
                zoneDetailMapper.updateZoneDetail(zoneDetail);
                zoneDetail = zoneDetailMapper.getZoneDetailByMaChiTietKhuVuc(zoneDetailDto.getMaChiTietKhuVuc());
            } else {
                zoneDetailMapper.insertZoneDetail(zoneDetail);
            }
        } catch (Exception e) {
            logger.error("Error when saving zone detail: {}", e.getMessage());
            throw new BadSqlGrammarException("Error when saving zone detail");
        }

        return zoneDetailConverter.toZoneDetailDto(zoneDetail);
    }

    @Override
    public ZoneDetailDto getZoneDetailByMaChiTietKhuVuc(String maChiTietKhuVuc) {
        if (!zoneDetailMapper.checkZoneDetailExists(maChiTietKhuVuc)) {
            throw new ResourceNotFoundException("ZoneDetail", "maChiTietKhuVuc", maChiTietKhuVuc);
        }

        try {
            ZoneDetail zoneDetail = zoneDetailMapper.getZoneDetailByMaChiTietKhuVuc(maChiTietKhuVuc);
            return zoneDetailConverter.toZoneDetailDto(zoneDetail);
        } catch (Exception e) {
            throw new BadSqlGrammarException("Get warehouse failed");
        }
    }

    @Override
    public void deleteZoneDetail(String maChiTietKhuVuc) {
        if (!zoneDetailMapper.checkZoneDetailExists(maChiTietKhuVuc)) {
            throw new ResourceNotFoundException("ZoneDetail", "maChiTietKhuVuc", maChiTietKhuVuc);
        }

        try {
            zoneDetailMapper.deleteZoneDetail(maChiTietKhuVuc);
        } catch (Exception e) {
            logger.error("Error when deleting zone detail: {}", e.getMessage());
            throw new BadSqlGrammarException("Error when deleting zone detail");
        }
    }

    @Override
    public void addZoneDetail(ZoneDetailDto zoneDetailDto) {
        ZoneDetail zoneDetail = zoneDetailConverter.toZoneDetail(zoneDetailDto);

        try {
            zoneDetailMapper.insertZoneDetail(zoneDetail);
        } catch (Exception e) {
            logger.error("Error when adding zone detail: {}", e.getMessage());
            throw new BadSqlGrammarException("Error when adding zone detail");
        }
    }

    @Override
    public void updateZoneDetail(ZoneDetailDto zoneDetailDto) {
        ZoneDetail zoneDetail = zoneDetailConverter.toZoneDetail(zoneDetailDto);

        if (!zoneDetailMapper.checkZoneDetailExists(zoneDetailDto.getMaChiTietKhuVuc())) {
            throw new ResourceNotFoundException("ZoneDetail", "maChiTietKhuVuc", zoneDetailDto.getMaChiTietKhuVuc());
        }

        try {
            zoneDetailMapper.updateZoneDetail(zoneDetail);
        } catch (Exception e) {
            logger.error("Error when updating zone detail: {}", e.getMessage());
            throw new BadSqlGrammarException("Error when updating zone detail");
        }
    }
}
