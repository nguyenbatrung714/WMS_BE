package org.example.wms_be.data.mgt;

import lombok.Getter;

@Getter
public enum RespMessagePurchaseReuqestIb {
    SAVE_SUCCESS("Lưu yêu cầu nhập  hàng thành công"),
    SAVE_FAILED("Lưu yêu cầu nhập hàng thất bại"),
    GET_SUCCESS("Lấy danh sách yêu cầu nhập hàng thành công"),
    GET_SUCCESS_BY_ID("Lấy yêu cầu nhập hàng theo mã Pr thành công"),
    GET_FAILED_BY_ID("Lấy yêu cầu nhập hàng theo mã Pr thất bại"),
    UPDATE_SUCCESS("Cập nhật yêu cầu nhập hàng thành công"),
    UPDATE_FAILED("Cập nhật yêu cầu nhập hàng thất bại"),
    DELETE_SUCCESS("Xóa yêu cầu nhập hàng thành công"),
    DELETE_FAILED("Xóa yêu cầu nhập hàng thất bại"),
    GET_FAILED("Lấy danh sách yêu cầu nhập hàng thất bại");
    private final String message;

    RespMessagePurchaseReuqestIb(String message) {
        this.message = message;
    }

}

