package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.WarehouseDto;
import org.example.wms_be.data.dto.WasteProductsDto;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.service.WasteProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/waste-products")
public class WasteProductsApi {
    private final WasteProductsService wasteProductsService;
    @PostMapping("/{sysIdTonKho}")
    public ResponseEntity<ApiResponse<WasteProductsDto>> moveToWaste(@PathVariable int sysIdTonKho,
                                                                     HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "WasteProducts saved successfully",
                wasteProductsService.insertWaste(sysIdTonKho)
        ), HttpStatus.CREATED);
    }
}
