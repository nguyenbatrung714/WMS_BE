package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.InboundConverter;
import org.example.wms_be.converter.PurchaseDetailsConverter;
import org.example.wms_be.data.request.InboundReq;
import org.example.wms_be.entity.purchase.Inbound;
import org.example.wms_be.entity.purchase.PurchaseRequestDetails;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.mapper.purchase.InboundMapper;
import org.example.wms_be.service.InboundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InboundServiceImpl implements InboundService {
    private static final Logger logger = LoggerFactory.getLogger(InboundServiceImpl.class);
    private final InboundMapper inboundMapper;
    private final PurchaseDetailsMapper purchaseDetailsMapper;
    private final InboundConverter inboundConverter;
    private final PurchaseDetailsConverter purchaseOrderConverter;
    private final PurchaseOrderMapper purchaseOrderMapper;

    @Override
    public InboundReq createInbound(InboundReq inboundReq) {
        if (purchaseOrderMapper.selectPurchaseOrderByMaPO(inboundReq.getMaPO()) == null) {
            throw new IllegalArgumentException("PO not found");
        }

        try {
            List<PurchaseRequestDetails> purchaseRequestDetails =
                    purchaseDetailsMapper.getPRDetailByMaPO(inboundReq.getMaPO());

            if (purchaseRequestDetails == null || purchaseRequestDetails.isEmpty()) {
                throw new IllegalArgumentException("PO Detail null");
            }

            // Update PR details when PO comfirmed
            purchaseDetailsMapper.updateDetailsFromPO(inboundReq.getMaPO(), inboundReq.getMaInBound());

            inboundReq.setChiTietDonHang(purchaseRequestDetails.stream().map(
                    inboundDetail -> {
                        inboundDetail.setMaInBound(inboundReq.getMaInBound());
                        return purchaseOrderConverter.toPurchaseDetailsDto(inboundDetail);
                    }).toList());

            Inbound inbound = inboundConverter.toInbound(inboundReq);
            inboundMapper.insertInbound(inbound);

            return inboundConverter.toInboundReq(inbound);
        } catch (Exception e) {
            throw new BadSqlGrammarException("Error getting PR detail by MaPR: " + e.getMessage());
        }
    }
}
