package org.example.wms_be.constant;

import lombok.Getter;

@Getter
public class PrConst {
    public static final String DEFAULT_USER_REQUESTING = "Không xác định";
    public static final String DEFAULT_ROLE = "Không xác định";
    public static final String DEFAULT_FULL_NAME = "Không xác định";
    private PrConst() {
        throw new IllegalStateException("Utility class");
    }
}