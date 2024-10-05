package org.example.wms_be.mapper.warehouse;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.warehouse.Zone;

import java.util.List;

@Mapper
public interface ZoneMapper {
    List<Zone> getAllZones();

    int insertZone(Zone zone);

    int updateZone(Zone zone);

    int deleteZone(String maKhuVuc);

    boolean checkZoneExists(String maKhuVuc);

    Zone getZoneByMaKhuVuc(String maKhuVuc);
}
