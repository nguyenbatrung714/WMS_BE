package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.inbound.PurchaseDetailsIbConverter;
import org.example.wms_be.converter.inbound.PurchaseOrderIbConverter;
import org.example.wms_be.data.request.PurchaseDetailsIbReq;
import org.example.wms_be.data.request.PurchaseOrderIbReq;
import org.example.wms_be.entity.inbound.PurchaseDetailsIb;
import org.example.wms_be.entity.inbound.PurchaseOrderIb;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.inbound.PurchaseDetailsIbMapper;
import org.example.wms_be.mapper.inbound.PurchaseOrderIbMapper;
import org.example.wms_be.service.PurchaseOrderIbService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PurchaseOrderIbServiceImpl implements PurchaseOrderIbService {
    private final PurchaseOrderIbConverter purchaseOrderIbConverter;
    private final PurchaseDetailsIbConverter purchaseDetailsIbConverter;
    private final PurchaseOrderIbMapper purchaseOrderIbMapper;
    private final PurchaseDetailsIbMapper purchaseDetailsIbMapper;

    @Override
    public PurchaseOrderIbReq createPurchaseOrder(PurchaseOrderIbReq purchaseOrderIbReq) {
        // Get PR details by MaPR
        if (purchaseOrderIbReq.getMaPR() != null) {
            try {
                List<PurchaseDetailsIb> purchaseRequestDetailIbs =
                        purchaseDetailsIbMapper.getPRDetailsIbByMaPR(purchaseOrderIbReq.getMaPR());

                if (purchaseRequestDetailIbs == null) {
                    throw new ResourceNotFoundException("PR Detail", "MaPR", purchaseOrderIbReq.getMaPR());
                }

                // Update PR details when PO comfirmed
                purchaseDetailsIbMapper.updateDetailsIbFromPR(purchaseOrderIbReq.getMaPR(), purchaseOrderIbReq.getMaPO());
                purchaseOrderIbReq.setChiTietNhapHang(purchaseRequestDetailIbs.stream().map(
                        prDetail -> {
                            prDetail.setMaPO(purchaseOrderIbReq.getMaPO());
                            return purchaseDetailsIbConverter.toPurchaseDetailsIbReq(prDetail);
                        }).toList());

                PurchaseOrderIb purchaseOrderIb = purchaseOrderIbConverter.toPurchaseOrderIb(purchaseOrderIbReq);

                purchaseOrderIbMapper.insertPurchaseOrderIb(purchaseOrderIb);

                return purchaseOrderIbConverter.toPurchaseOrderIbReq(purchaseOrderIb);
            } catch (Exception e) {
                throw new BadSqlGrammarException("Error getting PR detail by MaPR: " + e.getMessage());
            }
        } else {
            try {
                List<PurchaseDetailsIb> purchaseRequestDetailIbs = createPurchaseDetails(purchaseOrderIbReq.getChiTietNhapHang());
                purchaseOrderIbReq.setChiTietNhapHang(purchaseRequestDetailIbs.stream().map(
                        prDetail -> {
                            prDetail.setMaPO(purchaseOrderIbReq.getMaPO());
                            return purchaseDetailsIbConverter.toPurchaseDetailsIbReq(prDetail);
                        }).toList());

                // Create PO
                PurchaseOrderIb purchaseOrderIb = createPO(purchaseOrderIbReq);
                return purchaseOrderIbConverter.toPurchaseOrderIbReq(purchaseOrderIb);
            } catch (Exception e) {
                throw new BadSqlGrammarException("Error creating purchase order: " + e.getMessage());
            }
        }
    }

    private List<PurchaseDetailsIb> createPurchaseDetails(List<PurchaseDetailsIbReq> purchaseDetailsIbReqs) {
        purchaseDetailsIbReqs.forEach(prDetail -> {
            PurchaseDetailsIb purchaseDetailsIb = purchaseDetailsIbConverter.toPurchaseDetailsIb(prDetail);
            purchaseDetailsIbMapper.insertPurchaseDetails(purchaseDetailsIb);
        });
        return purchaseDetailsIbReqs.stream()
                .map(purchaseDetailsIbConverter::toPurchaseDetailsIb)
                .toList();
    }

    private PurchaseOrderIb createPO(PurchaseOrderIbReq purchaseOrderIbReq) {
        PurchaseOrderIb purchaseOrderIb = purchaseOrderIbConverter.toPurchaseOrderIb(purchaseOrderIbReq);
        purchaseOrderIb.setChiTietNhapHang(createPurchaseDetails(purchaseOrderIbReq.getChiTietNhapHang()));
        purchaseOrderIbMapper.insertPurchaseOrderIb(purchaseOrderIb);
        return purchaseOrderIb;
    }
}
