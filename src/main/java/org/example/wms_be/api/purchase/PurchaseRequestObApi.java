package org.example.wms_be.api.purchase;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.constant.RespMessagePurchaseRequestOb;
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
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PurchaseRequestObApi.class);

    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseRequestObResp>>> getAllPurchaseRequests(HttpServletRequest request) {
        try {
            List<PurchaseRequestObResp> purchaseRequests = purchaseRequestObService.getAllPurchaseRequestOb();
            ApiResponse<List<PurchaseRequestObResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessagePurchaseRequestOb.GET_SUCCESS.getMessage(),
                    purchaseRequests
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to get all purchase requests", e);
            ApiResponse<List<PurchaseRequestObResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    RespMessagePurchaseRequestOb.GET_FAILED.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{maPR}")
    public ResponseEntity<ApiResponse<List<PurchaseRequestObResp>>> getPurchaseRequestsByMaPR(
            @PathVariable("maPR") String maPR,
            HttpServletRequest request) {
        try {
            List<PurchaseRequestObResp> purchaseRequests = purchaseRequestObService.getPurchaseRequestObByMaPR(maPR);
            ApiResponse<List<PurchaseRequestObResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessagePurchaseRequestOb.GET_SUCCESS_BY_ID.getMessage(),
                    purchaseRequests
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to get purchase requests by MaPR", e);
            ApiResponse<List<PurchaseRequestObResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    RespMessagePurchaseRequestOb.GET_FAILED_BY_ID.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<PurchaseRequestObReq>> savePurchaseRequest(
            @RequestBody PurchaseRequestObReq purchaseRequestObReq,
            HttpServletRequest request) {
        boolean isSaved = purchaseRequestObReq.getSysIdYeuCauXuatHang() != null;
        try {
            purchaseRequestObService.savePurchaseRequestOb(purchaseRequestObReq);
            ApiResponse<PurchaseRequestObReq> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.CREATED.value(),
                    isSaved ? RespMessagePurchaseRequestOb.UPDATE_SUCCESS.getMessage() : RespMessagePurchaseRequestOb.SAVE_SUCCESS.getMessage(),
                    purchaseRequestObReq
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Failed to save purchase request", e);
            ApiResponse<PurchaseRequestObReq> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    isSaved ? RespMessagePurchaseRequestOb.UPDATE_FAILED.getMessage() : RespMessagePurchaseRequestOb.SAVE_FAILED.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
