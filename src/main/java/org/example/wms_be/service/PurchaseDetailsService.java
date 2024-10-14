package org.example.wms_be.service;

import org.example.wms_be.data.dto.PurchaseRequestDetailsDto;

import java.util.List;

public interface PurchaseDetailsService {
    List<PurchaseRequestDetailsDto> getAllPurchaseDetails();
}
