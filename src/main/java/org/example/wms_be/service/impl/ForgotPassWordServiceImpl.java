package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.constant.ForgotPassWordConst;
import org.example.wms_be.entity.account.ForgotPassWord;
import org.example.wms_be.entity.account.User;
import org.example.wms_be.mapper.account.ForgotPassWordMapper;
import org.example.wms_be.mapper.account.UserMapper;
import org.example.wms_be.service.ForgotPassWordService;
import org.example.wms_be.utils.ChangePassWord;
import org.example.wms_be.utils.EmailUtil;
import org.example.wms_be.utils.MailBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForgotPassWordServiceImpl implements ForgotPassWordService {
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(ForgotPassWordServiceImpl.class);
    private final EmailUtil emailUtil;
    private final ForgotPassWordMapper forgotPassWordMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        if (isValidEmail(email)) {
            throw new IllegalArgumentException(ForgotPassWordConst.INVALID_EMAIL);
        }
        User user = userMapper.findByEmail(email);
//        logger.info("Raw user from database: {}", user);
//        logger.info("sys_id_user value: {}", user.getSysIdUser());
        Optional<User> userOpt = Optional.of(user);
        userOpt.ifPresent(u -> logger.info("User found: {}", user.getUsername()));

        int otpCount = forgotPassWordMapper.countOtpRequestsInTimeRange(user.getSysIdUser());
        if (otpCount >= 3) {
            logger.warn(" {} đã yêu cầu OTP quá 3 lần", email);
            throw new IllegalArgumentException("Bạn đã yêu cầu OTP quá nhiều lần. Vui lòng thử lại sau 60 giây.");
        }
        int otp = otpGenerator();

        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("Mã OTP để đặt lại mật khẩu của bạn là: " + otp +
                        "\nMã này sẽ hết hạn sau " +
                        ForgotPassWordConst.OTP_TIMEOUT_SECONDS + " giây.")
                .subject("Yêu cầu đặt lại mật khẩu")
                .build();

        ForgotPassWord fp = ForgotPassWord.builder()
                .otp(otp)
                .expirationTime(LocalDateTime.now().plusSeconds(ForgotPassWordConst.OTP_TIMEOUT_SECONDS))
                .createdAt(LocalDateTime.now())
                .sysIdUser(user.getSysIdUser())
                .build();

        try {
            emailUtil.sendMailForgotPass(mailBody);
            forgotPassWordMapper.insertForgotPass(fp);
            logger.info("Đã gửi OTP tới email: {}", email);
        } catch (MailException e) {
            logger.error("Lỗi khi gửi email: {}", e.getMessage());
            throw new IllegalArgumentException(ForgotPassWordConst.EMAIL_SEND_FAILED);
        }

        return user;
    }

    @Override
    public User findByOtpWithEmail(Integer otp, String email) {
        if (isValidEmail(email)) {
            throw new IllegalArgumentException(ForgotPassWordConst.INVALID_EMAIL);
        }

        User user = userMapper.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException(ForgotPassWordConst.INVALID_USER);
        }

        ForgotPassWord fp = forgotPassWordMapper.findByOtpAndUser(otp, user.getSysIdUser());
        if (fp == null) {
            throw new IllegalArgumentException(ForgotPassWordConst.INVALID_OTP);
        }

        LocalDateTime now = LocalDateTime.now();
        if (fp.getExpirationTime().isBefore(now)) {
            forgotPassWordMapper.deleteForgotPass(fp.getId());
            throw new IllegalArgumentException(ForgotPassWordConst.INVALID_EXPIRATION_TIME);
        }
        forgotPassWordMapper.deleteForgotPass(fp.getId());
        logger.info("OTP hợp lệ : {}", user.getUsername());

        return user;
    }


    @Override
    public User saveForgotPass(String email, ChangePassWord changePassWord) {
        if (!Objects.equals(changePassWord.password(), changePassWord.repeatPassword())) {
            throw new IllegalArgumentException("Password and repeat password do not match");
        }
        String encodedPassword = passwordEncoder.encode(changePassWord.password());
        userMapper.updateForgotPass(email, encodedPassword);
        return userMapper.findByEmail(email);
    }

    private final Random random = new Random();

    private int otpGenerator() {
        return random.nextInt(900_000) + 100_000;
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email == null || !email.matches(emailRegex);
    }
}
