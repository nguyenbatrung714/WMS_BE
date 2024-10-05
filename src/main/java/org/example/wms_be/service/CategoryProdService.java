package org.example.wms_be.service;

import com.github.pagehelper.PageInfo;
import org.example.wms_be.data.dto.CategoryProdDto;
import org.example.wms_be.data.dto.WarehouseDto;

public interface CategoryProdService {
    PageInfo<CategoryProdDto> getAllCategoryProd(int page, int size);

    CategoryProdDto saveCategoryProd(CategoryProdDto categoryProdDto);

    CategoryProdDto getCategoryProdById(Integer sysIdDanhMuc);

    Void deleteCategoryProd(Integer maDanhMuc);


}
