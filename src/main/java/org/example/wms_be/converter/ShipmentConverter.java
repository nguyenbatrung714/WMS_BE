package org.example.wms_be.converter;

import org.example.wms_be.data.dto.ShipmentDto;
import org.example.wms_be.entity.Shipment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShipmentConverter {
    Shipment toShipment(ShipmentDto shipmentDto);
    ShipmentDto toShipmentDto(Shipment shipment);
}
