package org.example.wms_be.mapper.warehouse;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.data.response.WarehouseDetailConsignmentResp;
import org.example.wms_be.entity.warehouse.ZoneDetail;

import java.util.List;

@Mapper
public interface ZoneDetailMapper {
    List<ZoneDetail> getAllZoneDetails();

    int insertZoneDetail(ZoneDetail zoneDetail);

    int updateZoneDetail(ZoneDetail zoneDetail);

    int deleteZoneDetail(String maChiTietKhuVuc);

    boolean checkZoneDetailExists(String maChiTietKhuVuc);

    ZoneDetail getZoneDetailByMaChiTietKhuVuc(String maChiTietKhuVuc);

    WarehouseDetailConsignmentResp getWarehouseDetailByProduct(Integer sysIdSanPham);
}
