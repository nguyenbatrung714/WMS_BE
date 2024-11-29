package org.example.wms_be.constant;

public class ForgotPassWordConst {
    public static final String INVALID_EMAIL = " Email không hợp lệ";
    public static final String EMAIL_NOT_EXIST = "Email không tồn tại";
    public static final String EMAIL_SPAM_ERROR = "Gửi mail quá 3 lần trong 1 phút";
    public static final String INVALID_OTP = "Mã OTP không hợp lệ";
    public static final String INVALID_EXPIRATION_TIME = " thời gian hết hạn không hợp lệ";
    public static final String INVALID_USER = "Người dùng không tồn tại";
    public static final String EMAIL_SEND_FAILED = "Gửi email thất bại";
    public static final Integer OTP_TIMEOUT_SECONDS = 300;
    public static final String PASSWORD_NOT_MATCH = "Mật khẩu không khớp";

    private ForgotPassWordConst() {
        throw new IllegalStateException("Utility class");
    }

}
