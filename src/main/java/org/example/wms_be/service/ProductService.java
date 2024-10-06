package org.example.wms_be.service;

import com.github.pagehelper.PageInfo;
import org.example.wms_be.data.dto.ProductDto;

public interface ProductService {
    PageInfo<ProductDto> getAllProduct(int page, int size);
    ProductDto saveProduct(ProductDto productDto);
    void deleteProduct(Integer maSanPham);
    ProductDto getAllSanPhamById(Integer maSanPham);

}
