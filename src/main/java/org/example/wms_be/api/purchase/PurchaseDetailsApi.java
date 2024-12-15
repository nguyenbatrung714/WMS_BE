package org.example.wms_be.api.purchase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.request.PurchaseDetailsIbReq;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;
import org.example.wms_be.service.PurchaseDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/purchase-details")
public class PurchaseDetailsApi {
    private final PurchaseDetailsService purchaseDetailsService;
    @GetMapping()
    public ResponseEntity<ApiResponse<List<PurchaseDetailsIbReq>>> getAllPurchaseDetails(HttpServletRequest request) {
        List<PurchaseDetailsIbReq> purchaseDetails = purchaseDetailsService.getAllPurchaseDetails();
        ApiResponse<List<PurchaseDetailsIbReq>> response = new ApiResponse<>(
                request.getRequestURI(),
                HttpStatus.OK.value(),
                "lấy danh sách thành công",
                purchaseDetails
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/thong-ke-nhap-nhieu-nhat")
    public ResponseEntity<ApiResponse<List<PurchaseDetailsIbReq>>> getMostIbProducts(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of most ob products",
                purchaseDetailsService.getMostIbProducts()
        ), HttpStatus.OK);
    }
    @GetMapping("/thong-ke-nhap-it-nhat")
    public ResponseEntity<ApiResponse<List<PurchaseDetailsIbReq>>> getLeastIbProducts(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of least ob products",
                purchaseDetailsService.getLeastIbProducts()
        ), HttpStatus.OK);
    }
    @GetMapping("/thong-ke-nhap")
    public ResponseEntity<ApiResponse<Double>> getTongSoLuongNhap(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List purchase ib",
                purchaseDetailsService.tongSoLuongNhap()
        ), HttpStatus.OK);
    }
}
