package org.example.wms_be.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.data.dto.WasteProductsDto;
import org.example.wms_be.entity.WasteProducts;

import java.util.List;

@Mapper
public interface WasteProductsMapper {
    void insertWasteProducts(WasteProducts wp);
    List<WasteProducts> getAllWasteProducts();
    List<WasteProductsDto> getSoLuongTrongTuan();

}
