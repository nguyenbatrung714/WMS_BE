package org.example.wms_be.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseRequestDto {
    private Integer sysIdYeuCauMuaHang;
    private String  maPR;
    private String  ngayYeuCau;
    private Integer nguoiYeuCau;
    private String trangThai;
    List<PurchaseRequestDetailsDto> chiTietDonHang;
}
