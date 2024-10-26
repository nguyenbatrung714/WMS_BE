package org.example.wms_be.service;

import org.example.wms_be.data.dto.CategoryProdDto;

import java.util.List;

public interface CategoryProdService {
    List<CategoryProdDto> getAllCategoryProd();

    CategoryProdDto saveCategoryProd(CategoryProdDto categoryProdDto);

    CategoryProdDto getCategoryProdById(Integer sysIdDanhMuc);

    Void deleteCategoryProd(Integer maDanhMuc);


}
