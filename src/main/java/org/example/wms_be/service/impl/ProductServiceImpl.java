package org.example.wms_be.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.wms_be.amazons3.service.S3Service;
import org.example.wms_be.converter.ProductConverter;
import org.example.wms_be.data.dto.ProductDto;
import org.example.wms_be.entity.product.Product;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.FileStorageException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.categoryProd.CategoryProdMapper;
import org.example.wms_be.mapper.product.ProductMapper;
import org.example.wms_be.service.ProductService;
import org.example.wms_be.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final CategoryProdMapper categoryProdMapper;
    private final ProductConverter productConverter;
    private final S3Service s3Service;
    private static final Logger logger = LoggerFactory.getLogger(CategoryProdServiceImpl.class);

    @Override
    public PageInfo<ProductDto> getAllProduct(int page, int size) {
        try {
            PageMethod.startPage(page + 1, size);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error when starting page: " + e.getMessage());
        }

        return new PageInfo<>(productMapper.getAllProduct().stream().map(
                product -> {
                    product.setHinhAnh(generateImageUrl(product.getHinhAnh()));
                    return productConverter.toProductDto(product);
                }
        ).toList());
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        if (productDto.getSysIdDanhMuc() == null) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
        if (!categoryProdMapper.checkCategoryProdExists(productDto.getSysIdDanhMuc())) {
            throw new ResourceNotFoundException("Category Product", "Category Product not found with id: ", productDto.getSysIdDanhMuc().toString());
        }

        // Kiểm tra hinhAnh trước khi upload hoặc giữ nguyên
        String imagePath = null;
        if (productDto.getHinhAnh() != null && !productDto.getHinhAnh().isEmpty()) {
            imagePath = uploadImage(productDto.getHinhAnh());
        } else if (productDto.getHinhAnh() != null) {
            imagePath = productDto.getHinhAnh().toString();  // Giữ nguyên đường dẫn nếu không upload
        }

        Product product = productConverter.toProduct(productDto);
        product.setHinhAnh(imagePath);

        try {
            if (productMapper.checkProductExists(productDto.getSysIdSanPham())) {
                productMapper.updateProduct(product);
            } else {
                productMapper.insertProduct(product);
            }
        } catch (Exception e) {
            logger.error("Insert or update category failed: {}", e.getMessage());
            throw new BadSqlGrammarException("Save category failed");
        }
        return productConverter.toProductDto(product);


    }

    @Override
    public void deleteProduct(Integer maSanPham) {
        if (productMapper.checkProductExists(maSanPham))
            productMapper.deleteProduct(maSanPham);
        else
            throw new ResourceNotFoundException("Product", "Product not found with id: ", maSanPham.toString());
    }


    @Override
    public ProductDto getAllSanPhamById(Integer maSanPham) {
        return productMapper.getProductByMaSanPham(maSanPham)
                .map(
                        product -> {
                            product.setHinhAnh(generateImageUrl(product.getHinhAnh()));
                            return productConverter.toProductDto(product);
                        }
                ).orElseThrow(() -> new ResourceNotFoundException("Product","Product not found with id: " ,maSanPham.toString()));
    }
    private String generateImageUrl(String hinhAnh) {
        return s3Service.generatePresignedUrl(hinhAnh);
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

}
