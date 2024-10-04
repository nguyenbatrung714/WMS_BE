package org.example.wms_be.mapper.account;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.account.User;

@Mapper
public interface UserMapper {

    User findUserByUsername(String username);

}
