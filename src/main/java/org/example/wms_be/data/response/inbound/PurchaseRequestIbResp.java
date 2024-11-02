package org.example.wms_be.data.response.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseRequestIbResp {
    private Integer sysIdYeuCauNhapHang;
    private String maPR;
    private String ngayYeuCau;
    private String nguoiYeuCau;
    private String trangThai;
    private String loaiYeuCau;
    List<PurchaseRequestDetailsIbResp> chiTietNhapHang;
}
