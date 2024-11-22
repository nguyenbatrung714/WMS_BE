package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.entity.inbound.PurchaseOrderIb;
import org.example.wms_be.service.impl.BarCodeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@CrossOrigin
public class BarCode {

    private final BarCodeServiceImpl barCodeService;

    @GetMapping("/{maPO}")
    public ResponseEntity<ApiResponse<PurchaseOrderIb>> getPurchaseOrder(HttpServletRequest request,
                                                                         @PathVariable String maPO) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "CategoryProd found successfully",
                barCodeService.getPurchaseOrderByMaPO(maPO)
        ), HttpStatus.OK);
    }


    @GetMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateQRCode(@RequestParam String maPO) {
        try {
            return barCodeService.generateQRCode(maPO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
