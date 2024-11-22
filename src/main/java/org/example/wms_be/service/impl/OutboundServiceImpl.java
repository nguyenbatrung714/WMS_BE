package org.example.wms_be.service.impl;

import lombok.AllArgsConstructor;
import org.example.wms_be.converter.OutboundConverter;
import org.example.wms_be.converter.PurchaseDetailsObConverter;
import org.example.wms_be.data.request.OutboundReq;
import org.example.wms_be.data.request.PurchaseRequestDetailsObReq;
import org.example.wms_be.data.response.OutboundResp;
import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;
import org.example.wms_be.entity.outbound.OutBound;
import org.example.wms_be.entity.outbound.PurchaseRequestDetailsOb;
import org.example.wms_be.mapper.inbound.PurchaseOrderIbMapper;
import org.example.wms_be.mapper.purchase.OutboundMapper;
import org.example.wms_be.mapper.purchase.PurchaseDetailsObMapper;
import org.example.wms_be.service.OutboundService;
import org.example.wms_be.utils.TimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OutboundServiceImpl implements OutboundService {
    private final OutboundMapper outboundMapper;
    private static final Logger logger = LoggerFactory.getLogger(OutboundServiceImpl.class);
    private final PurchaseDetailsObMapper purchaseDetailsObMapper;
    private final OutboundConverter outboundConverter;
    private final PurchaseOrderIbMapper purchaseOrderIbMapper;
    private final PurchaseDetailsObConverter purchaseDetailsObConverter;

    @Override
    public List<OutboundResp> getAllOutbound() {
        try {
            List<OutboundResp> outboundResps = outboundMapper.getAllOutbound();
            return exportList(outboundResps);
        } catch (Exception e) {
            logger.error("Get all purchase requests failed", e);
            return Collections.emptyList();
        }
    }



    @Override
    @Transactional
    public void saveOutbound(OutboundReq outboundReq) {
        // Lưu Outbound và lấy mã Outbound
        OutBound outBound = saveOutbounds(outboundReq);

        // Lấy mã Outbound và cập nhật cho OutBound
        String maOB = layMaOB(outBound);
        outBound.setMaOB(maOB);
        logger.info("Thêm mã OB mới: {} với MaOB: {}", outBound, maOB);

        // Cập nhật chi tiết xuất hàng với mã Outbound
        updateDeatils(outboundReq, maOB);
    }

    private void updateDeatils(OutboundReq outboundReq, String maOB) {
        if (outboundReq.getChiTietXuatHang() == null || outboundReq.getChiTietXuatHang().isEmpty()) {
            throw new IllegalArgumentException("Chi tiết xuất hàng không thể null hoặc rỗng");
        }

        for (PurchaseRequestDetailsObReq detailsReq : outboundReq.getChiTietXuatHang()) {
            PurchaseRequestDetailsOb details = purchaseDetailsObConverter.toPurchaseRequestDeatilsOb(detailsReq);
            details.setMaPR(maOB);

            // Cập nhật chi tiết xuất hàng với mã outbound
            purchaseDetailsObMapper.updateDetailsObFromPO(outboundReq.getMaPO(), maOB);
        }
    }


    private String layMaOB(OutBound outBound) {
        // Lấy mã Outbound từ cơ sở dữ liệu thông qua sysIdOutbound
        return outboundMapper.getMaOBById(outBound.getSysIdOutbound());
    }

    private OutBound saveOutbounds(OutboundReq outboundReq) {
        // Kiểm tra PO có tồn tại không
        if (!purchaseOrderIbMapper.purchaseOrderIbExistByMaPO(outboundReq.getMaPO())) {
            throw new IllegalArgumentException("PO không tồn tại");
        }

        // Lấy chi tiết xuất hàng từ cơ sở dữ liệu
        List<PurchaseRequestDetailsOb> purchaseRequestDetailsObs = purchaseDetailsObMapper.getPurchaseDetailsObByMaPO(outboundReq.getMaPO());
        if (purchaseRequestDetailsObs == null || purchaseRequestDetailsObs.isEmpty()) {
            throw new IllegalArgumentException("Chi tiết PO không có dữ liệu");
        }

        // Chuyển đổi outboundReq thành đối tượng OutBound và lưu vào cơ sở dữ liệu
        OutBound outBound = outboundConverter.toOutbound(outboundReq);
        outboundMapper.insertOutbound(outBound);

        return outBound;
    }



    private List<OutboundResp> exportList(List<OutboundResp> outboundResps) {
        outboundResps.stream()
                .filter(Objects::nonNull)
                .filter(ob -> ob.getMaOB() != null)
                .forEach(ob -> {
                    if (ob.getNgayXuat() != null) {
                        // convert ngayYeuCau from yyyy-MM-dd HH:mm:ss to dd/MM/yyyy
                        String ngayXuat = TimeConverter.formatNgayYeuCau(Timestamp.valueOf(ob.getNgayXuat()));
                        ob.setNgayXuat(ngayXuat);
                    }
                    List<PurchaseRequestDetailsObResp> chiTietXuatHang = purchaseDetailsObMapper.layDanhSachXuatHangTheoMaOutbound(ob.getMaOB());
                    logger.info("Found {} details for PurchaseRequest: {}", chiTietXuatHang.size(), ob.getMaOB());
                    chiTietXuatHang.forEach(detail -> {
                        if (detail.getNgayXuatDuKien() != null) {
                            String ngayXuatDuKienFormatted = TimeConverter.formatNgayXuat(TimeConverter.parseDateOnly(detail.getNgayXuatDuKien()));
                            detail.setNgayXuatDuKien(ngayXuatDuKienFormatted);
                        }
                    });
                    ob.setChiTietXuatHang(chiTietXuatHang);
                });
        return outboundResps;
    }
}
