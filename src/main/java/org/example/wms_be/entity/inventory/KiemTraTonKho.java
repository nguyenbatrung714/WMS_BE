package org.example.wms_be.entity.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class KiemTraTonKho {
    private Integer sysIdSanPham;
    private List<LoSuDung> loSuDung;
}
