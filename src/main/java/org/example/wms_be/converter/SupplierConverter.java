package org.example.wms_be.converter;

import org.example.wms_be.data.dto.SupplierDto;
import org.example.wms_be.entity.customer.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierConverter {
    SupplierDto toSupLierDto (Supplier supplier);
    Supplier toSupplier (SupplierDto supplierDto);
}
