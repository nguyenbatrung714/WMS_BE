package org.example.wms_be.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.entity.inbound.PurchaseDetailsIb;
import org.example.wms_be.entity.inbound.PurchaseOrderIb;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.mapper.inbound.PurchaseDetailsIbMapper;
import org.example.wms_be.mapper.inbound.PurchaseOrderIbMapper;
import org.example.wms_be.service.BarCodeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BarCodeServiceImpl implements BarCodeService {

    private final PurchaseOrderIbMapper purchaseOrderIbMapper;
    private final PurchaseDetailsIbMapper purchaseDetailsIbMapper;

    @Override
    public PurchaseOrderIb getPurchaseOrderByMaPO(String maPO) {
        try {
            PurchaseOrderIb purchaseOrderIb = purchaseOrderIbMapper.getPurchaseOrderByMaPO(maPO);
            purchaseOrderIb.setChiTietNhapHang(purchaseDetailsIbMapper.getPurchaseDetailsIbByMaPO(maPO));
            return purchaseOrderIb;
        } catch (Exception e) {
            throw new BadSqlGrammarException("Error while getting purchase order by MaPO: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<byte[]> generateQRCode(@RequestParam String maPO) {
        try {
            String url = "http://localhost:8080/api/v1/" + maPO;

            // Cấu hình cho mã QR
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 1);

            // Tạo BitMatrix cho mã QR
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 200, 200, hints);

            // Chuyển BitMatrix thành ảnh PNG
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            // Trả về mã QR dưới dạng byte[]
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=QRCode.png")
                    .body(outputStream.toByteArray());

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
