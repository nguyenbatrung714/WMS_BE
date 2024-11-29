package org.example.wms_be.data.mgt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RespMessageOutbound {
    GET_SUCCESS("lay danh sach xuat hang thanh cong"),
    GET_FAILED("lay danh sach xuat hang that bai"),
    SAVE_SUCCESS("luu xuat hang thanh cong"),
    SAVE_FAILED("luu xuat hang that bai"),
    CHECK_INVENTORY_SUCCESS("kiem tra ton kho thanh cong"),
    CHECK_INVENTORY_FAILED("kiem tra ton kho that bai"),
    GET_FIRSTOUT_FAILED("Lấy thông tin lô hàng đầu tiên thất bại"),
    GET_FIRSTOUT_SUCCESS("Lấy thông tin lô hàng đầu tiên thành công");
    private final String message;
}
