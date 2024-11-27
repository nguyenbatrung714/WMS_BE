package org.example.wms_be.service;

import org.example.wms_be.data.dto.AccountDto;
import org.example.wms_be.data.dto.CategoryProdDto;
import org.example.wms_be.data.dto.UserDto;

public interface AccountManagerService {
    AccountDto insertAccount(AccountDto accountDto);

    Void deleteAccount(String username);
}
