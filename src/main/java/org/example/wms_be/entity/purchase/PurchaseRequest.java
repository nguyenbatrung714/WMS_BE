package org.example.wms_be.entity.purchase;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseRequest {
    private Integer sysIdYeuCauMuaHang;
    private String maPR;
    private String ngayYeuCau;
    private Integer nguoiYeuCau;
    private String trangThai;
    List<PurchaseRequestDetails> chiTietDonHang;
}
