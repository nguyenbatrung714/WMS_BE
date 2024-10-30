package org.example.wms_be.data.mgt;

import lombok.Getter;

@Getter
public enum RespMessagePurchaseRequestOb {
    SAVE_SUCCESS("Lưu yêu cầu xuất hàng thành công"),
    SAVE_FAILED("Lưu yêu cầu xuất hàng thất bại"),
    GET_SUCCESS("Lấy danh sách yêu cầu xuất hàng thành công"),
    GET_SUCCESS_BY_ID("Lấy yêu cầu xuất hàng theo mã Pr thành công"),
    GET_FAILED_BY_ID("Lấy yêu cầu xuất hàng theo mã Pr thất bại"),
    UPDATE_SUCCESS("Cập nhật yêu cầu xuất hàng thành công"),
    UPDATE_FAILED("Cập nhật yêu cầu xuất hàng thất bại"),
    DELETE_SUCCESS("Xóa yêu cầu xuất hàng thành công"),
    DELETE_FAILED("Xóa yêu cầu xuất hàng thất bại"),
    GET_FAILED("Lấy danh sách yêu cầu xuất hàng thất bại");
    private final String message;

    RespMessagePurchaseRequestOb(String message) {
        this.message = message;
    }
}
