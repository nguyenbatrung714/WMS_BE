package org.example.wms_be.constant;

import lombok.Getter;

@Getter
public class PrConst {
    public static final String DEFAULT_USER_REQUESTING = "Không xác định";
    public static final String DEFAULT_ROLE = "Không xác định";
    public static final String DEFAULT_FULL_NAME = "Không xác định";
    public static final String DEFAULT_PR_EMAIL = "giangvthps29208@fpt.edu.vn"; // Địa chỉ email mặc định của PR
    public static final String DEFAULT_PO_EMAIL = "baothiien2004@gmail.com"; // Địa chỉ email mặc định của PO
    private PrConst() {
        throw new IllegalStateException("Utility class");
    }
}
