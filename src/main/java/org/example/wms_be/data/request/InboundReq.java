package org.example.wms_be.data.request;

import lombok.Getter;
import lombok.Setter;
import org.example.wms_be.enums.TrangThai;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class InboundReq {
    private Integer sysIdInBound;
    private String maInBound;
    private String maKho;
    private String maPO;
    private Timestamp ngayNhap = new Timestamp(System.currentTimeMillis());
    private TrangThai trangThai = TrangThai.confirm;
    private Integer sysIdUser;
    private String fullName;
    private String tenKho;
    List<PurchaseDetailsIbReq> chiTietNhapHang;
}
