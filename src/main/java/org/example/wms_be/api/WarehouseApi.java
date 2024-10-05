package org.example.wms_be.api;

import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.WarehouseDto;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.service.WarehouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/warehouses")
public class WarehouseApi {

    private final WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageInfo<WarehouseDto>>> getAllWarehouses(@RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of warehouses",
                warehouseService.getAllWarehouses(page, size)
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WarehouseDto>> saveWarehouse(@RequestBody WarehouseDto warehouseDto,
                                                                   HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Warehouse saved successfully",
                warehouseService.saveWarehouse(warehouseDto)
        ), HttpStatus.CREATED);
    }

    @GetMapping("/{maKho}")
    public ResponseEntity<ApiResponse<WarehouseDto>> findWarehouseById(@PathVariable String maKho,
                                                                       HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Warehouse found successfully",
                warehouseService.getWarehouseById(maKho)
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{maKho}")
    public ResponseEntity<ApiResponse<Void>> removeWarehouse(@PathVariable String maKho,
                                                             HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Warehouse removed successfully with maKho: " + maKho,
                warehouseService.deleteWarehouse(maKho)
        ), HttpStatus.OK);
    }
}