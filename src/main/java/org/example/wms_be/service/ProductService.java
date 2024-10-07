package org.example.wms_be.service;

import com.github.pagehelper.PageInfo;
import org.example.wms_be.data.dto.ProductDto;
import org.example.wms_be.data.request.ProductReq;
import org.example.wms_be.data.response.ProductResp;

public interface ProductService {
    PageInfo<ProductResp> getAllProduct(int page, int size);

    ProductResp saveProduct(ProductReq productReq);

    void deleteProduct(Integer sysIdSanPham);

    ProductResp getAllSanPhamById(Integer sysIdSanPham);
}
