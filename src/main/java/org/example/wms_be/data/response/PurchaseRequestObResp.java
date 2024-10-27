package org.example.wms_be.data.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseRequestObResp {
    private Integer sysIdYeuCauXuatHang;
    private String  maPR;
    private String  ngayYeuCau;
    private String  nguoiYeuCau;
    private String  trangThai;
    private String  loaiYeuCau;
    List<PurchaseRequestDetailsResp> chiTietXuatHang;
}
