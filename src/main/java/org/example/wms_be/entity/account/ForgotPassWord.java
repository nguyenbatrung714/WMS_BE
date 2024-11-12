package org.example.wms_be.entity.account;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPassWord {
    private Integer id;
    private Integer otp;
    private Date createdAt;
    private Date expirationTime;
    private Integer sysIdUser;
}
