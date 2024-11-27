package org.example.wms_be.mapper.account;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.data.dto.AccountDto;
import org.example.wms_be.entity.account.Account;
import org.example.wms_be.entity.account.User;
import org.example.wms_be.entity.account.UserRole;

@Mapper
public interface AccountManagerMapper {
    int insertAccount(Account account);
    int insertUserRole(UserRole userRole);

    int deleteAccount(String username);
    boolean checkUserExitsByUserName(String username);
    boolean checkUserExitsById(Integer id);
    boolean checkUserRoleExists(Integer sysIdUser,Integer sysIdRole);

}
