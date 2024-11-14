package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.constant.ForgotPassWordConst;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.mgt.RespMessageForgetPass;
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
    public ResponseEntity<ApiResponse<RespMessageForgetPass>> verifyEmail(
            @PathVariable String email,
            HttpServletRequest request) {
        try {
            forgotPassWordService.findByEmail(email);
            ApiResponse<RespMessageForgetPass> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessageForgetPass.SEND_OTP_SUCCESS.getMessage(),
                    null

            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("mail không tồn tại", e);
            ApiResponse<RespMessageForgetPass> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                   RespMessageForgetPass.SEND_OTP_FAILED.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify-otp/{otp}/{email}")
    public ResponseEntity<ApiResponse<RespMessageForgetPass>> verifyOtp(
            @PathVariable Integer otp,
            @PathVariable String email,
            HttpServletRequest request) {
        try {
            forgotPassWordService.findByOtpWithEmail(otp, email);
            ApiResponse<RespMessageForgetPass> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessageForgetPass.VERIFY_OTP_SUCCESS.getMessage(),
                    null

            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            if (e.getMessage().equals(ForgotPassWordConst.INVALID_OTP)) {
                logger.error("OTP không hợp lệ! Xác thực thất bại", e);
                ApiResponse<RespMessageForgetPass> response = new ApiResponse<>(
                        request.getRequestURI(),
                        HttpStatus.BAD_REQUEST.value(),
                        RespMessageForgetPass.VERIFY_OTP_FAILED.getMessage(),
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else if (e.getMessage().equals(ForgotPassWordConst.INVALID_EXPIRATION_TIME)) {
                logger.error("OTP đã hết hạn! Xác thực thất bại", e);
                ApiResponse<RespMessageForgetPass> response = new ApiResponse<>(
                        request.getRequestURI(),
                        HttpStatus.GONE.value(),
                        RespMessageForgetPass.EXPIRED_OTP.getMessage(),
                        null
                );
                return new ResponseEntity<>(response, HttpStatus.GONE);
            }
            logger.error("Xác thực mã OTP thất bại", e);
            ApiResponse<RespMessageForgetPass> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Xác thực mã OTP thất bại",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<ApiResponse<RespMessageForgetPass>> changePassword(
            @PathVariable String email,
            @RequestBody ChangePassWord changePassWord,
            HttpServletRequest request) {
        try {
            forgotPassWordService.saveForgotPass(email, changePassWord);
            ApiResponse<RespMessageForgetPass> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    RespMessageForgetPass.RESET_PASS_SUCCESS.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Thay đổi mật khẩu thất bại", e);
            ApiResponse<RespMessageForgetPass> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    RespMessageForgetPass.RESET_PASS_FAILED.getMessage(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
