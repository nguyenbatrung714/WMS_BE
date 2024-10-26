package org.example.wms_be.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.ShipmentConverter;

import org.example.wms_be.data.dto.ShipmentDto;
import org.example.wms_be.entity.Shipment;
import org.example.wms_be.entity.product.CategoryProd;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.ShipmentMapper;
import org.example.wms_be.mapper.product.ProductMapper;
import org.example.wms_be.mapper.warehouse.ZoneDetailMapper;
import org.example.wms_be.service.ShipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {
    private static final Logger logger = LoggerFactory.getLogger(ShipmentServiceImpl.class);
    private final ShipmentMapper shipmentMapper;
    private final ProductMapper productMapper;
    private final ZoneDetailMapper zoneDetailMapper;
    private final ShipmentConverter shipmentConverter;

    @Override
    public List<ShipmentDto> getAllShipment() {
        return
               shipmentMapper.getAllShipment()
                        .stream()
                        .map(shipmentConverter::toShipmentDto)
                        .toList();
    }

    @Override
    public ShipmentDto saveShipment(ShipmentDto shipmentDto) {
        if (!productMapper.checkProductExists(shipmentDto.getSysIdSanPham())) {
            throw new ResourceNotFoundException("Shipment", "maSanPham", shipmentDto.getSysIdSanPham().toString());
        }
        else if(!zoneDetailMapper.checkZoneDetailExists(shipmentDto.getMaChiTietKhuVuc())){
            throw new ResourceNotFoundException("Shipment", "maChiTietKhuVuc", shipmentDto.getMaChiTietKhuVuc());
        }

        Shipment shipment = shipmentConverter.toShipment(shipmentDto);

        try {
            if (shipmentMapper.checkShipmentExists(shipmentDto.getMaLo())) {
                shipmentMapper.updateShipment(shipment);
                shipment = shipmentMapper.getShipmentByMaLo(shipment.getMaLo());
            } else {
                shipmentMapper.insertShipment(shipment);
            }
        } catch (Exception e) {
            throw new BadSqlGrammarException("Save shipment failed");
        }

        return shipmentConverter.toShipmentDto(shipment);
    }

    @Override
    public Void deleteShipment(String maLo) {
        if (!shipmentMapper.checkShipmentExists(maLo)) {
            throw new ResourceNotFoundException("Shipment", "maLo", maLo);
        }

        try {
            shipmentMapper.deleteShipment(maLo);
            return null;
        } catch (Exception e) {
            logger.error("Error when deleting shipment: {}", e.getMessage());
            throw new BadSqlGrammarException("Error when deleting shipment");
        }
    }

    @Override
    public ShipmentDto getAllShipmentById(String maLo) {
        if (!shipmentMapper.checkShipmentExists(maLo)) {
            throw new ResourceNotFoundException("Shipment", "maLo", maLo);
        }
        try {
          Shipment shipment= shipmentMapper.getShipmentByMaLo(maLo);
          return shipmentConverter.toShipmentDto(shipment);
        }  catch (Exception e) {
            throw new BadSqlGrammarException("Get shipment failed");
        }
    }
}
