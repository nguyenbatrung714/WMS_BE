package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.inventory.ConsignmentConverter;
import org.example.wms_be.data.request.ConsignmentReq;
import org.example.wms_be.entity.inventory.Consignment;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.inventory.ConsignmentMapper;
import org.example.wms_be.mapper.product.ProductMapper;
import org.example.wms_be.mapper.warehouse.ZoneDetailMapper;
import org.example.wms_be.service.ConsignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsignmentServiceImpl implements ConsignmentService {
    private static final Logger logger =  LoggerFactory.getLogger(ConsignmentServiceImpl.class);
    private final ConsignmentMapper consignmentMapper;
    private final ProductMapper productMapper;
    private final ZoneDetailMapper zoneDetailMapper;
    private final ConsignmentConverter consignmentConverter;
    @Override
    public List<ConsignmentReq> getAllConsignment() {
        return
                consignmentMapper.getAllConsignment()
                        .stream()
                        .map(consignmentConverter::toConsignmentReq)
                        .toList();
    }

    @Override
    public ConsignmentReq saveConsignment(ConsignmentReq consignmentReq) {
        if (!productMapper.checkProductExists(consignmentReq.getSysIdSanPham())) {
            throw new ResourceNotFoundException("Consignment", "maSanPham", consignmentReq.getSysIdSanPham().toString());
        }
        else if(!zoneDetailMapper.checkZoneDetailExists(consignmentReq.getMaChiTietKhuVuc())){
            throw new ResourceNotFoundException("Consignment", "maChiTietKhuVuc", consignmentReq.getMaChiTietKhuVuc());
        }
        Consignment consignment = consignmentConverter.toConsignment(consignmentReq);
        try {
            if (consignmentMapper.checkConsignmentExists(consignmentReq.getMaLo())) {
                consignmentMapper.updateConsignment(consignment);
                consignment = consignmentMapper.getConsignmentByMaLo(consignment.getMaLo());
            } else {
                consignmentMapper.insertConsignment(consignment);
            }
        } catch (Exception e) {
            throw new BadSqlGrammarException("Save consignment failed");
        }
        return consignmentConverter.toConsignmentReq(consignment);
    }

    @Override
    public Void deleteConsignment(String maLo) {
        if (!consignmentMapper.checkConsignmentExists(maLo)) {
            throw new ResourceNotFoundException("Consignment", "maLo", maLo);
        }
        try {
            consignmentMapper.deleteConsignment(maLo);
            return null;
        } catch (Exception e) {
            logger.error("Error when deleting Consignment: {}", e.getMessage());
            throw new BadSqlGrammarException("Error when deleting Consignment");
        }
    }

    @Override
    public ConsignmentReq getAllConsignmentById(String maLo) {
        if (!consignmentMapper.checkConsignmentExists(maLo)) {
            throw new ResourceNotFoundException("Consignment", "maLo", maLo);
        }
        try {
            Consignment consignment= consignmentMapper.getConsignmentByMaLo(maLo);
            return consignmentConverter.toConsignmentReq(consignment);
        }  catch (Exception e) {
            throw new BadSqlGrammarException("Get Consignment failed");
        }

    }
}
