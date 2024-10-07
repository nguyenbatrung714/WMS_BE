package org.example.wms_be.converter;

import org.example.wms_be.data.dto.CategoryProdDto;
import org.example.wms_be.entity.product.CategoryProd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryProdConverter {
    CategoryProdDto toCategoryProdDto (CategoryProd categoryProd);
    CategoryProd toCategoryProd (CategoryProdDto categoryProdDto);
}
