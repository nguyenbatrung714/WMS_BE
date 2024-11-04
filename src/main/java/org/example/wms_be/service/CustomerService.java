package org.example.wms_be.service;

import org.example.wms_be.data.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomer();
    CustomerDto saveCustomer(CustomerDto customerDto);
    Void deleteCustomer(Integer maKhachHang);
    CustomerDto getAllCustomerById(Integer maKhachHang);

}
