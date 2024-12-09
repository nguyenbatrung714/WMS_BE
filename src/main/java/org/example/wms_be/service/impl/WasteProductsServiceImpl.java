package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.example.wms_be.converter.WasteProductsConverter;
import org.example.wms_be.data.dto.WasteProductsDto;
import org.example.wms_be.data.request.InventoryReq;
import org.example.wms_be.data.response.InventoryResp;
import org.example.wms_be.entity.WasteProducts;
import org.example.wms_be.entity.inventory.Inventory;
import org.example.wms_be.mapper.WasteProductsMapper;
import org.example.wms_be.mapper.inventory.InventoryMapper;
import org.example.wms_be.service.WarehouseService;
import org.example.wms_be.service.WasteProductsService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class WasteProductsServiceImpl implements WasteProductsService {
    private final InventoryMapper inventoryMapper;
    private final WasteProductsMapper wasteProductsMapper;
    private final WasteProductsConverter wasteProductsConverter;


    @Override
    public WasteProductsDto insertWaste(Integer sysIdTonKho) {
        Inventory inventory =  inventoryMapper.getLohangById(sysIdTonKho);
        if (inventory == null) {
            throw new RuntimeException("Không tìm thấy tồn kho với ID: " + sysIdTonKho);
        }

        // Bước 2: Chuyển thông tin sang `phe_pham`
        WasteProducts phePham = new WasteProducts();
        phePham.setMaKho(inventory.getMaKho());
        phePham.setMaLo(inventory.getMaLo());
        phePham.setTenSanPham(inventory.getTenSanPham()); // hoặc bạn có thể lấy tên sản phẩm từ bảng khác nếu có
        phePham.setSoLuong(inventory.getSoLuong()); // Chuyển từ String sang Integer
        phePham.setNgayHetHan(inventory.getNgayCapNhat()); // Sử dụng ngayCapNhat làm ngày hết hạn (nếu thích hợp)
        phePham.setNgayPhePham(new Date()); // Ngày hiện tại

        wasteProductsMapper.insertWasteProducts(phePham);

        // Bước 3: Xóa lô hàng khỏi `ton_kho`
        inventoryMapper.deleteLohangById(sysIdTonKho);

        // Bước 4: Trả về DTO chứa thông tin phế phẩm
        return wasteProductsConverter.toWasteProductsDto(phePham);
    }
}
