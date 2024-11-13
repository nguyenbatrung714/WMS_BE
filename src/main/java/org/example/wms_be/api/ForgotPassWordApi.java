package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.constant.ForgotPassWordConst;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.entity.account.User;
import org.example.wms_be.service.ForgotPassWordService;
import org.example.wms_be.utils.ChangePassWord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/forgot-password")
@RequiredArgsConstructor
@CrossOrigin
public class ForgotPassWordApi {
    private final ForgotPassWordService forgotPassWordService;
    private final Logger logger = LoggerFactory.getLogger(ForgotPassWordApi.class);

    @PostMapping("/verify-mail/{email}")
    public ResponseEntity<ApiResponse<User>> verifyEmail(
            @PathVariable String email,
            HttpServletRequest request) {
        try {
            ApiResponse<User> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    "mã OTP đã được gửi",
                    forgotPassWordService.findByEmail(email)
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("mail không tồn tại", e);
            ApiResponse<User> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "mail không tồn tại",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify-otp/{otp}/{email}")
    public ResponseEntity<ApiResponse<User>> verifyOtp(
            @PathVariable Integer otp,
            @PathVariable String email,
            HttpServletRequest request) {
        try {
            ApiResponse<User> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    "Xác thực mã OTP thành công",
                    forgotPassWordService.findByOtpWithEmail(otp, email)
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage().equals(ForgotPassWordConst.INVALID_OTP)) {
                logger.error("OTP không hợp lệ! Xác thực thất bại", e);
                ApiResponse<User> response = new ApiResponse<>(
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST.value(),
                        "OTP không hợp lệ! Xác thực thất bại",
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else if (e.getMessage().equals(ForgotPassWordConst.INVALID_EXPIRATION_TIME)) {
                logger.error("OTP đã hết hạn! Xác thực thất bại", e);
                ApiResponse<User> response = new ApiResponse<>(
                        request.getRequestURI(),
                        HttpStatus.GONE.value(),
                        "OTP đã hết hạn! Xác thực thất bại",
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            logger.error("Xác thực mã OTP thất bại", e);
            ApiResponse<User> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Xác thực mã OTP thất bại",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<ApiResponse<User>> changePassword(
            @PathVariable String email,
            @RequestBody ChangePassWord changePassWord,
            HttpServletRequest request) {
        try {
            ApiResponse<User> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    "Thay đổi mật khẩu thành công",
                    forgotPassWordService.saveForgotPass(email, changePassWord)
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Thay đổi mật khẩu thất bại", e);
            ApiResponse<User> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Thay đổi mật khẩu thất bại",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
