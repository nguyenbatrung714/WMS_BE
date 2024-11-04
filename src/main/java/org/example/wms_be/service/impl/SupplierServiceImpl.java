package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.SupplierConverter;
import org.example.wms_be.data.dto.SupplierDto;
import org.example.wms_be.entity.customer.Supplier;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.customer.SupplierMapper;
import org.example.wms_be.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private static final Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);
    private final SupplierMapper supplierMapper;
    private final SupplierConverter supplierConverter;
    @Override
    public SupplierDto saveSupplier(SupplierDto supplierDto) {
        Supplier supplier= supplierConverter.toSupplier(supplierDto);
        try{
            if(supplierMapper.checkSupplierExits(supplierDto.getSysIdNhaCungCap())){
                supplierMapper.updateSupplier(supplier);
                supplier=supplierMapper.getSupplierById(supplier.getSysIdNhaCungCap());
            }else{
                supplierMapper.insertSupplier(supplier);
            }


        }catch(Exception e) {
            logger.error("Insert supplier failed: {}", e.getMessage());
            throw new BadSqlGrammarException("Insert supplier failed");
        }
        return supplierConverter.toSupLierDto(supplier);
    }

    @Override
    public List<SupplierDto> getAllSuppliers() {
        return supplierMapper.getAllSupplier()
                .stream()
                .map(supplierConverter ::toSupLierDto)
                .toList();
    }

    @Override
    public SupplierDto getSupplierById(String maNhaCungCap) {
        if (!supplierMapper.checkSupplierExits(maNhaCungCap)) {
            throw new ResourceNotFoundException("Supplier", "maNhaCungCap", maNhaCungCap);
        }
        try {
            Supplier supplier = supplierMapper.getSupplierById(maNhaCungCap);
            return supplierConverter.toSupLierDto(supplier);
        } catch (Exception e) {
            throw new BadSqlGrammarException("Get supplier failed");
        }
    }

    @Override
    public Void deleteSupplier(String maNhaCungCap) {
       if(!supplierMapper.checkSupplierExits(maNhaCungCap)) {
           throw new ResourceNotFoundException("Supplier", "maNhaCungCap", maNhaCungCap);
       }
           try{
             supplierMapper.deleteSupplier(maNhaCungCap);
               return null;
       }catch(Exception e){
               logger.error("Delete supplier failed: {}", e.getMessage());
               throw new BadSqlGrammarException("Delete supplier failed");

        }
    }
}
