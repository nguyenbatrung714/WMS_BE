package org.example.wms_be.converter;

import org.example.wms_be.data.dto.ProductDto;
import org.example.wms_be.data.request.ProductReq;
import org.example.wms_be.data.response.ProductResp;
import org.example.wms_be.entity.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductConverter {
    @Mapping(target = "hinhAnh", ignore = true)
    Product toProduct(ProductReq productRequest);

    @Mapping(source = "hinhAnh", target = "hinhAnhUrl")
    ProductResp toProductResp(Product product);
}
