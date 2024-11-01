package org.example.wms_be.service.impl;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.inbound.PurchaseDetailsIbConverter;
import org.example.wms_be.converter.inbound.PurchaseRequestIbConverter;
import org.example.wms_be.data.dto.PurchaseRequestDto;
import org.example.wms_be.data.response.PurchaseRequestObResp;
import org.example.wms_be.data.response.inbound.PurchaseRequestDetailsIbResp;
import org.example.wms_be.data.response.inbound.PurchaseRequestIbResp;
import org.example.wms_be.mapper.account.UserMapper;
import org.example.wms_be.mapper.inbound.PurchaseDetailsIbMapper;
import org.example.wms_be.mapper.inbound.PurchaseRequestIbMapper;
import org.example.wms_be.service.PurchaseRequestIbService;
import org.example.wms_be.utils.EmailUtil;
import org.example.wms_be.utils.TimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;


@Service
@RequiredArgsConstructor
public class PurchaseRequestIbImpl implements PurchaseRequestIbService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseRequestIbImpl.class);
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
                            String ngayNhapDuKienFormatted = TimeConverter.formatNgayXuat(TimeConverter.parseDateOnly(detail.getNgayNhapDuKien()));
                            detail.setNgayNhapDuKien(ngayNhapDuKienFormatted);
                        }
                    });
                    pr.setChiTietNhapHang(chiTietNhapHang);
                });
        return purchaseRequestIbResps;
    }

    @Override
    public void savePurchaseRequestWithDetails(PurchaseRequestDto purchaseRequestDto) throws MessagingException {

    }
}



