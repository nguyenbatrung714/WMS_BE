package org.example.wms_be.mapper.account;

import org.apache.ibatis.annotations.Mapper;
import org.example.wms_be.entity.account.ForgotPassWord;

@Mapper
public interface ForgotPassWordMapper {
    ForgotPassWord findByOtpAndUser(Integer otp, Integer sysIdUser);
    int insertForgotPass (ForgotPassWord forgotPassWord);
    int deleteForgotPass (Integer id);

}
