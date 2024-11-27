package org.example.wms_be.data.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OutboundResp {
    private Integer sysIdOutbound;
    private String maOB;
    private String ngayXuat;
    private String trangThai;
    private String maPO;
    private String nguoiPhuTrach;
    List<PurchaseRequestDetailsObResp> chiTietXuatHang;
}
