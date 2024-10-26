package org.example.wms_be.converter;

import org.example.wms_be.data.dto.UserDto;
import org.example.wms_be.data.dto.UserInfoDto;
import org.example.wms_be.data.dto.UserPasswordDto;
import org.example.wms_be.entity.account.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConverter {
    User  toUser (UserDto userDto);
    UserDto toUserDto (User user);
    User  toUserPassword (UserPasswordDto userDto);
    UserPasswordDto toUserPasswordDto (User user);
    User  toUserInfo (UserInfoDto userDto);
    UserInfoDto toUserInfoDto (User user);
}
