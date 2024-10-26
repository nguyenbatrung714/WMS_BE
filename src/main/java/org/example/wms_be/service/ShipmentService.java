package org.example.wms_be.service;

import com.github.pagehelper.PageInfo;
import org.example.wms_be.data.dto.ShipmentDto;
import org.example.wms_be.data.request.ProductReq;
import org.example.wms_be.data.response.ProductResp;
import org.example.wms_be.entity.Shipment;

import java.util.List;

public interface ShipmentService {
    List<ShipmentDto> getAllShipment();

    ShipmentDto saveShipment(ShipmentDto shipment);

    Void deleteShipment(String maLo);

    ShipmentDto getAllShipmentById(String maLo);
}
