package org.example.wms_be.converter;

import org.example.wms_be.data.dto.WasteProductsDto;
import org.example.wms_be.entity.WasteProducts;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WasteProductsConverter {
    WasteProducts toWasteProducts(WasteProductsDto wasteProductsDto);
    WasteProductsDto toWasteProductsDto(WasteProducts wasteProducts);
}
