package org.example.wms_be.converter;

import org.example.wms_be.data.dto.CustomerDto;
import org.example.wms_be.entity.customer.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerConverter {

    Customer toCustomer(CustomerDto customerDto);

    CustomerDto toCustomerDto(Customer customer);
}
