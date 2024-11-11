package org.example.wms_be.entity.inbound;

import lombok.Getter;
import lombok.Setter;
import org.example.wms_be.enums.TypeOrder;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class PurchaseOrderIb {
    private Integer sysIdPO;
    private String maPO;
    private String maPR;
    private Timestamp ngayTao;
    private Integer sysIdUser;
    private String nguoiTao;
    List<PurchaseDetailsIb> chiTietNhapHang;
}
