package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.constant.ForgotPassWordConst;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.mgt.RespMessageForgotPass;
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
    public ResponseEntity<ApiResponse<RespMessageForgotPass>> verifyEmail(
            @PathVariable String email,
            HttpServletRequest request) {
        try {
            forgotPassWordService.findByEmail(email);
            ApiResponse<RespMessageForgotPass> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessageForgotPass.SEND_OTP_SUCCESS.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage().equals(ForgotPassWordConst.EMAIL_NOT_EXIST)) {
                ApiResponse<RespMessageForgotPass> response = new ApiResponse<>(
                        request.getRequestURI(),
                        HttpStatus.NOT_FOUND.value(),
                        RespMessageForgotPass.EMAIL_NOT_FOUND.getMessage(),
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else if (e.getMessage().equals(ForgotPassWordConst.EMAIL_SPAM_ERROR)) {
                ApiResponse<RespMessageForgotPass> response = new ApiResponse<>(
                        request.getRequestURI(),
                        HttpStatus.TOO_MANY_REQUESTS.value(),
                        RespMessageForgotPass.SEND_OTP_SPAM.getMessage(),
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.TOO_MANY_REQUESTS);
            }
            ApiResponse<RespMessageForgotPass> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    RespMessageForgotPass.SEND_OTP_FAILED.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify-otp/{otp}/{email}")
    public ResponseEntity<ApiResponse<RespMessageForgotPass>> verifyOtp(
            @PathVariable Integer otp,
            @PathVariable String email,
            HttpServletRequest request) {
        try {
            forgotPassWordService.findByOtpWithEmail(otp, email);
            ApiResponse<RespMessageForgotPass> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessageForgotPass.VERIFY_OTP_SUCCESS.getMessage(),
                    null

            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage().equals(ForgotPassWordConst.INVALID_OTP)) {
                ApiResponse<RespMessageForgotPass> response = new ApiResponse<>(
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST.value(),
                        RespMessageForgotPass.OTP_IS_INCORRECT.getMessage(),
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else if (e.getMessage().equals(ForgotPassWordConst.INVALID_EXPIRATION_TIME)) {
                logger.error("OTP đã hết hạn! Xác thực thất bại", e);
                ApiResponse<RespMessageForgotPass> response = new ApiResponse<>(
                        request.getRequestURI(),
                        HttpStatus.GONE.value(),
                        RespMessageForgotPass.EXPIRED_OTP.getMessage(),
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.GONE);
            }
            ApiResponse<RespMessageForgotPass> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    RespMessageForgotPass.VERIFY_OTP_FAILED.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<ApiResponse<RespMessageForgotPass>> changePassword(
            @PathVariable String email,
            @RequestBody ChangePassWord changePassWord,
            HttpServletRequest request) {
        try {
            forgotPassWordService.saveForgotPass(email, changePassWord);
            ApiResponse<RespMessageForgotPass> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessageForgotPass.RESET_PASS_SUCCESS.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage().equals(ForgotPassWordConst.PASSWORD_NOT_MATCH)) {
                ApiResponse<RespMessageForgotPass> response = new ApiResponse<>(
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST.value(),
                        RespMessageForgotPass.RESET_PASS_NOT_MATCH.getMessage(),
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
            ApiResponse<RespMessageForgotPass> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    RespMessageForgotPass.RESET_PASS_FAILED.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
