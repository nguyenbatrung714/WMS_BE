package org.example.wms_be.converter;

import org.example.wms_be.data.dto.ProductDto;
import org.example.wms_be.entity.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductConverter {

    @Mapping(target = "hinhAnh", ignore = true)
    Product toProduct(ProductDto productDto);
    @Mapping(target = "hinhAnh", ignore = true)
    ProductDto toProductDto(Product product);
}
