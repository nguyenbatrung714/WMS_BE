package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.wms_be.entity.account.Role;
import org.example.wms_be.entity.account.UserRole;

import java.util.Set;

@Getter
@Setter
public class AccountDto {
    private Integer sysIdUser;
    private String username;
    private String email;
    private String fullName;
    private String password;
    private String soDienThoai;
    private Integer sysIdRole;

}
