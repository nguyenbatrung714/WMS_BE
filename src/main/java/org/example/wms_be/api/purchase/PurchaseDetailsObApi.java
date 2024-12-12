package org.example.wms_be.api.purchase;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;
import org.example.wms_be.data.response.inbound.PurchaseRequestIbResp;
import org.example.wms_be.service.PurchaseDetailsObService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/purchase-details-outbound")
public class PurchaseDetailsObApi {
    private final PurchaseDetailsObService purchaseDetailsService;
    @GetMapping()
    public ResponseEntity<ApiResponse<List<PurchaseRequestDetailsObResp>>> getAllPurchaseDetails(HttpServletRequest request, @RequestParam String maPR) {
        List<PurchaseRequestDetailsObResp> purchaseDetails = purchaseDetailsService.getChiTietDonHangByMaPR(maPR);
        ApiResponse<List<PurchaseRequestDetailsObResp>> response = new ApiResponse<>(
                request.getRequestURI(),
                HttpStatus.OK.value(),
                "lấy danh sách thành công",
                purchaseDetails
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/thong-ke-xuat-nhieu-nhat")
    public ResponseEntity<ApiResponse<List<PurchaseRequestDetailsObResp>>> getMostIbProducts(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of most ob products",
                purchaseDetailsService.getMostObProducts()
        ), HttpStatus.OK);
    }
    @GetMapping("/thong-ke-xuat-it-nhat")
    public ResponseEntity<ApiResponse<List<PurchaseRequestDetailsObResp>>> getLeastIbProducts(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of least ob products",
                purchaseDetailsService.getLeastObProducts()
        ), HttpStatus.OK);
    }
    @GetMapping("/thong-ke-xuat")
    public ResponseEntity<ApiResponse<Double>> getTongSoLuongXuat(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of purchase ob",
                purchaseDetailsService.tongSoLuongXuat()
        ), HttpStatus.OK);
    }
}
