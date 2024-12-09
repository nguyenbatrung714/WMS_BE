package org.example.wms_be.service.impl;
import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;
import org.example.wms_be.data.response.inbound.PurchaseRequestIbResp;
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

    @Override
    public List<PurchaseRequestDetailsObResp> getMostObProducts() {
        try {
            return purchaseDetailsObMapper.getMostObProducts();
        } catch (Exception e) {
            // Log lỗi
            System.err.println("Error fetching most imported products: " + e.getMessage());
            // Quăng lại lỗi hoặc trả về giá trị mặc định
            throw new RuntimeException("Unable to fetch most imported products.", e);
        }
    }

    @Override
    public List<PurchaseRequestDetailsObResp> getLeastObProducts() {
        try {
            return purchaseDetailsObMapper.getLeastObProducts();
        } catch (Exception e) {
            // Log lỗi
            System.err.println("Error fetching least imported products: " + e.getMessage());
            // Quăng lại lỗi hoặc trả về giá trị mặc định
            throw new RuntimeException("Unable to fetch least imported products.", e);
        }
    }
}
