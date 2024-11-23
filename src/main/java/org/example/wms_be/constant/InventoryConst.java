package org.example.wms_be.constant;

import lombok.Getter;

@Getter
public class InventoryConst {
    public static final String NOT_ENOUGH_QUANTITY = "Số lượng tồn kho không đủ";
    public static final String NOT_FOUND_INVENTORY = "Không tìm thấy số lượng cần xuất cho chi tiết xuất hàng";
    public static final String GET_FIRSTOUT_FAILED = "Lấy thông tin lô hàng đầu tiên thất bại";

    private InventoryConst() {
        throw new IllegalStateException("Utility class");
    }
}
