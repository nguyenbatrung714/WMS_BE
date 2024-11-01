package org.example.wms_be.mapper.product;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.product.Product;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductMapper {
    List<Product> getAllProduct();

    int insertProduct(Product product);

    int updateProduct(Product product);

    int deleteProduct(Integer maSanPham);

    boolean checkProductExists(Integer maSanPham);

    Optional<Product> getProductByMaSanPham(Integer maSanPham);

    String getImgProductByMaSanPham(Integer maSanPham);
}
