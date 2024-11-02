package org.example.wms_be.data.response.inbound;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseRequestDetailsIbResp {
    private Integer sysIdChiTietNhapHang;
    private String  maIB;
    private String  maPR;
    private String  maPO;
    private String  maLoHang;
    private String  tenKhachHang;
    private String  tenSanPham;
    private Double  soLuong;
    private Double  gia;
    private Double  tongChiPhi;
    private String  ngayNhapDuKien;
    private Integer sysIdKhachHang;
    private Integer sysIdSanPham;
}
