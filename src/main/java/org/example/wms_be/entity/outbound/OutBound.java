package org.example.wms_be.entity.outbound;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class OutBound {
    private Integer sysIdOutbound;
    private String maOB;
    private String ngayXuat;
    private String trangThai;
    private Integer nguoiPhuTrach;
    List<PurchaseRequestDetailsOb> chiTietXuatHang;
}
