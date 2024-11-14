package org.example.wms_be.mapper.account;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.account.User;

import java.util.List;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    List<User> findAll();
    User findUserByUsername(String username);
    User findUserById(Integer id);
    boolean checkUserExits(Integer username);
    boolean checkUserExitsByUserName(String username);

    int updateInfo(User user);
    int updateMatKhau(User user);

    Map<String, String> getEmailByRoles(String email);
    String getFullNameByRoles(Integer roleId);
    boolean checkMailExits(String email);

    User findByEmail(String email);
    int updateForgotPass(String email, String password);
}
