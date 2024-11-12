package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.example.wms_be.data.dto.CategoryProdDto;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.service.CategoryProdService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/categories")
@CrossOrigin
public class CategoryProdApi {
    private final CategoryProdService categoryProdService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryProdDto>>> getAllCategoryProd(HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "List of CategoryProd",
                categoryProdService.getAllCategoryProd()
        ), HttpStatus.OK);
    }

    @GetMapping("/{sysIdDanhMuc}")
    public ResponseEntity<ApiResponse<CategoryProdDto>> findCategoryProdById(@PathVariable Integer sysIdDanhMuc,
                                                                             HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "CategoryProd found successfully",
                categoryProdService.getCategoryProdById(sysIdDanhMuc)
        ), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryProdDto>> saveCategoryProd(@RequestBody CategoryProdDto categoryProdDto,
                                                         HttpServletRequest request) {
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "CategoryProd saved successfully",
                categoryProdService.saveCategoryProd(categoryProdDto)
        ), HttpStatus.CREATED);
    }

    @DeleteMapping("/{maDanhMuc}")
    public ResponseEntity<ApiResponse<Void>> removeCategoryProd(@PathVariable Integer maDanhMuc,
                                                                HttpServletRequest request) {
        categoryProdService.deleteCategoryProd(maDanhMuc);
        return new ResponseEntity<>(new ApiResponse<>(
                request.getRequestURI(),
                200,
                "CategoryProd removed successfully with maDanhMuc: " + maDanhMuc,
                null
        ), HttpStatus.OK);
    }
}
