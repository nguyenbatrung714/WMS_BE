package org.example.wms_be.converter;

import org.example.wms_be.data.dto.PurchaseDetailsDto;
import org.example.wms_be.entity.purchase.PurchaseDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseDetailsConverter {
    PurchaseDetails toPurchaseDetails(PurchaseDetailsDto purchaseDetailsDto);
    PurchaseDetailsDto toPurchaseDetailsDto(PurchaseDetails purchaseDetails);

}
