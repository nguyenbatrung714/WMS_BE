package org.example.wms_be.service;

import com.github.pagehelper.PageInfo;
import org.example.wms_be.data.dto.UserDto;
import org.example.wms_be.data.response.ProductResp;
import org.example.wms_be.entity.account.User;

public interface UserService {
    PageInfo<UserDto> getAllUser(int page, int size);
    UserDto updateUserInfo(UserDto userDto);
    UserDto updatePassword(UserDto userDto);
    UserDto findUserById(Integer sysIdUser);
}
