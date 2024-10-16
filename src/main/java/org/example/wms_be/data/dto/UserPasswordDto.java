package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPasswordDto {
    private String username;
    private String password;
    private String confirmPassword;
}
