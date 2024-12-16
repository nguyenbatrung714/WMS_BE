package org.example.wms_be.data.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseRequestDetailsObResp {
    private Integer sysIdChiTietXuatHang;
    private String  maOB;
    private String  maPR;
    private String  maPO;
    private String  tenKhachHang;
    private String  tenSanPham;
    private Double  soLuong;
    private BigDecimal gia;
    private BigDecimal tongChiPhi;
    private String  ngayXuatDuKien;
    private Integer sysIdKhachHang;
    private Integer sysIdSanPham;
    private Boolean isOutboundNull;
}
