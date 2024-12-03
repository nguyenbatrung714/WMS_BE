package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.wms_be.entity.account.Role;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    private Integer sysIdUser;
    private String username;
    private String email;
    private String fullName;
    private String soDienThoai;
    private Boolean active;
    private String hinhAnh;
    private Set<Role> roles;
}
