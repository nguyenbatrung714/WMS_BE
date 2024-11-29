package org.example.wms_be.api.purchase;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.constant.InventoryConst;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.mgt.RespMessageOutbound;
import org.example.wms_be.data.mgt.RespMessagePurchaseReuqestIb;
import org.example.wms_be.data.request.OutboundReq;
import org.example.wms_be.data.response.InventoryResp;
import org.example.wms_be.data.response.OutboundResp;
import org.example.wms_be.entity.inventory.KiemTraTonKho;
import org.example.wms_be.service.InventoryService;
import org.example.wms_be.service.OutboundService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/outbound")
public class OutboundApi {
    private final OutboundService outboundService;

    private final InventoryService inventoryService;
    private final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OutboundApi.class);

    @GetMapping
    public ResponseEntity<ApiResponse<List<OutboundResp>>> getAllOutbound(HttpServletRequest request) {
        try {
            List<OutboundResp> outboundResps = outboundService.getAllOutbound();
            ApiResponse<List<OutboundResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessageOutbound.GET_SUCCESS.getMessage(),
                   outboundResps
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<List<OutboundResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    RespMessageOutbound.GET_FAILED.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<OutboundReq>> savePurchaseRequest(
            @RequestBody OutboundReq outboundReq,
            HttpServletRequest request) {
        try {
            outboundService.saveOutbound(outboundReq);
            ApiResponse<OutboundReq> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessageOutbound.SAVE_SUCCESS.getMessage(),
                    outboundReq
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse<OutboundReq> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    RespMessageOutbound.SAVE_FAILED.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/first-out/{sysIdSanPham}")
    public ResponseEntity<ApiResponse<List<InventoryResp>>> getFirstOutbound(
            @PathVariable("sysIdSanPham") Integer sysIdSanPham,
            HttpServletRequest request){
        try {
            List<InventoryResp> inventoryResps = inventoryService.layDanhSachLoHangCanXuat(sysIdSanPham);
            ApiResponse<List<InventoryResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessageOutbound.GET_FIRSTOUT_SUCCESS.getMessage(),
                    inventoryResps
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<InventoryResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.BAD_REQUEST.value(),
                    RespMessageOutbound.GET_FIRSTOUT_FAILED.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<List<InventoryResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    RespMessageOutbound.GET_FIRSTOUT_FAILED.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/check-outbound/{sysIdChiTietXuatHang}/{sysIdSanPham}")
    public ResponseEntity<ApiResponse<KiemTraTonKho>> kiemTraTonKho(
            @PathVariable("sysIdChiTietXuatHang") Integer sysIdChiTietXuatHang,
            @PathVariable("sysIdSanPham") Integer sysIdSanPham,
            HttpServletRequest request) {
        try {
            KiemTraTonKho kiemTraTonKhos = inventoryService.kiemTraTonKho(sysIdSanPham, sysIdChiTietXuatHang);
            ApiResponse<KiemTraTonKho> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessageOutbound.CHECK_INVENTORY_SUCCESS.getMessage(),
                    kiemTraTonKhos
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Nếu không đủ số lượng tồn kho, trả về lỗi 400
            ApiResponse<KiemTraTonKho> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse<KiemTraTonKho> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    RespMessageOutbound.CHECK_INVENTORY_FAILED.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
