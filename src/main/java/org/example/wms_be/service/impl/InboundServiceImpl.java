package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.InboundConverter;
import org.example.wms_be.converter.inbound.PurchaseDetailsIbConverter;
import org.example.wms_be.data.request.InboundReq;
import org.example.wms_be.entity.inbound.Inbound;
import org.example.wms_be.entity.inbound.PurchaseDetailsIb;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.mapper.inbound.PurchaseDetailsIbMapper;
import org.example.wms_be.mapper.inbound.PurchaseOrderIbMapper;
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
    private final PurchaseDetailsIbMapper purchaseDetailsIbMapper;
    private final InboundConverter inboundConverter;
    private final PurchaseDetailsIbConverter purchaseDetailsIbConverter;
    private final PurchaseOrderIbMapper purchaseOrderIbMapper;

    @Override
    public InboundReq createInbound(InboundReq inboundReq) {
        try {
            if (!purchaseOrderIbMapper.purchaseOrderIbExistByMaPO(inboundReq.getMaPO())) {
                throw new IllegalArgumentException("PO not found");
            }

            List<PurchaseDetailsIb> purchaseDetailsIbs = purchaseDetailsIbMapper.getPurchaseDetailsIbByMaPO(inboundReq.getMaPO());

            if (purchaseDetailsIbs == null || purchaseDetailsIbs.isEmpty()) {
                throw new IllegalArgumentException("PO Detail null");
            }

            Inbound inbound = inboundConverter.toInbound(inboundReq);
            inboundMapper.insertInbound(inbound);

            String maInBound = inbound.getMaInBound();

            // Update PR details when PO comfirmed
            purchaseDetailsIbMapper.updateDetailsIbFromPO(inboundReq.getMaPO(), maInBound);

//            inboundReq.setChiTietNhapHang(purchaseDetailsIbs.stream().map(
//                    inboundDetail -> {
//                        inboundDetail.setMaInBound(maInBound);
//                        return purchaseDetailsIbConverter.toPurchaseDetailsIbReq(inboundDetail);
//                    }).toList());

            purchaseDetailsIbs.forEach(purchaseDetailsIb -> purchaseDetailsIb.setMaInBound(maInBound));
            inbound.setChiTietNhapHang(purchaseDetailsIbs);

            return inboundConverter.toInboundReq(inbound);
        } catch (Exception e) {
            throw new BadSqlGrammarException("Bad sql: " + e.getMessage());
        }
    }

    @Override
    public List<InboundReq> getAllInbound() {
        try {
            List<Inbound> inbounds = inboundMapper.getAllInbound();

            return inbounds.stream().map(inbound -> {
                        inbound.setChiTietNhapHang(purchaseDetailsIbMapper.getPurchaseDetailsIbByMaIb(inbound.getMaInBound()));
                        return inboundConverter.toInboundReq(inbound);
                    })
                    .filter(inboundReq -> inboundReq.getChiTietNhapHang() != null && !inboundReq.getChiTietNhapHang().isEmpty())
                    .toList();
        } catch (Exception e) {
            throw new BadSqlGrammarException("Bad sql: " + e.getMessage());
        }
    }
}
