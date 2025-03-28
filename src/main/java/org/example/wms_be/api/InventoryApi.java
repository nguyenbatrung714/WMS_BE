package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.wms_be.data.dto.CategoryProdDto;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.response.InventoryResp;
import org.example.wms_be.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/inventories")
@CrossOrigin
public class InventoryApi {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<InventoryResp>>> getAllInventories(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of Inventories",
                inventoryService.getInventoryList()
        ), HttpStatus.OK);
    }
    @GetMapping("/tong-so-luong")
    public ResponseEntity<ApiResponse<Double>> thongKeTongSoLuongTonKho(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Thống kê tổng số lượng tồn kho thành công",
                inventoryService.thongKeTongSoLuongTonKho()
        ), HttpStatus.OK);


    }
}
