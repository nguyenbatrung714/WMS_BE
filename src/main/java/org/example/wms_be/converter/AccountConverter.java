package org.example.wms_be.converter;

import org.example.wms_be.data.dto.AccountDto;
import org.example.wms_be.data.dto.UserDto;
import org.example.wms_be.entity.account.Account;
import org.example.wms_be.entity.account.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountConverter {
    Account toAccount (AccountDto ccountDto);
    AccountDto toAccountDto (Account account);
}
