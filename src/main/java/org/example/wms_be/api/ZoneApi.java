package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.ZoneDto;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.service.ZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/zones")
public class ZoneApi {
    private final ZoneService zoneService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ZoneDto>>> getAllZones(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of zones",
                zoneService.getAllZones()
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ZoneDto>> saveZone(@RequestBody ZoneDto zoneDto,
                                                         HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Zone saved successfully",
                zoneService.saveZone(zoneDto)
        ), HttpStatus.CREATED);
    }

    @GetMapping("/{maKhuVuc}")
    public ResponseEntity<ApiResponse<ZoneDto>> findZoneById(@PathVariable String maKhuVuc,
                                                             HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Zone found successfully",
                zoneService.getZoneById(maKhuVuc)
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{maKhuVuc}")
    public ResponseEntity<ApiResponse<Void>> removeZone(@PathVariable String maKhuVuc,
                                                        HttpServletRequest request) {
        zoneService.deleteZone(maKhuVuc);
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Zone removed successfully with maKhuVuc: " + maKhuVuc,
                null
        ), HttpStatus.OK);
    }
}
