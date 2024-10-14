package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.PurchaseDetailsConverter;
import org.example.wms_be.converter.PurchaseOrderConverter;
import org.example.wms_be.data.dto.PurchaseRequestDetailsDto;
import org.example.wms_be.data.request.PurchaseOrderReq;
import org.example.wms_be.entity.purchase.PurchaseOrder;
import org.example.wms_be.entity.purchase.PurchaseRequestDetails;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.purchase.PurchaseDetailsMapper;
import org.example.wms_be.mapper.purchase.PurchaseOrderMapper;
import org.example.wms_be.service.PurchaseOrderService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderConverter purchaseOrderConverter;
    private final PurchaseDetailsConverter purchaseDetailsConverter;
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseDetailsMapper purchaseDetailsMapper;

    @Override
    public PurchaseOrderReq createPurchaseOrder(PurchaseOrderReq purchaseOrderReq) {
        // Get PR details by MaPR
        if (purchaseOrderReq.getMaPR() != null) {
            try {
                List<PurchaseRequestDetails> purchaseRequestDetails =
                        purchaseDetailsMapper.getPRDetailByMaPR(purchaseOrderReq.getMaPR());

                if (purchaseRequestDetails == null) {
                    throw new ResourceNotFoundException("PR Detail", "MaPR", purchaseOrderReq.getMaPR());
                }

                // Update PR details when PO comfirmed
                purchaseDetailsMapper.updateDetailsFromPR(purchaseOrderReq.getMaPR(), purchaseOrderReq.getMaPO());
                purchaseOrderReq.setChiTietDonHang(purchaseRequestDetails.stream().map(
                        prDetail -> {
                            prDetail.setMaPO(purchaseOrderReq.getMaPO());
                            return purchaseDetailsConverter.toPurchaseDetailsDto(prDetail);
                        }).toList());

                PurchaseOrder purchaseOrder = purchaseOrderConverter.toPurchaseOrder(purchaseOrderReq);

                purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);

                return purchaseOrderConverter.toPurchaseOrderReq(purchaseOrder);
            } catch (Exception e) {
                throw new BadSqlGrammarException("Error getting PR detail by MaPR: " + e.getMessage());
            }
        } else {
            try {
                List<PurchaseRequestDetails> purchaseRequestDetails = createPurchaseDetails(purchaseOrderReq.getChiTietDonHang());
                purchaseOrderReq.setChiTietDonHang(purchaseRequestDetails.stream().map(
                        prDetail -> {
                            prDetail.setMaPO(purchaseOrderReq.getMaPO());
                            return purchaseDetailsConverter.toPurchaseDetailsDto(prDetail);
                        }).toList());

                // Create PO
                PurchaseOrder purchaseOrder = createPO(purchaseOrderReq);
                return purchaseOrderConverter.toPurchaseOrderReq(purchaseOrder);
            } catch (Exception e) {
                throw new BadSqlGrammarException("Error creating purchase order: " + e.getMessage());
            }
        }
    }

    private List<PurchaseRequestDetails> createPurchaseDetails(List<PurchaseRequestDetailsDto> purchaseRequestDetailsDtos) {
        purchaseRequestDetailsDtos.forEach(prDetail -> {
            PurchaseRequestDetails purchaseRequestDetails = purchaseDetailsConverter.toPurchaseDetails(prDetail);
            purchaseDetailsMapper.insertPurchaseDetails(purchaseRequestDetails);
        });
        return purchaseRequestDetailsDtos.stream()
                .map(purchaseDetailsConverter::toPurchaseDetails)
                .toList();
    }

    private PurchaseOrder createPO(PurchaseOrderReq purchaseOrderReq) {
        PurchaseOrder purchaseOrder = purchaseOrderConverter.toPurchaseOrder(purchaseOrderReq);
        purchaseOrder.setChiTietDonHang(createPurchaseDetails(purchaseOrderReq.getChiTietDonHang()));
        purchaseOrderMapper.insertPurchaseOrder(purchaseOrder);
        return purchaseOrder;
    }
}
