package org.example.wms_be.converter.inbound;

import org.example.wms_be.data.dto.PurchaseRequestDto;
import org.example.wms_be.entity.inbound.PurchaseRequestIb;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseRequestIbConverter {
    PurchaseRequestIb toPurchaseRequest(PurchaseRequestDto purchaseRequestDto);
    PurchaseRequestDto toPurchaseRequestDto(PurchaseRequestIb purchaseRequestIb);
}
