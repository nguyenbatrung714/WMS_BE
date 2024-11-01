package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.CategoryProdConverter;
import org.example.wms_be.data.dto.CategoryProdDto;
import org.example.wms_be.entity.product.CategoryProd;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.product.CategoryProdMapper;
import org.example.wms_be.mapper.warehouse.WarehouseMapper;
import org.example.wms_be.service.CategoryProdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryProdServiceImpl implements CategoryProdService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryProdServiceImpl.class);
    private final CategoryProdMapper categoryProdMapper;
    private final CategoryProdConverter categoryProdConverter;
    private final WarehouseMapper warehouseMapper;

    @Override
    public List<CategoryProdDto> getAllCategoryProd() {
        return categoryProdMapper.getAllCategoryProd()
                .stream()
                .map(categoryProdConverter::toCategoryProdDto)
                .toList();
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
            throw new BadSqlGrammarException("Save category failed");
        }

        return categoryProdConverter.toCategoryProdDto(categoryProd);
    }

    @Override
    public CategoryProdDto getCategoryProdById(Integer sysIdDanhMuc) {
        if (!categoryProdMapper.checkCategoryProdExists(sysIdDanhMuc)) {
            throw new ResourceNotFoundException("Category product", "maDanhMuc", sysIdDanhMuc + "");
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
            throw new ResourceNotFoundException("Category product", "maKhuVuc", maDanhMuc.toString());
        }

        try {
            categoryProdMapper.deleteCategoryProd(maDanhMuc);
            return null;
        } catch (Exception e) {
            throw new BadSqlGrammarException("Delete categoryProd failed");
        }
    }
}
