package org.example.wms_be.api.purchase;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.PurchaseRequestDto;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.mgt.RespMessagePurchaseReuqestIb;
import org.example.wms_be.data.response.inbound.PurchaseRequestIbResp;
import org.example.wms_be.service.PurchaseRequestIbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/purchase-requests")
public class PurchaseRequestIbApi {
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PurchaseRequestIbApi.class);
   private final PurchaseRequestIbService purchaseRequestIbService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseRequestIbResp>>> getAllPurchaseRequests(HttpServletRequest request) {
        try {
            List<PurchaseRequestIbResp> purchaseRequests = purchaseRequestIbService.getAllPurchaseRequestIb();
            ApiResponse<List<PurchaseRequestIbResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessagePurchaseReuqestIb.GET_SUCCESS.getMessage(),
                    purchaseRequests
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to get all purchase requests", e);
            ApiResponse<List<PurchaseRequestIbResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    RespMessagePurchaseReuqestIb.GET_FAILED.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{maPR}")
    public ResponseEntity<ApiResponse<List<PurchaseRequestIbResp>>> getPurchaseRequestsByMaPR(
            @PathVariable("maPR") String maPR,
            HttpServletRequest request) {
        try {
            List<PurchaseRequestIbResp> purchaseRequests = purchaseRequestIbService.getPurchaseRequestIbByMaPR(maPR);
            ApiResponse<List<PurchaseRequestIbResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessagePurchaseReuqestIb.GET_SUCCESS_BY_ID.getMessage(),
                    purchaseRequests
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to get purchase requests by MaPR", e);
            ApiResponse<List<PurchaseRequestIbResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    RespMessagePurchaseReuqestIb.GET_FAILED_BY_ID.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/save")
    public ResponseEntity<ApiResponse<PurchaseRequestDto>> savePurchaseRequest(
            @RequestBody PurchaseRequestDto purchaseRequestDto,
            HttpServletRequest request) throws MessagingException {
        // Lưu yêu cầu mua hàng
        purchaseRequestIbService.savePurchaseRequestWithDetails(purchaseRequestDto);
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
