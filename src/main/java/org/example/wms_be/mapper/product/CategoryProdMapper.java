package org.example.wms_be.mapper.product;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.product.CategoryProd;

import java.util.List;

@Mapper
public interface CategoryProdMapper {
    List<CategoryProd> getAllCategoryProd();

    int insertCategoryProd(CategoryProd categoryProd);

    int updateCategoryProd(CategoryProd categoryProd);

    int deleteCategoryProd(Integer maDanhMuc);

    boolean checkCategoryProdExists(Integer maDanhMuc);

    CategoryProd getCategoryProdByMaDanhMuc(Integer maDanhMuc);
}
