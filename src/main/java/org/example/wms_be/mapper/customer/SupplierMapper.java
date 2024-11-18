package org.example.wms_be.mapper.customer;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.customer.Supplier;

import java.util.List;

@Mapper
public interface SupplierMapper {
    List<Supplier> getAllSupplier();
    int insertSupplier(Supplier supplier);
    int updateSupplier(Supplier supplier);
    int deleteSupplier(Integer maNhaCungCap);
    Supplier getSupplierById(Integer maNhaCungCap);
    boolean checkSupplierExits(Integer maNhaCungCap);

}
