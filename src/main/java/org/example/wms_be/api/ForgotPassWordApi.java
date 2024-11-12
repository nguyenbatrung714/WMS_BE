package org.example.wms_be.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.mgt.ApiResponse;
import org.example.wms_be.data.mgt.RespMessagePurchaseReuqestIb;
import org.example.wms_be.data.response.inbound.PurchaseRequestIbResp;
import org.example.wms_be.entity.account.User;
import org.example.wms_be.service.ForgotPassWordService;
import org.example.wms_be.utils.ChangePassWord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forgot-password")
@RequiredArgsConstructor
public class ForgotPassWordApi {
    private final ForgotPassWordService forgotPassWordService;
    private final Logger logger = LoggerFactory.getLogger(ForgotPassWordApi.class);
    @PostMapping("/verify-mail/{email}")
    public ResponseEntity<ApiResponse<Object>> verifyEmail(
            @PathVariable String email,
            HttpServletRequest request) {
        try {
            ApiResponse<Object> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    "send mail success",
                    forgotPassWordService.findByEmail(email)
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("send mail failed", e);
            ApiResponse<Object> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                   "send mail failed",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/verify-otp/{otp}/{email}")
    public ResponseEntity<ApiResponse<Object>> verifyOtp(
            @PathVariable Integer otp,
            @PathVariable String email,
            HttpServletRequest request) {
        try {
            ApiResponse<Object> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    "otp verify success",
                    forgotPassWordService.findByOtpWithEmail(otp, email)
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("otp failed", e);
            ApiResponse<Object> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "otp failed",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/change-password/{email}")
    public ResponseEntity<ApiResponse<Object>> changePassword(
            @PathVariable String email,
            @RequestBody ChangePassWord changePassWord,
            HttpServletRequest request) {
        try {
            ApiResponse<Object> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.OK.value(),
                    "change password success",
                    forgotPassWordService.saveForgotPass(email,changePassWord)
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("change password failed", e);
            ApiResponse<Object> response = new ApiResponse<>(
                    request.getRequestURI(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "change password failed",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
