package org.example.wms_be.constant;

public class ForgotPassWordConst {
    public static final String INVALID_EMAIL = " Email không hợp lệ";
    public static final String INVALID_OTP = "Mã OTP không hợp lệ";
    public static final String INVALID_EXPIRATION_TIME = "Mã OTP đã hết hạn";
    public static final String INVALID_USER = "Người dùng không tồn tại";
    public static final String EMAIL_SEND_FAILED = "Gửi email thất bại";
    public static final Integer OTP_TIMEOUT_SECONDS = 20;

    private ForgotPassWordConst() {
        throw new IllegalStateException("Utility class");
    }

}
