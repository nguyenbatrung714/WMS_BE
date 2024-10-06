package org.example.wms_be.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseRequestDto {
    private Integer sysIdYeuCauMuaHang;
    private String  maPR;
    private String  ngayYeuCau;
    private Integer nguoiYeuCau;
    private Boolean trangThai;
    List<PurchaseDetailsDto> chiTietDonHang;
}
