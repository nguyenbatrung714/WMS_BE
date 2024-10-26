package org.example.wms_be.converter;

import org.example.wms_be.data.dto.PurchaseRequestDetailsDto;
import org.example.wms_be.entity.purchase.PurchaseRequestDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PurchaseDetailsConverter {
    PurchaseRequestDetails toPurchaseDetails(PurchaseRequestDetailsDto purchaseDetailsDto);
    PurchaseRequestDetailsDto toPurchaseDetailsDto(PurchaseRequestDetails purchaseDetails);

}
