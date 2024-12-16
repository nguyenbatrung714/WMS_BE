package org.example.wms_be.constant;

import lombok.Getter;

@Getter
public class PurchaseRequestConst {
    public static final String DEFAULT_USER_REQUESTING = "Không xác định";
    public static final String DEFAULT_ROLE = "Không xác định";
    public static final String DEFAULT_FULL_NAME = "Không xác định";
    public static final String DEFAULT_PR_EMAIL = "wms.purchase.requisition@gmail.com"; // Địa chỉ email mặc định của PR
    public static final String DEFAULT_PO_EMAIL = "wms.inventory.techtribe@gmail.com"; // Địa chỉ email mặc định của PO
    public static final String DEFAULT_ADMIN_EMAIL = "wms.inventory.techtribe@gmail.com"; // Địa chỉ email mặc định của Admin
    private PurchaseRequestConst() {
        throw new IllegalStateException("Utility class");
    }
}
