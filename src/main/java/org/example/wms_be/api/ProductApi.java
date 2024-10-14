package org.example.wms_be.api;

import com.github.pagehelper.PageInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.request.ProductReq;
import org.example.wms_be.data.response.ProductResp;
import org.example.wms_be.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductApi {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<PageInfo<ProductResp>>> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                                             @RequestParam(defaultValue = "10") int size,
                                                                             HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of Product",
                productService.getAllProduct(page, size)
        ), HttpStatus.OK);

    }

    @GetMapping("/{maSanPham}")
    public ResponseEntity<ApiResponse<ProductResp>> getProductById(@PathVariable("maSanPham") Integer maSanPham,
                                                                   HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Product found successfully",
                productService.getAllSanPhamById(maSanPham)
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResp>> saveProduct(@ModelAttribute ProductReq productReq,
                                                               HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Product save successfully",
                productService.saveProduct(productReq)
        ), HttpStatus.OK);
    }

    @DeleteMapping("/{maSanPham}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable("maSanPham") Integer maSanPham,
                                                           HttpServletRequest request) {
        productService.deleteProduct(maSanPham);
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "Product delete successfully with id: " + maSanPham,
                null

        ), HttpStatus.OK);
    }
}