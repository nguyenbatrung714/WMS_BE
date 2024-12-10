package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.wms_be.entity.account.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
@Getter
@Setter
public class UserInfoDto {
    private Integer sysIdUser;
    private String username;
    private String email;
    private String fullName;
    private String soDienThoai;
    private MultipartFile hinhAnh;
    private Set<Role> roles;
}
