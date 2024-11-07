package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.SupplierDto;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierApi {
    private final SupplierService supplierService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierDto>>> getAllSuppliers(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of supplier",
                supplierService.getAllSuppliers()
        ), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ApiResponse<SupplierDto>> saveSupplier(HttpServletRequest request, @RequestBody SupplierDto supplierDto) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Supplier save successfully ",
                supplierService.saveSupplier(supplierDto)
        ),HttpStatus.OK);
    }
    @DeleteMapping("/{maNhaCungCap}")
    public ResponseEntity<ApiResponse<Void>> deleteSupplier(@PathVariable String maNhaCungCap,
                                                            HttpServletRequest request
                                                            ){
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Supplier delete successfully",
                supplierService.deleteSupplier(maNhaCungCap)
        ),HttpStatus.OK);
    }
    @GetMapping("/{maNhaCungCap}")
    public ResponseEntity<ApiResponse<SupplierDto>> getSupplierById(@PathVariable String maNhaCungCap,
                                                            HttpServletRequest request
                                                            ){
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Supplier found successfully",
                supplierService.getSupplierById(maNhaCungCap)
        ),HttpStatus.OK);
    }
}
