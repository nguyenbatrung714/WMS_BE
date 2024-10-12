package org.example.wms_be.mapper.account;

import org.apache.ibatis.annotations.*;
import org.example.wms_be.entity.account.User;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    User findUserByUsername(String username);

    boolean checkUserExits(Integer username);

    Map<String, String> getEmailByRoles(String email);
    String getFullNameByRoles(Integer roleId);
}
