package org.example.wms_be.data.dto;

import lombok.*;
import org.example.wms_be.entity.account.User;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPassWorDto {
    private Integer id;
    private Integer otp;
    private Date createdAt;
    private Date expirationTime;
    private Integer sysIdUser;

}
