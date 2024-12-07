package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.constant.InventoryConst;
import org.example.wms_be.data.response.InventoryResp;
import org.example.wms_be.entity.inventory.KiemTraTonKho;
import org.example.wms_be.entity.inventory.LoSuDung;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.mapper.inventory.InventoryMapper;
import org.example.wms_be.mapper.product.ProductMapper;
import org.example.wms_be.mapper.purchase.PurchaseDetailsObMapper;
import org.example.wms_be.service.InventoryService;
import org.example.wms_be.utils.TimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryMapper inventoryMapper;
    private final   PurchaseDetailsObMapper purchaseDetailsObMapper;
    private final ProductMapper productMapper;
    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

    @Override
    public List<InventoryResp> layDanhSachLoHangCanXuat(Integer sysIdSanPham) {
        try {
            List<InventoryResp> inventoryList = inventoryMapper.layDanhSachLoHangCanXuat(sysIdSanPham);

            // Format ngayCapNhat for each inventory response
            for (InventoryResp resp : inventoryList) {
                if (resp.getNgayCapNhat() != null) {
                    resp.setNgayCapNhat(TimeConverter.formatTimestamp(Timestamp.valueOf(resp.getNgayCapNhat())));
                }
            }

            return inventoryList;
        } catch (Exception e) {
            logger.error(InventoryConst.GET_FIRSTOUT_FAILED);
            return Collections.emptyList();
        }
    }

    @Override
    public KiemTraTonKho kiemTraTonKho(Integer sysIdSanPham, Integer sysIdChiTietXuatHang) {

        // Lấy số lượng cần xuất
        double soLuongCanXuat = purchaseDetailsObMapper.getSoLuongCanXuat(sysIdChiTietXuatHang, sysIdSanPham);
        if (soLuongCanXuat <= 0) {
            throw new IllegalArgumentException(InventoryConst.NOT_FOUND_INVENTORY + sysIdChiTietXuatHang);
        }

        // Lấy danh sách lô hàng
        List<InventoryResp> inventoryResps = inventoryMapper.layDanhSachLoHangCanXuat(sysIdSanPham);

        // Tính tổng số lượng tồn kho
        double tongSoLuongTonKho = inventoryResps.stream()
                .mapToDouble(InventoryResp::getSoLuong)
                .sum();

        // Kiểm tra tổng số lượng tồn kho có đủ không
        if (tongSoLuongTonKho < soLuongCanXuat) {
            throw new IllegalArgumentException(InventoryConst.NOT_ENOUGH_QUANTITY);
        }

        //  trừ số lượng từ từng lô
        double soLuongConThieu = soLuongCanXuat;
        List<LoSuDung> loSuDung = new ArrayList<>();

        for (InventoryResp inventoryResp : inventoryResps) {
            if (soLuongConThieu <= 0) break;

            double soLuongTru = Math.min(soLuongConThieu, inventoryResp.getSoLuong());
            soLuongConThieu -= soLuongTru;


            // Ghi lại các lô được sử dụng
            loSuDung.add(new LoSuDung(inventoryResp.getSysIdTonKho(), soLuongTru,inventoryResp.getMaLo()));

            // Cập nhật tồn kho từng lô
            inventoryMapper.updateInventory(inventoryResp.getSysIdTonKho(), inventoryResp.getSoLuong() - soLuongTru);
        }

        // Cập nhật tổng số lượng tồn kho của sản phẩm
        inventoryMapper.updateSoLuongHienCo(sysIdSanPham);

        return new KiemTraTonKho(sysIdSanPham, loSuDung);
    }


    @Override
    public List<InventoryResp> getInventoryList() {
        try {
            return inventoryMapper.getAllInventory();
        } catch (Exception e) {
            throw new BadSqlGrammarException("Failed to get inventory list" + e.getMessage());
        }
    }

    @Override
    public Double thongKeTongSoLuongTonKho() {
        try {
            Double tongSoLuong = inventoryMapper.thongKeTongSoLuongTonKho();
            return tongSoLuong != null ? tongSoLuong : 0.0;
        } catch (Exception e) {
            logger.error("Lỗi khi thống kê tổng số lượng tồn kho", e);
            return 0.0;
        }
    }

}
