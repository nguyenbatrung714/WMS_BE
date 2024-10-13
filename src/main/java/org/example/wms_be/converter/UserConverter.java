package org.example.wms_be.converter;

import org.example.wms_be.data.dto.UserDto;
import org.example.wms_be.entity.account.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConverter {
    User  toUser (UserDto userDto);
    UserDto toUserDto (User user);
}
