package org.example.wms_be.mapper.categoryProd;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.categoryProd.CategoryProd;
import org.example.wms_be.entity.warehouse.ZoneDetail;

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
