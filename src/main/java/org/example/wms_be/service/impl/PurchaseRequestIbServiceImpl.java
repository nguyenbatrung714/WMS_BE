package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.inbound.PurchaseDetailsIbConverter;
import org.example.wms_be.converter.inbound.PurchaseRequestIbConverter;
import org.example.wms_be.data.request.PurchaseDetailsIbReq;
import org.example.wms_be.data.request.PurchaseRequestIbReq;
import org.example.wms_be.data.response.inbound.PurchaseRequestDetailsIbResp;
import org.example.wms_be.data.response.inbound.PurchaseRequestIbResp;
import org.example.wms_be.entity.inbound.PurchaseDetailsIb;
import org.example.wms_be.entity.inbound.PurchaseRequestIb;
import org.example.wms_be.mapper.account.UserMapper;
import org.example.wms_be.mapper.inbound.PurchaseDetailsIbMapper;
import org.example.wms_be.mapper.inbound.PurchaseRequestIbMapper;
import org.example.wms_be.service.PurchaseRequestIbService;
import org.example.wms_be.utils.EmailUtil;
import org.example.wms_be.utils.TimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;


@Service
@RequiredArgsConstructor
public class PurchaseRequestIbServiceImpl implements PurchaseRequestIbService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseRequestIbServiceImpl.class);
    private final PurchaseDetailsIbMapper purchaseDetailsIbMapper;
    private final PurchaseRequestIbConverter purchaseRequestIbConverter;
    private final PurchaseDetailsIbConverter purchaseDetailsIbConverter;
    private final UserMapper userMapper;
    private final EmailUtil emailUtil;
    private final PurchaseRequestIbMapper purchaseRequestIbMapper;


    @Override
    public List<PurchaseRequestIbResp> getAllPurchaseRequestIb() {
        try {
            List<PurchaseRequestIbResp> purchaseRequestIbResps = purchaseRequestIbMapper.getAllPurchaseRequestIb();
            return processPurchaseRequestList(purchaseRequestIbResps);
        } catch (Exception e) {
            logger.error("Get all purchase requests failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<PurchaseRequestIbResp> getPurchaseRequestIbByMaPR(String maPR) {
        try {
            List<PurchaseRequestIbResp> purchaseRequestIbResps = purchaseRequestIbMapper.getAllPurchaseRequestIbByMaPR(maPR);
            return processPurchaseRequestList(purchaseRequestIbResps);
        } catch (Exception e) {
            logger.error("Get purchase requests by MaPR failed", e);
            return Collections.emptyList();
        }
    }


    private List<PurchaseRequestIbResp> processPurchaseRequestList(List<PurchaseRequestIbResp> purchaseRequestIbResps) {
        purchaseRequestIbResps.stream()
                .filter(Objects::nonNull)
                .filter(pr -> pr.getMaPR() != null)
                .forEach(pr -> {
                    if (pr.getNgayYeuCau() != null) {
                        // convert ngayYeuCau from yyyy-MM-dd HH:mm:ss to dd/MM/yyyy
                        String ngayYeuCauFormatted = TimeConverter.formatNgayYeuCau(Timestamp.valueOf(pr.getNgayYeuCau()));
                        pr.setNgayYeuCau(ngayYeuCauFormatted);
                    }
                    List<PurchaseRequestDetailsIbResp> chiTietNhapHang = purchaseDetailsIbMapper.getPurchaseRequestById(pr.getMaPR());
                    logger.info("Found {} details for PurchaseRequest: {}", chiTietNhapHang.size(), pr.getMaPR());
                    chiTietNhapHang.forEach(detail -> {
                        if (detail.getNgayNhapDuKien() != null) {
                            String ngayNhapDuKienFormatted = TimeConverter.formatNgayNhap(TimeConverter.parseDateOnly(detail.getNgayNhapDuKien()));
                            detail.setNgayNhapDuKien(ngayNhapDuKienFormatted);
                        }
                    });
                    pr.setChiTietNhapHang(chiTietNhapHang);
                });
        return purchaseRequestIbResps;
    }

    @Override
    @Transactional
    public void savePurchaseRequestWithDetails(PurchaseRequestIbReq purchaseRequestIbReq) {
        PurchaseRequestIb purchaseRequestIb = purchaseRequestIbConverter.toPurchaseRequestIb(purchaseRequestIbReq);
        if(purchaseRequestIbMapper.existById(purchaseRequestIbReq.getSysIdYeuCauNhapHang())){
            purchaseRequestIbMapper.updatePurchaseRequestIb(purchaseRequestIb);
        }else {
            purchaseRequestIbMapper.insertPurchaseRequestIb(purchaseRequestIb);
        }
        // lay maPR
        String maPR = purchaseRequestIbMapper.getMaPrById(purchaseRequestIb.getSysIdYeuCauNhapHang());
        purchaseRequestIb.setMaPR(maPR);
        logger.info("Inserted new PurchaseRequestOb: {} with MaPR: {}", purchaseRequestIb, maPR);
        // luu chi tiet nhap hang
        for (PurchaseDetailsIbReq purchaseDetailsIbReq : purchaseRequestIbReq.getChiTietNhapHang()) {
            PurchaseDetailsIb purchaseDetailsIb = purchaseDetailsIbConverter.toPurchaseDetailsIb(purchaseDetailsIbReq);
            String ngayNhapDuKien = purchaseDetailsIb.getNgayNhapDuKien();
            if (ngayNhapDuKien != null && !ngayNhapDuKien.isEmpty()) {
                try {
                    // Convert ngayXuatDuKien from dd/MM/yyyy to yyyy-MM-dd
                    String ngayNhapDuKienDbFormat = TimeConverter.toDbFormat(TimeConverter.parseDateFromDisplayFormat(ngayNhapDuKien));
                    purchaseDetailsIb.setNgayNhapDuKien(ngayNhapDuKienDbFormat);
                    logger.info("NgayNhapDuKien: {} -> {}", ngayNhapDuKien, ngayNhapDuKienDbFormat);
                } catch (IllegalArgumentException e) {
                    logger.error("Invalid date format: {}", ngayNhapDuKien);
                }
            }
            if (purchaseDetailsIbMapper.existById(purchaseDetailsIbReq.getSysIdChiTietNhapHang())) {
                purchaseDetailsIbMapper.updatePurchaseDetails(purchaseDetailsIb);
            } else {
                purchaseDetailsIb.setMaPR(purchaseRequestIb.getMaPR());
                purchaseDetailsIbMapper.insertPurchaseDetails(purchaseDetailsIb);
            }
        }
    }
}



