package org.example.wms_be.data.mgt;


import lombok.Getter;

@Getter
public enum RespMessageForgetPass {
    SEND_OTP_SUCCESS("Gửi mã OTP thành công"),
    SEND_OTP_FAILED("Gửi mã OTP thất bại"),
    SEND_OTP_SPAM("Gửi mã OTP thất bại! Gửi mail quá 3 lần trong 1 phút"),
    EMAIL_NOT_FOUND(" Gửi mã OTP thất bại! Email không tồn tại"),
    VERIFY_OTP_SUCCESS("Xác thực mã OTP thành công"),
    VERIFY_OTP_FAILED("Xác thực mã OTP thất bại"),
    OTP_IS_INCORRECT("Mã OTP không chính xác"),
    EXPIRED_OTP("OTP đã hết hạn! Xác thực thất bại"),
    RESET_PASS_SUCCESS("Đặt lại mật khẩu thành công"),
    RESET_PASS_FAILED("Đặt lại mật khẩu thất bại"),
    RESET_PASS_NOT_MATCH("Mật khẩu không khớp");
    private final String message;

    RespMessageForgetPass(String message) {
        this.message = message;
    }

}
