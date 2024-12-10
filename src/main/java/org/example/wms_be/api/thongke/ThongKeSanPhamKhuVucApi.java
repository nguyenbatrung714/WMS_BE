package org.example.wms_be.api.thongke;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.response.thongke.ThongKeSanPhamKhuVucResp;
import org.example.wms_be.service.ThongKeSanPhamKhuVucService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/thongke")
@CrossOrigin
public class ThongKeSanPhamKhuVucApi {
    private final ThongKeSanPhamKhuVucService thongKeSanPhamKhuVuc;

    @GetMapping("/san-pham/theo-khu-vuc")
    public ResponseEntity<ApiResponse<List<ThongKeSanPhamKhuVucResp>>> getThongKeSanPhamTheoKhuVuc(
            HttpServletRequest request) {
        try {
            List<ThongKeSanPhamKhuVucResp> thongKeSanPhamKhuVucResps = thongKeSanPhamKhuVuc.getThongKeSanPhamKhuVuc();

            ApiResponse<List<ThongKeSanPhamKhuVucResp>> response = new ApiResponse<>(
                    request.getRequestURI(),
                    200,
                    "Lấy thống kê sản phẩm theo khu vực thành công",
                    thongKeSanPhamKhuVucResps
            );
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(
                    request.getRequestURI(),
                    500,
                    "Lấy thống kê sản phẩm theo khu vực thất bại",
                    null
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
