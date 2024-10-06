package org.example.wms_be.service;

import org.example.wms_be.data.dto.PurchaseDetailsDto;

import java.util.List;

public interface PurchaseDetailsService {
    List<PurchaseDetailsDto> getAllPurchaseDetails();
}
