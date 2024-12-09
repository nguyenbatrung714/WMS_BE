package org.example.wms_be.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.WasteProducts;
@Mapper
public interface WasteProductsMapper {
    void insertWasteProducts(WasteProducts wp);
}
