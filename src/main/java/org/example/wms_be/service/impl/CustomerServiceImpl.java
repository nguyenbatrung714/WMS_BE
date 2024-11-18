package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.CustomerConverter;
import org.example.wms_be.data.dto.CustomerDto;
import org.example.wms_be.entity.customer.Customer;
import org.example.wms_be.entity.customer.Supplier;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.customer.CustomerMapper;
import org.example.wms_be.mapper.customer.SupplierMapper;
import org.example.wms_be.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private final CustomerMapper customerMapper;
    private final CustomerConverter customerConverter;
    private final SupplierMapper supplierMapper;

    @Override
    public List<CustomerDto> getAllCustomer() {
        return customerMapper.getAllCustomer()
                .stream().map(customerConverter::toCustomerDto)
                .toList();
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer customer = customerConverter.toCustomer(customerDto);
        try {
            if (customerMapper.checkCustomerExits(customerDto.getSysIdKhachHang())) {
                customerMapper.updateCustomer(customer);
                customer = customerMapper.getCustomerById(customerDto.getSysIdKhachHang());
            } else {
                customerMapper.insertCustomer(customer);
            }

        } catch (Exception e) {
            logger.error("Insert or update customer failed: {}", e.getMessage());
            throw new BadSqlGrammarException("Save customer failed");
        }
        return customerConverter.toCustomerDto(customer);

    }

    @Override
    public Void deleteCustomer(Integer maKhachHang) {
        if (!customerMapper.checkCustomerExits(maKhachHang)) {
            throw new ResourceNotFoundException("Customer", "maKhachHang", maKhachHang.toString());
        }
        try {
            customerMapper.deleteCustomer(maKhachHang);
            return null;
        } catch (Exception e) {
            throw new BadSqlGrammarException("Get customer failed");
        }
    }


    @Override
    public CustomerDto getAllCustomerById(Integer maKhachHang) {
        if (!customerMapper.checkCustomerExits(maKhachHang)) {
            throw new ResourceNotFoundException("Customer", "maKhachHang", maKhachHang.toString());
        }
        try {
            Customer customer = customerMapper.getCustomerById(maKhachHang);
            return customerConverter.toCustomerDto(customer);
        } catch (Exception e) {
            throw new BadSqlGrammarException("Get customer failed");
        }
    }


}

