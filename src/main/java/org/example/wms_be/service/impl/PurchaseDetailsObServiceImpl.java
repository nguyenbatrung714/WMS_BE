package org.example.wms_be.service.impl;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;
import org.example.wms_be.mapper.purchase.PurchaseDetailsObMapper;
import org.example.wms_be.service.PurchaseDetailsObService;
import org.example.wms_be.utils.TimeConverter;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseDetailsObServiceImpl implements PurchaseDetailsObService {
    private final PurchaseDetailsObMapper purchaseDetailsObMapper;
    @Override
    public List<PurchaseRequestDetailsObResp> getChiTietDonHangByMaPR(String maPR) {
       List<PurchaseRequestDetailsObResp> chiTietXuatHang = purchaseDetailsObMapper.layDanhSachXuatHangTheoMaPR(maPR);
         chiTietXuatHang.forEach(detail -> {
              if (detail.getNgayXuatDuKien() != null) {
                String ngayXuatDuKienFormatted = TimeConverter.formatNgayXuat(TimeConverter.parseDateOnly(detail.getNgayXuatDuKien()));
                detail.setNgayXuatDuKien(ngayXuatDuKienFormatted);
              }
         });
            return chiTietXuatHang;
    }
}
