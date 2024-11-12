package org.example.wms_be.service;

import org.example.wms_be.entity.account.User;
import org.example.wms_be.utils.ChangePassWord;

public interface ForgotPassWordService {
    User findByEmail(String email);
    User findByOtpWithEmail(Integer otp, String email);
    User saveForgotPass(String email, ChangePassWord changePassWord);
}
