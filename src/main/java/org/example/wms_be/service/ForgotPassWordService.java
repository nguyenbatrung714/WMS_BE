package org.example.wms_be.service;

import org.example.wms_be.entity.account.User;
import org.example.wms_be.utils.ChangePassWord;

public interface ForgotPassWordService {
    void findByEmail(String email);
    void findByOtpWithEmail(Integer otp, String email);
    void saveForgotPass(String email, ChangePassWord changePassWord);
}
