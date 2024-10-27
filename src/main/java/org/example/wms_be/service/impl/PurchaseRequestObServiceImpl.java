package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.PurchaseDetailsObConverter;
import org.example.wms_be.converter.PurchaseRequestObConverter;
import org.example.wms_be.data.request.PurchaseRequestDetailsObReq;
import org.example.wms_be.data.request.PurchaseRequestObReq;
import org.example.wms_be.data.response.PurchaseRequestDetailsResp;
import org.example.wms_be.data.response.PurchaseRequestObResp;
import org.example.wms_be.entity.purchase.PurchaseRequestDetailsOb;
import org.example.wms_be.entity.purchase.PurchaseRequestOb;
import org.example.wms_be.mapper.purchase.PurchaseDetailsObMapper;
import org.example.wms_be.mapper.purchase.PurchaseRequestObMapper;
import org.example.wms_be.service.PurchaseRequestObService;
import org.example.wms_be.utils.TimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseRequestObServiceImpl implements PurchaseRequestObService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseRequestObServiceImpl.class);
    private final PurchaseRequestObConverter purchaseRequestObConverter;
    private final PurchaseDetailsObConverter purchaseDetailsObConverter;
    private final PurchaseRequestObMapper purchaseRequestObMapper;
    private final PurchaseDetailsObMapper purchaseDetailsObMapper;

    @Override
    public List<PurchaseRequestObResp> getAllPurchaseRequestOb() {
        try {
            List<PurchaseRequestObResp> purchaseRequestObResps = purchaseRequestObMapper.getAllPurchaseRequestOb();
            purchaseRequestObResps.stream()
                    .filter(Objects::nonNull)
                    .filter(pr -> pr.getMaPR() != null)
                    .forEach(pr -> {
                        if (pr.getNgayYeuCau() != null) {
                            // convert ngayYeuCau from yyyy-MM-dd HH:mm:ss to dd/MM/yyyy
                            String ngayYeuCauFormatted = TimeConverter.formatNgayYeuCau(Timestamp.valueOf(pr.getNgayYeuCau()));
                            pr.setNgayYeuCau(ngayYeuCauFormatted);
                        }
                        List<PurchaseRequestDetailsResp> chiTietXuatHang = purchaseDetailsObMapper.layDanhSachXuatHangTheoMaPR(pr.getMaPR());
                        logger.info("Found {} details for PurchaseRequest: {}", chiTietXuatHang.size(), pr.getMaPR());
                        chiTietXuatHang.forEach(detail -> {
                            if (detail.getNgayXuatDuKien() != null) {
                                String ngayXuatDuKienFormatted = TimeConverter.formatNgayXuat(TimeConverter.parseDateOnly(detail.getNgayXuatDuKien()));
                                detail.setNgayXuatDuKien(ngayXuatDuKienFormatted);
                            }
                        });
                        pr.setChiTietXuatHang(chiTietXuatHang);
                    });
            return purchaseRequestObResps;
        } catch (Exception e) {
            logger.error("Get all purchase requests failed", e);
            return Collections.emptyList();
        }
    }
    @Override
    @Transactional
    public void savePurchaseRequestOb(PurchaseRequestObReq purchaseRequestObReq) {
        try {
            // Convert sang entity
            PurchaseRequestOb purchaseRequestOb = purchaseRequestObConverter.toPurchaseRequestObReq(purchaseRequestObReq);
            if (purchaseRequestObMapper.existById(purchaseRequestObReq.getSysIdYeuCauXuatHang())) {
                purchaseRequestObMapper.updatePurchaseRequestOb(purchaseRequestOb);
            } else {
                purchaseRequestObMapper.insertPurchaseRequestOb(purchaseRequestOb);
            }
            for (PurchaseRequestDetailsObReq purchaseRequestDetailsObReq : purchaseRequestObReq.getChiTietXuatHang()) {
                // Chuyển đổi chi tiết yêu cầu
                PurchaseRequestDetailsOb purchaseRequestDetailsOb = purchaseDetailsObConverter.toPurchaseRequestDeatilsOb(purchaseRequestDetailsObReq);
                String ngayXuatDuKien = purchaseRequestDetailsOb.getNgayXuatDuKien();
                if (ngayXuatDuKien != null && !ngayXuatDuKien.isEmpty()) {
                    try {
                        // convert ngayXuatDuKien from dd/MM/yyyy to yyyy-MM-dd
                        String ngayXuatDuKienDbFormat = TimeConverter.toDbFormat(TimeConverter.parseDateFromDisplayFormat(ngayXuatDuKien));
                        purchaseRequestDetailsOb.setNgayXuatDuKien(ngayXuatDuKienDbFormat);
                        logger.info("NgayXuatDuKien: {} -> {}", ngayXuatDuKien, ngayXuatDuKienDbFormat);
                    } catch (IllegalArgumentException e) {
                        logger.error("Invalid date format: {}", ngayXuatDuKien);
                    }
                }
                //  cập nhật hoặc thêm mới
                if (purchaseDetailsObMapper.existById(purchaseRequestDetailsObReq.getSysIdChiTietXuatHang())) {
                    purchaseDetailsObMapper.updatePurchaseRequestDetailsOb(purchaseRequestDetailsOb);
                } else {
                    purchaseRequestDetailsOb.setMaPR(purchaseRequestOb.getMaPR());
                    purchaseDetailsObMapper.insertPurchaseRequestDetailsOb(purchaseRequestDetailsOb);
                }
            }
        } catch (Exception e) {
            logger.error("Save purchase request failed", e);
        }
    }
}