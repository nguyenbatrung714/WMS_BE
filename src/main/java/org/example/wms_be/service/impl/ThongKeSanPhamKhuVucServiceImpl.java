package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.data.response.thongke.ThongKeSanPhamKhuVucResp;
import org.example.wms_be.mapper.product.ProductMapper;
import org.example.wms_be.service.ThongKeSanPhamKhuVucService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThongKeSanPhamKhuVucServiceImpl implements ThongKeSanPhamKhuVucService {
    private final ProductMapper productMapper;
    @Override
    public List<ThongKeSanPhamKhuVucResp> getThongKeSanPhamKhuVuc() {
       try {
           return productMapper.getThongKeSanPhamKhuVuc();
       }catch (Exception e){
           throw new IllegalArgumentException("Error getThongKeSanPhamKhuVuc");
       }
    }
}
