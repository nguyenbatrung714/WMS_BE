package org.example.wms_be.api.purchase;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.PurchaseDetailsDto;
import org.example.wms_be.data.mgt.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<PurchaseDetailsDto>>> getAllPurchaseDetails(HttpServletRequest request) {
        List<PurchaseDetailsDto> purchaseDetails = purchaseDetailsService.getAllPurchaseDetails();
        ApiResponse<List<PurchaseDetailsDto>> response = new ApiResponse<>(
                request.getRequestURI(),
                HttpStatus.OK.value(),
                "lấy danh sách thành công",
                purchaseDetails
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
