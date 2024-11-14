package org.example.wms_be.data.mgt;


import lombok.Getter;

@Getter
public enum RespMessageForgetPass {
    SEND_OTP_SUCCESS("Gửi mã OTP thành công"),
    SEND_OTP_FAILED("Gửi mã OTP thất bại"),
    VERIFY_OTP_SUCCESS("Xác thực mã OTP thành công"),
    VERIFY_OTP_FAILED("Xác thực mã OTP thất bại"),
    EXPIRED_OTP("Mã OTP đã hết hạn"),
    RESET_PASS_SUCCESS("Đặt lại mật khẩu thành công"),
    RESET_PASS_FAILED("Đặt lại mật khẩu thất bại");
    private final String message;

    RespMessageForgetPass(String message) {
        this.message = message;
    }

}
