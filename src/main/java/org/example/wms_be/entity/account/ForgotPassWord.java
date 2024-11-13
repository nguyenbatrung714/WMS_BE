package org.example.wms_be.entity.account;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPassWord {
    private Integer id;
    private Integer otp;
    private LocalDateTime createdAt;
    private LocalDateTime expirationTime;
    private Integer sysIdUser;
}
