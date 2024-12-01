package org.example.wms_be.entity.account;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class User {
    private Integer sysIdUser;
    private String username;
    private String password;
    private String email;
    private String mailQuanTri;
    private String fullName;
    private String soDienThoai;
    private Set<Role> roles;
}
