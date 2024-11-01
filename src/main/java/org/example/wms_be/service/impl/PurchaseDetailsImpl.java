package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.inbound.PurchaseDetailsIbConverter;
import org.example.wms_be.data.request.PurchaseDetailsIbReq;
import org.example.wms_be.mapper.inbound.PurchaseDetailsIbMapper;
import org.example.wms_be.service.PurchaseDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseDetailsImpl implements PurchaseDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseDetailsImpl.class);
    private final PurchaseDetailsIbMapper purchaseDetailsIbMapper;
    private final PurchaseDetailsIbConverter purchaseDetailsIbConverter;

    @Override
    public List<PurchaseDetailsIbReq> getAllPurchaseDetails() {
        try {
            // lay tat ca doi tuong PurchaseDetails tu purchaseDetailsMapper tra ve 1 list
            return purchaseDetailsIbMapper.getAllPurchaseDetails()
                    //purchasedetails -> Stream<PurchaseDetails> chuyen thanh 1 luong xu li tuan tu
                    .stream()
                    // chuyen doi tuong PurchaseDetails thanh PurchaseDetailsDto
                    .map(purchaseDetailsIbConverter::toPurchaseDetailsIbReq)
                    // thu thap cac phan tu thanh 1 list
                    .toList();
        } catch (Exception e) {
            logger.error("Get all purchase request failed: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
