package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.amazons3.service.S3Service;
import org.example.wms_be.converter.ProductConverter;
import org.example.wms_be.data.request.ProductReq;
import org.example.wms_be.data.response.ProductResp;
import org.example.wms_be.entity.product.Product;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.FileStorageException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.product.CategoryProdMapper;
import org.example.wms_be.mapper.product.ProductMapper;
import org.example.wms_be.service.ProductService;
import org.example.wms_be.utils.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final CategoryProdMapper categoryProdMapper;
    private final ProductConverter productConverter;
    private final S3Service s3Service;

    @Override
    public List<ProductResp> getAllProduct() {
        return productMapper.getAllProduct().stream().map(
                product -> {
                    product.setHinhAnh(generateImageUrl(product.getHinhAnh()));
                    return productConverter.toProductResp(product);
                }
        ).toList();
    }

    @Override
    public ProductResp saveProduct(ProductReq productReq) {
        if (!categoryProdMapper.checkCategoryProdExists(productReq.getSysIdDanhMuc())) {
            throw new ResourceNotFoundException("CategoryProd", "sysIdDanhMuc", productReq.getSysIdDanhMuc() + "");
        }

        String imagePath = null;
        if (productReq.getHinhAnh() != null && !productReq.getHinhAnh().isEmpty()) {
            try {
                imagePath = uploadImage(productReq.getHinhAnh());
            } catch (FileStorageException e) {
                throw new FileStorageException("Error occurred while uploading the file: " + e.getMessage());
            }
        }

        Product product = productConverter.toProduct(productReq);
        product.setSysIdSanPham(productReq.getSysIdSanPham());

        if (imagePath != null) {
            product.setHinhAnh(imagePath);
        }

        try {
            if (productMapper.checkProductExists(productReq.getSysIdSanPham())) {
                String img = productMapper.getImgProductByMaSanPham(productReq.getSysIdSanPham());
                product.setHinhAnh(img);
                productMapper.updateProduct(product);
            } else {
                productMapper.insertProduct(product);
            }
        } catch (Exception e) {
            throw new BadSqlGrammarException("Save product failed");
        }
        return productConverter.toProductResp(product);
    }

    @Override
    public void deleteProduct(Integer sysIdSanPham) {
        if (!productMapper.checkProductExists(sysIdSanPham))
            throw new ResourceNotFoundException("Product", "Product not found with id: ", sysIdSanPham.toString());

        try {
            productMapper.deleteProduct(sysIdSanPham);
        } catch (Exception e) {
            throw new BadSqlGrammarException("Delete product failed");
        }
    }


    @Override
    public ProductResp getAllSanPhamById(Integer sysIdSanPham) {
        return productMapper.getProductByMaSanPham(sysIdSanPham)
                .map(
                        product -> {
                            product.setHinhAnh(generateImageUrl(product.getHinhAnh()));
                            return productConverter.toProductResp(product);
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("Product", "sysId", sysIdSanPham + ""));
    }

    private String uploadImage(MultipartFile multipartFile) {
        try {
            File fileConvert = FileUtil.convertMultipartFileToFile(multipartFile);
            String imagePath = s3Service.uploadFileToS3(fileConvert);
            Files.deleteIfExists(fileConvert.toPath());
            return imagePath;
        } catch (IOException e) {
            throw new FileStorageException("Error when uploading file: " + e.getMessage());
        }
    }

    private String generateImageUrl(String imagePath) {
        return s3Service.generatePresignedUrl(imagePath);
    }
}
