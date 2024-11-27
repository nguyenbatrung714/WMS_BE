package org.example.wms_be.mapper.account;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.account.Role;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role>getAllRoles();

}
