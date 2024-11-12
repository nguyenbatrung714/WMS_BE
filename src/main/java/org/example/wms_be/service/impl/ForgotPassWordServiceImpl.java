package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
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

import java.time.Instant;
import java.util.Date;
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
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email");
        }
        User user = userMapper.findByEmail(email);
        logger.info("Raw user from database: {}", user);
        logger.info("sys_id_user value: {}", user.getSysIdUser());
        Optional<User> userOpt = Optional.of(user);
        userOpt.ifPresent(u -> logger.info("User found: {}", user.getUsername()));
        int otp = otpGenerator();

        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your password reset: " + otp)
                .subject("OTP for forgot password request")
                .build();

        ForgotPassWord fp = ForgotPassWord.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 30_000))
                .createdAt(new Date())
                .sysIdUser(user.getSysIdUser())
                .build();

        try {
            // Gửi email
            emailUtil.sendMailForgotPass(mailBody);
        } catch (MailException e) {
            logger.error("Failed to send email");
        }
        forgotPassWordMapper.insertForgotPass(fp);
        return user;
    }

    @Override
    public User findByOtpWithEmail(Integer otp, String email) {
        User user = userMapper.findByEmail(email);
        Optional<User> userOpt = Optional.of(user);
        userOpt.ifPresent(u -> logger.info(" Hay Cung cap email hop le: {}", user.getUsername()));
        ForgotPassWord fp = forgotPassWordMapper.findByOtpAndUser(otp, user.getSysIdUser());
                Optional<ForgotPassWord> fpOpt = Optional.of(fp);
        fpOpt.ifPresent(f -> logger.info("Otp cung cap cho mail khong hop le: {}", fp.getOtp()));
        if (fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPassWordMapper.deleteForgotPass(fp.getId());

        }
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
        return random.nextInt(900_000) + 100_000; // Đảm bảo OTP có 6 chữ số
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && email.matches(emailRegex);
    }
}
