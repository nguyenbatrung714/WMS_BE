package org.example.wms_be.api.purchase;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.request.PurchaseOrderIbReq;
import org.example.wms_be.service.PurchaseOrderIbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/purchase-orders")
public class PurchaseOrderApi {
    private final PurchaseOrderIbService purchaseOrderIbService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PurchaseOrderIbReq>> createPurchaseOrder(@RequestBody PurchaseOrderIbReq purchaseOrderIbReq,
                                                                               HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                201,
                "Create purchase order successfully",
                purchaseOrderIbService.createPurchaseOrder(purchaseOrderIbReq)
        ), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<PurchaseOrderIbReq>>> getAllInbounds(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of Inbound",
                purchaseOrderIbService.getAllPurchaseOrders()
        ), HttpStatus.OK);
    }

    @GetMapping("/check-exist-by-ma-pr")
    public ResponseEntity<ApiResponse<Boolean>> getAllInbounds(HttpServletRequest request,
                                                               @RequestParam("maPR") String maPR) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Get success",
                purchaseOrderIbService.checkExistByMaPR(maPR)
        ), HttpStatus.OK);
    }
}
