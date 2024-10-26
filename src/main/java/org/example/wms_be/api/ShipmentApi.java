package org.example.wms_be.api;

import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.dto.ShipmentDto;
import org.example.wms_be.data.dto.UserDto;
import org.example.wms_be.data.dto.WarehouseDto;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.service.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/shipment")
@RequiredArgsConstructor
@CrossOrigin
public class ShipmentApi {
    private final ShipmentService shipmentService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<ShipmentDto>>> getAllShipment(HttpServletRequest request)
    {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of User",
                shipmentService.getAllShipment()
        ), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<ApiResponse<ShipmentDto>> saveShipment(@RequestBody ShipmentDto shipmentDto,
                                                                   HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Shipment saved successfully",
                shipmentService.saveShipment(shipmentDto)
        ), HttpStatus.CREATED);
    }
    @DeleteMapping("/{maLo}")
    public ResponseEntity<ApiResponse<Void>> deleteShipment(@PathVariable String maLo,
                                                                 HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Shipment removed successfully with maKho: " + maLo,
                shipmentService.deleteShipment(maLo)
        ), HttpStatus.OK);
    }
    @GetMapping("/{maLo}")
    public ResponseEntity<ApiResponse<ShipmentDto>> getShipmentByMaLo(@PathVariable String maLo,
                                                            HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Shipment found successfully " ,
                shipmentService.getAllShipmentById(maLo)
        ), HttpStatus.OK);
    }


}
