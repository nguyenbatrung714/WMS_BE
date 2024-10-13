package org.example.wms_be.mapper.account;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.account.User;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
    User findUserByUsername(String username);
    User findUserById(Integer id);

    boolean checkUserExits(Integer username);
    boolean checkUserExitsByUserName(String username);

    int updateInfo(User user);
    int updateMatKhau(User user);
}
