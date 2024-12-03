package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.ZoneDetailDto;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.response.WarehouseDetailConsignmentResp;
import org.example.wms_be.service.ZoneDetailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/zone-details")
public class ZoneDetailApi {
    private final ZoneDetailService zoneDetailService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ZoneDetailDto>>> getAllZoneDetails(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of zone-details",
                zoneDetailService.getAllZoneDetails()
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ZoneDetailDto>> saveZoneDetail(@RequestBody ZoneDetailDto zoneDetailDto,
                                                                     HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                201,
                "Zone detail created successfully",
                zoneDetailService.saveZoneDetail(zoneDetailDto)
        ), HttpStatus.CREATED);
    }

    @GetMapping("/{maChiTietKhuVuc}")
    public ResponseEntity<ApiResponse<ZoneDetailDto>> getZoneDetailById(@PathVariable("maChiTietKhuVuc") String maChiTietKhuVuc,
                                                                        HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Zone detail retrieved successfully with ID: " + maChiTietKhuVuc,
                zoneDetailService.getZoneDetailByMaChiTietKhuVuc(maChiTietKhuVuc)
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{maChiTietKhuVuc}")
    public ResponseEntity<ApiResponse<Void>> deleteZoneDetail(@PathVariable("maChiTietKhuVuc") String maChiTietKhuVuc,
                                                              HttpServletRequest request) {
        zoneDetailService.deleteZoneDetail(maChiTietKhuVuc);
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                204,
                "Zone detail deleted successfully with ID: " + maChiTietKhuVuc,
                null
        ), HttpStatus.OK);
    }

    @GetMapping("/warehouse-detail-by-product/{sysIdSanPham}")
    public ResponseEntity<ApiResponse<WarehouseDetailConsignmentResp>> getWarehouseDetailByProduct(@PathVariable Integer sysIdSanPham,
                                                                                                   HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "get successfully",
                zoneDetailService.getWarehouseDetailByProduct(sysIdSanPham)
        ), HttpStatus.OK);
    }

}

