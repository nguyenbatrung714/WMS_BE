package org.example.wms_be.service;

import org.example.wms_be.data.dto.UserDto;
import org.example.wms_be.data.dto.UserInfoDto;
import org.example.wms_be.data.dto.UserPasswordDto;
import org.example.wms_be.data.request.LockAccountReq;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUser();
    UserInfoDto updateUserInfo(UserInfoDto userDto);
    UserPasswordDto updatePassword(UserPasswordDto userDto);
    UserDto findUserById(Integer sysIdUser);
    UserDto findUserByUsername(String username);

    LockAccountReq lockAccount(LockAccountReq lockAccountReq);
}
