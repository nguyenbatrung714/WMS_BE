package org.example.wms_be.converter;

import org.example.wms_be.data.dto.RoleDto;
import org.example.wms_be.entity.account.Role;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface RoleConverter {
    Role toRole(RoleDto roleDto);
    RoleDto toRoleDto(Role role);
}
