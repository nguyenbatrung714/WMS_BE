package org.example.wms_be.service;

import org.example.wms_be.data.dto.WasteProductsDto;

public interface WasteProductsService {
    WasteProductsDto insertWaste(Integer sysIdTonKho,String lyDo);
}
