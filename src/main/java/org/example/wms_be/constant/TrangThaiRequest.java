package org.example.wms_be.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TrangThaiRequest {
    DANG_XU_LY("Đang xử lý"),
    DA_HUY("Đã hủy"),
    XAC_NHAN("Xác nhận");
    private String displayName;

    // Phương thức để chuyển đổi từ chuỗi thành enum
    public static TrangThaiRequest fromValue(String value) {
        for (TrangThaiRequest trangThai : values()) {
            if (trangThai.name().equalsIgnoreCase(value)) {
                return trangThai;
            }
        }
        throw new IllegalArgumentException("Trạng thái không hợp lệ: " + value);
    }
}
