package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.request.ConsignmentReq;
import org.example.wms_be.service.ConsignmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consignment")
@RequiredArgsConstructor

@CrossOrigin
public class ConsignmentApi {
    private final ConsignmentService consignmentService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ConsignmentReq>>> getAllShipment(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of Consignment",
                consignmentService.getAllConsignment()
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ConsignmentReq>> saveShipment(@RequestBody ConsignmentReq consignmentReq,
                                                                    HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Consignment saved successfully",
                consignmentService.saveConsignment(consignmentReq)
        ), HttpStatus.CREATED);
    }

    @DeleteMapping("/{maLo}")
    public ResponseEntity<ApiResponse<Void>> deleteShipment(@PathVariable String maLo,
                                                            HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Consignment removed successfully with maKho: " + maLo,
                consignmentService.deleteConsignment(maLo)
        ), HttpStatus.OK);
    }

    @GetMapping("/{maLo}")
    public ResponseEntity<ApiResponse<ConsignmentReq>> getShipmentByMaLo(@PathVariable String maLo,
                                                                         HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Consignment found successfully ",
                consignmentService.getAllConsignmentById(maLo)
        ), HttpStatus.OK);
    }
}