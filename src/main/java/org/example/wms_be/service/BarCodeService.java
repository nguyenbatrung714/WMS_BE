package org.example.wms_be.service;

import org.example.wms_be.entity.inbound.PurchaseOrderIb;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface BarCodeService {

    PurchaseOrderIb getPurchaseOrderByMaPO(String maPO);

    ResponseEntity<byte[]> generateQRCode(@RequestParam String maPO);
}
