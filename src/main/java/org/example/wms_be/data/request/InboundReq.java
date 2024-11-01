package org.example.wms_be.data.request;

import lombok.Getter;
import lombok.Setter;
import org.example.wms_be.enums.TrangThai;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class InboundReq {
    private String maInBound;
    private String maPO;
    private Timestamp ngayNhap = new Timestamp(System.currentTimeMillis());
    private TrangThai trangThai = TrangThai.DANG_XU_LY;
    private Integer sysIdUser;
    List<PurchaseDetailsIbReq> chiTietNhapHang;
}
