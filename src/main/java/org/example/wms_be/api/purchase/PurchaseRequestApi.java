package org.example.wms_be.api.purchase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.PurchaseRequestDto;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.service.PurchaseRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/purchase-requests")
public class PurchaseRequestApi {
   private final PurchaseRequestService purchaseRequestService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseRequestDto>>> getAllPurchaseRequests(HttpServletRequest request) {
        // Lấy danh sách các yêu cầu mua hàng từ service
        List<PurchaseRequestDto> purchaseRequests = purchaseRequestService.getAllPurchaseRequest();
        // Tạo ApiResponse để trả về dữ liệu
        ApiResponse<List<PurchaseRequestDto>> response = new ApiResponse<>(
                request.getRequestURI(),
                HttpStatus.OK.value(),
                "lấy danh sách thành công",
                purchaseRequests
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<PurchaseRequestDto>> savePurchaseRequest(
            @RequestBody PurchaseRequestDto purchaseRequestDto,
            HttpServletRequest request) {
        // Lưu yêu cầu mua hàng
        purchaseRequestService.savePurchaseRequestWithDetails(purchaseRequestDto);
        // Tạo ApiResponse để trả về dữ liệu
        ApiResponse<PurchaseRequestDto> response = new ApiResponse<>(
                request.getRequestURI(),
                HttpStatus.CREATED.value(),
                "Lưu yêu cầu mua hàng thành công",
                purchaseRequestDto
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
