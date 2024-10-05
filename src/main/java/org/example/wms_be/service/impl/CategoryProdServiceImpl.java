package org.example.wms_be.service.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.AllArgsConstructor;
import org.example.wms_be.converter.CategoryProdConverter;
import org.example.wms_be.converter.WarehouseConverter;
import org.example.wms_be.data.dto.CategoryProdDto;
import org.example.wms_be.entity.categoryProd.CategoryProd;
import org.example.wms_be.entity.warehouse.Zone;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.account.UserMapper;
import org.example.wms_be.mapper.categoryProd.CategoryProdMapper;
import org.example.wms_be.mapper.warehouse.WarehouseMapper;
import org.example.wms_be.service.CategoryProdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryProdServiceImpl implements CategoryProdService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryProdServiceImpl.class);
    private final CategoryProdMapper categoryProdMapper;
    private final CategoryProdConverter categoryProdConverter;
    private final WarehouseMapper warehouseMapper;
    @Override
    public PageInfo<CategoryProdDto> getAllCategoryProd(int page, int size) {
        try {
            PageMethod.startPage(page + 1, size);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error when starting page for CategoryProd");
        }

        return new PageInfo<>(
                categoryProdMapper.getAllCategoryProd()
                        .stream()
                        .map(categoryProdConverter::toCategoryProdDto)
                        .toList());
    }

    @Override
    public CategoryProdDto saveCategoryProd(CategoryProdDto categoryProdDto) {
        if (!warehouseMapper.checkWarehouseExists(categoryProdDto.getMaKho())) {
            throw new ResourceNotFoundException("Warehouse", "maKho", categoryProdDto.getMaKho());
        }

        CategoryProd categoryProd = categoryProdConverter.toCategoryProd(categoryProdDto);

        try {
            if (categoryProdMapper.checkCategoryProdExists(categoryProdDto.getSysIdDanhMuc())) {
                categoryProdMapper.updateCategoryProd(categoryProd);
                categoryProd = categoryProdMapper.getCategoryProdByMaDanhMuc(categoryProdDto.getSysIdDanhMuc());
            } else {
                categoryProdMapper.insertCategoryProd(categoryProd);
            }
        } catch (Exception e) {
            logger.error("Insert or update category failed: {}", e.getMessage());
            throw new BadSqlGrammarException("Save category failed");
        }

        return categoryProdConverter.toCategoryProdDto(categoryProd);
    }

    @Override
    public CategoryProdDto getCategoryProdById(Integer sysIdDanhMuc) {
        if (!categoryProdMapper.checkCategoryProdExists(sysIdDanhMuc)) {
            throw new ResourceNotFoundException("CategoryProd", "maDanhMuc", sysIdDanhMuc.toString());
        }

        try {
            CategoryProd categoryProd = categoryProdMapper.getCategoryProdByMaDanhMuc(sysIdDanhMuc);
            return categoryProdConverter.toCategoryProdDto(categoryProd);
        } catch (Exception e) {
            throw new BadSqlGrammarException("Get categoryProd failed");
        }

    }

    @Override
    public Void deleteCategoryProd(Integer maDanhMuc) {
        if (!categoryProdMapper.checkCategoryProdExists(maDanhMuc)) {
            throw new ResourceNotFoundException("Zone", "maKhuVuc", maDanhMuc.toString());
        }

        try {
            categoryProdMapper.deleteCategoryProd(maDanhMuc);
            return null;
        } catch (Exception e) {
            logger.error("Delete zone failed: {}", e.getMessage());
            throw new BadSqlGrammarException("Delete categoryProd failed");
        }
    }
}
