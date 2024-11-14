package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.RoleConverter;
import org.example.wms_be.data.dto.RoleDto;
import org.example.wms_be.mapper.account.RoleMapper;
import org.example.wms_be.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleConverter roleConverter;
    @Override
    public List<RoleDto> getAllRoles() {
        return roleMapper.getAllRoles()
                .stream()
                .map(roleConverter ::toRoleDto)
                .toList();
    }
}
