package org.example.wms_be.api.thongke;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.response.thongke.ThongKeGiaoDichSanPhamGanDayResp;
import org.example.wms_be.service.ThongKeGiaoDichSanPhamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/thongke")
@CrossOrigin
public class ThongKeGiaoDichSanPhamApi {
    private final ThongKeGiaoDichSanPhamService thongKeGiaoDichSanPham;
    @GetMapping("/giao-dich-san-pham/nhap")
    public ResponseEntity<ApiResponse<ThongKeGiaoDichSanPhamGanDayResp>> getThongKeGiaoDichSanPham(
            HttpServletRequest request) {

        try {

            ThongKeGiaoDichSanPhamGanDayResp thongKeGiaoDichSanPhamGanDayResp = thongKeGiaoDichSanPham.getThongKeGiaoDichSanPham(true);

            ApiResponse<ThongKeGiaoDichSanPhamGanDayResp> response = new ApiResponse<>(
                    request.getRequestURI(),
                    200,
                    "Lấy thống kê giao dịch sản phẩm nhập thành công",
                    thongKeGiaoDichSanPhamGanDayResp
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(
                    request.getRequestURI(),
                    500,
                    "Lấy thống kê giao dịch sản phẩm nhập thất bại",
                    null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/giao-dich-san-pham/xuat")
    public ResponseEntity<ApiResponse<ThongKeGiaoDichSanPhamGanDayResp>> getThongKeGiaoDichSanPhamXuat(HttpServletRequest request) {
        try {

            ThongKeGiaoDichSanPhamGanDayResp thongKeGiaoDichSanPhamGanDayResp = thongKeGiaoDichSanPham.getThongKeGiaoDichSanPham(false);

            ApiResponse<ThongKeGiaoDichSanPhamGanDayResp> response = new ApiResponse<>(
                    request.getRequestURI(),
                    200,
                    "Lấy thống kê giao dịch sản phẩm xuất thành công",
                    thongKeGiaoDichSanPhamGanDayResp
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(
                    request.getRequestURI(),
                    500,
                    "Lấy thống kê giao dịch sản phẩm xuất thất bại",
                    null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

