package org.example.wms_be.service;

import org.example.wms_be.data.dto.SupplierDto;

import java.util.List;

public interface SupplierService {
    SupplierDto saveSupplier(SupplierDto supplierDto);
    List<SupplierDto> getAllSuppliers();
    SupplierDto getSupplierById(Integer maNhaCungCap);
    Void deleteSupplier(Integer maNhaCungCap);
}
