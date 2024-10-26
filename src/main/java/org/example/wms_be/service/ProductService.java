package org.example.wms_be.service;

import org.example.wms_be.data.request.ProductReq;
import org.example.wms_be.data.response.ProductResp;

import java.util.List;

public interface ProductService {
    List<ProductResp> getAllProduct();

    ProductResp saveProduct(ProductReq productReq);

    void deleteProduct(Integer sysIdSanPham);

    ProductResp getAllSanPhamById(Integer sysIdSanPham);
}
