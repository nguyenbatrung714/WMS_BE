package org.example.wms_be.mapper.customer;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.customer.Customer;

import java.util.List;

@Mapper
public interface CustomerMapper {
    List<Customer> getAllCustomer();
    int insertCustomer(Customer customer);
    int updateCustomer(Customer customer);
    int deleteCustomer(Integer maKhachHang);
    boolean checkCustomerExits(Integer maKhachHang);
    Customer getCustomerById(Integer maKhachHang);



}
