package org.example.wms_be.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.Shipment;


import java.util.List;

@Mapper
public interface ShipmentMapper {
    List<Shipment> getAllShipment();

    int insertShipment(Shipment shipment);

    int updateShipment(Shipment shipment);

    int deleteShipment(String maLo);

    boolean checkShipmentExists(String maLo);

    Shipment getShipmentByMaLo(String maLo);
}
