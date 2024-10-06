package org.example.wms_be.converter;

import org.example.wms_be.data.dto.PurchaseRequestDto;
import org.example.wms_be.entity.purchase.PurchaseRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseRequestConverter {
    PurchaseRequest toPurchaseRequest(PurchaseRequestDto purchaseRequestDto);
    PurchaseRequestDto toPurchaseRequestDto(PurchaseRequest purchaseRequest);
}
