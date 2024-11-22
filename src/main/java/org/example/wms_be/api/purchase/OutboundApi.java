package org.example.wms_be.api.purchase;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.mgt.RespMessagePurchaseRequestOb;
import org.example.wms_be.data.request.InboundReq;
import org.example.wms_be.data.request.OutboundReq;
import org.example.wms_be.data.request.PurchaseRequestObReq;
import org.example.wms_be.data.response.OutboundResp;
import org.example.wms_be.data.response.PurchaseRequestObResp;
import org.example.wms_be.service.OutboundService;
import org.example.wms_be.service.PurchaseRequestObService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/outbound")
public class OutboundApi {
    private final OutboundService outboundService;
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OutboundApi.class);

    @GetMapping
    public ResponseEntity<ApiResponse<List<OutboundResp>>> getAllPurchaseRequests(HttpServletRequest request) {
        try {
            List<OutboundResp> outboundResps = outboundService.getAllOutbound();
            ApiResponse<List<OutboundResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    "Get all outbound successfully",
                   outboundResps
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to get all outbound", e);
            ApiResponse<List<OutboundResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to get all outbound",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<OutboundReq>> savePurchaseRequest(
            @RequestBody OutboundReq outboundReq,
            HttpServletRequest request) {
        try {
            outboundService.saveOutbound(outboundReq);
            ApiResponse<OutboundReq> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    "Save outbound successfully",
                    outboundReq
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Failed to save outbound", e);
            ApiResponse<OutboundReq> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to save outbound",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
