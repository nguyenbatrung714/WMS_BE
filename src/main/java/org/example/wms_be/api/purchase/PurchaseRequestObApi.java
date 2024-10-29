package org.example.wms_be.api.purchase;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.request.PurchaseRequestObReq;
import org.example.wms_be.data.response.PurchaseRequestObResp;
import org.example.wms_be.service.PurchaseRequestObService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/purchase-request-ob")
public class PurchaseRequestObApi {
    private final PurchaseRequestObService purchaseRequestObService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseRequestObResp>>> getAllPurchaseRequests(HttpServletRequest request) {
        // Lấy danh sách các yêu cầu mua hàng từ service
        List<PurchaseRequestObResp> purchaseRequests = purchaseRequestObService.getAllPurchaseRequestOb();
        // Tạo ApiResponse để trả về dữ liệu
        ApiResponse<List<PurchaseRequestObResp>> response = new ApiResponse<>(
                request.getRequestURI(),
                HttpStatus.OK.value(),
                "lấy danh sách thành công",
                purchaseRequests
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<PurchaseRequestObReq>> savePurchaseRequest(
            @RequestBody PurchaseRequestObReq purchaseRequestObReq,
            HttpServletRequest request) {
        // Lưu yêu cầu mua hàng
        purchaseRequestObService.savePurchaseRequestOb(purchaseRequestObReq);
        // Tạo ApiResponse để trả về dữ liệu
        ApiResponse<PurchaseRequestObReq> response = new ApiResponse<>(
                request.getRequestURI(),
                HttpStatus.CREATED.value(),
                "Lưu yêu cầu xuất hàng thành công",
                purchaseRequestObReq
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
