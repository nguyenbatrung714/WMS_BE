package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Integer sysIdUser;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String fullName;
    private String soDienThoai;
}
