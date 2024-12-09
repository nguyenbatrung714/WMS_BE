package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.converter.WasteProductsConverter;
import org.example.wms_be.data.dto.WasteProductsDto;
import org.example.wms_be.entity.WasteProducts;
import org.example.wms_be.entity.inventory.Inventory;
import org.example.wms_be.entity.product.Product;
import org.example.wms_be.mapper.WasteProductsMapper;
import org.example.wms_be.mapper.inventory.InventoryMapper;
import org.example.wms_be.mapper.product.ProductMapper;
import org.example.wms_be.service.WasteProductsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WasteProductsServiceImpl implements WasteProductsService {
    private final InventoryMapper inventoryMapper;
    private final ProductMapper productMapper;
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
        wasteProductsMapper.insertWasteProducts(phePham);
        Product product = productMapper.getProductByTenSanPham(inventory.getTenSanPham());
        if (product.equals("")) {
            throw new RuntimeException("Không tìm thấy sản phẩm với tên: " + inventory.getTenSanPham());
        }
        double updatedQuantity =  (product.getSoLuongHienCo() - inventory.getSoLuong());
        if (updatedQuantity < 0) {
            throw new RuntimeException("Số lượng tồn kho vượt quá số lượng hiện có của sản phẩm.");
        }
        product.setSoLuongHienCo(updatedQuantity);
        productMapper.updateProduct(product);
        // Bước 3: Xóa lô hàng khỏi `ton_kho`
        inventoryMapper.deleteLohangById(sysIdTonKho);
        // Bước 4: Trả về DTO chứa thông tin phế phẩm
        return wasteProductsConverter.toWasteProductsDto(phePham);
    }
}
