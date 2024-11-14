package org.example.wms_be.entity.account;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Account {
    private Integer sysIdUser;
    private String username;
    private String email;
    private String fullName;
    private String password;
    private String soDienThoai;
    private Integer sysIdRole;


}
