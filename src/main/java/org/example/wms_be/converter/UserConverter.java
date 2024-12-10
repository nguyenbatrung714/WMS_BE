package org.example.wms_be.converter;

import org.example.wms_be.data.dto.UserDto;
import org.example.wms_be.data.dto.UserInfoDto;
import org.example.wms_be.data.dto.UserPasswordDto;
import org.example.wms_be.entity.account.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConverter {
    User  toUser (UserDto userDto);

    UserDto toUserDto (User user);

    User  toUserPassword (UserPasswordDto userDto);

    UserPasswordDto toUserPasswordDto (User user);

    @Mapping(target = "hinhAnh", ignore = true)
    User  toUserInfo (UserInfoDto userDto);

    @Mapping(target = "hinhAnh", ignore = true)
    UserInfoDto toUserInfoDto (User user);
}
