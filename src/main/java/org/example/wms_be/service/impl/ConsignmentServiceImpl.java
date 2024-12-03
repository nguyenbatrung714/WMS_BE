package org.example.wms_be.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.wms_be.amazons3.service.S3Service;
import org.example.wms_be.converter.inventory.ConsignmentConverter;
import org.example.wms_be.data.request.ConsignmentInbound;
import org.example.wms_be.data.request.ConsignmentReq;
import org.example.wms_be.data.request.PurchaseDetailsIbReq;
import org.example.wms_be.data.response.ConsignmentResp;
import org.example.wms_be.entity.inbound.PurchaseDetailsIb;
import org.example.wms_be.entity.inventory.Consignment;
import org.example.wms_be.exception.BadSqlGrammarException;
import org.example.wms_be.exception.ResourceNotFoundException;
import org.example.wms_be.mapper.inbound.PurchaseDetailsIbMapper;
import org.example.wms_be.mapper.inventory.ConsignmentMapper;
import org.example.wms_be.mapper.product.ProductMapper;
import org.example.wms_be.mapper.warehouse.ZoneDetailMapper;
import org.example.wms_be.service.ConsignmentService;
import org.example.wms_be.utils.TimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.expression.Arrays;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsignmentServiceImpl implements ConsignmentService {
    private static final Logger logger = LoggerFactory.getLogger(ConsignmentServiceImpl.class);
    private final ConsignmentMapper consignmentMapper;
    private final ProductMapper productMapper;
    private final ZoneDetailMapper zoneDetailMapper;
    private final ConsignmentConverter consignmentConverter;
    private final PurchaseDetailsIbMapper purchaseDetailsIbMapper;
    private final S3Service s3Service;

    @Override
    public List<ConsignmentReq> getAllConsignment() {

        return consignmentMapper.getAllConsignment()
                .stream()
                .map(consignment -> {
                    // Chuyển đối tượng consignment sang đối tượng ConsignmentReq
                    ConsignmentReq req = consignmentConverter.toConsignmentReq(consignment);

                    // Kiểm tra và xử lý ngày sản xuất
                    if (req.getNgaySanXuat() != null) {
                        String ngayXuatDuKienFormatted = TimeConverter.formatNgayXuat(TimeConverter.parseDateOnly(req.getNgaySanXuat()));
                        req.setNgaySanXuat(ngayXuatDuKienFormatted);
                    }
                    // Kiểm tra và xử lý hạn sử dụng
                    if (req.getHanSuDung() != null) {
                        String hanSuDung = TimeConverter.formatNgayXuat(TimeConverter.parseDateOnly(req.getHanSuDung()));
                        req.setHanSuDung(hanSuDung);
                    }

                    // Trả về đối tượng đã được định dạng
                    return req;
                })
                .collect(Collectors.toList());

    }

    @Override
    public ConsignmentReq saveConsignment(ConsignmentReq consignmentReq) {
        if (!productMapper.checkProductExists(consignmentReq.getSysIdSanPham())) {
            throw new ResourceNotFoundException("Consignment", "maSanPham", consignmentReq.getSysIdSanPham().toString());
        } else if (!zoneDetailMapper.checkZoneDetailExists(consignmentReq.getMaChiTietKhuVuc())) {
            throw new ResourceNotFoundException("Consignment", "maChiTietKhuVuc", consignmentReq.getMaChiTietKhuVuc());
        } else if (!purchaseDetailsIbMapper.existById(consignmentReq.getSysIdChiTietNhapHang())) {
            throw new ResourceNotFoundException("Consignment", "maChiTietNhap", consignmentReq.getSysIdChiTietNhapHang().toString());
        }
        // Kiểm tra và định dạng ngày sản xuất nếu có

        // Kiểm tra và định dạng hạn sử dụng nếu có


        Consignment consignment = consignmentConverter.toConsignment(consignmentReq);
        // Kiểm tra và định dạng ngày sản xuất nếu có
        ngaySanXuat(consignment);
        // Kiểm tra và định dạng hạn sử dụng nếu có
        hanSuDung(consignment);
        try {
            if (consignmentMapper.checkConsignmentExists(consignmentReq.getMaLo())) {
                consignmentMapper.updateConsignment(consignment);
                consignment = consignmentMapper.getConsignmentByMaLo(consignment.getMaLo());
            } else {
                consignmentMapper.insertConsignment(consignment);
            }
        } catch (Exception e) {
            throw new BadSqlGrammarException("Save consignment failed");
        }
        // Trả về kết quả đã được chuyển đổi
        consignment.setNgaySanXuat(formatDateForClient(consignment.getNgaySanXuat()));
        consignment.setHanSuDung(formatDateForClient(consignment.getHanSuDung()));

        return consignmentConverter.toConsignmentReq(consignment);
    }

    @Override
    public Void deleteConsignment(String maLo) {
        if (!consignmentMapper.checkConsignmentExists(maLo)) {
            throw new ResourceNotFoundException("Consignment", "maLo", maLo);
        }
        try {
            consignmentMapper.deleteConsignment(maLo);
            return null;
        } catch (Exception e) {
            logger.error("Error when deleting Consignment: {}", e.getMessage());
            throw new BadSqlGrammarException("Error when deleting Consignment");
        }
    }

    @Override
    public ConsignmentReq getAllConsignmentById(String maLo) {
        if (!consignmentMapper.checkConsignmentExists(maLo)) {
            throw new ResourceNotFoundException("Consignment", "maLo", maLo);
        }
        try {
            Consignment consignment = consignmentMapper.getConsignmentByMaLo(maLo);
            return consignmentConverter.toConsignmentReq(consignment);
        } catch (Exception e) {
            throw new BadSqlGrammarException("Get Consignment failed");
        }

    }

    @Override
    public ConsignmentResp getInfoLoHang(String maLo) {
        try {
            ConsignmentResp consignment = consignmentMapper.getInfoLoHang(maLo);
            consignment.setHinhAnh(s3Service.generatePresignedUrl(consignment.getHinhAnh()));
            return consignment;
        } catch (Exception e) {
            throw new BadSqlGrammarException("Get info lo hang failed" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<ConsignmentReq> saveConsignmentFromInbound(ConsignmentInbound consignmentInbound) {
        try {
            List<Consignment> consignments = new ArrayList<>();

            PurchaseDetailsIb purchaseDetailsIb = purchaseDetailsIbMapper.getPurchaseDetailsById(consignmentInbound.getSysIdChiTietNhapHang());
            if (purchaseDetailsIb == null) {
                throw new ResourceNotFoundException("PurchaseDetailsIb", "sysIdChiTietNhapHang", consignmentInbound.getSysIdChiTietNhapHang().toString());
            }

            double soLuongSanPham = purchaseDetailsIb.getSoLuong();

            while (soLuongSanPham > 0) {
                double soLuongMoiLo = Math.min(soLuongSanPham, consignmentInbound.getSoLuong());

                Consignment consignment = convert(consignmentInbound);

                while (soLuongSanPham < consignmentInbound.getSoLuong()) {
                    if (soLuongSanPham <= 20) {
                        consignment.setDungTich(0.05);
                        break;
                    } else if (soLuongSanPham <= 40) {
                        consignment.setDungTich(0.1);
                        break;
                    } else {
                        consignment.setDungTich(0.2);
                        break;
                    }
                }

                consignment.setSoLuong(soLuongMoiLo);
                consignment.setSysIdChiTietNhapHang(purchaseDetailsIb.getSysIdChiTietNhapHang());
                consignment.setSysIdSanPham(purchaseDetailsIb.getSysIdSanPham());
                consignmentMapper.insertConsignment(consignment);

                soLuongSanPham -= soLuongMoiLo;

                consignments.add(consignment);
            }

            return consignments.stream().map(consignmentConverter::toConsignmentReq).toList();
        } catch (Exception e) {
            throw new BadSqlGrammarException("Save consignment failed: " + e.getMessage());
        }
    }


    private Consignment convert(ConsignmentInbound consignmentInbound) {
        Consignment consignment = new Consignment();
        consignment.setMaLo(consignmentInbound.getMaLo());
        consignment.setSysIdSanPham(consignmentInbound.getSysIdSanPham());
        consignment.setSoLuong(consignmentInbound.getSoLuong());
        consignment.setNgaySanXuat(consignmentInbound.getNgaySanXuat());
        consignment.setHanSuDung(consignmentInbound.getHanSuDung());
        consignment.setDungTich(consignmentInbound.getDungTich());
        consignment.setMaChiTietKhuVuc(consignmentInbound.getMaChiTietKhuVuc());
        return consignment;
    }


    private void ngaySanXuat(Consignment consignment) {
        String ngaySanXuat = consignment.getNgaySanXuat();

        if (ngaySanXuat != null && !ngaySanXuat.trim().isEmpty()) {
            try {
                DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate.parse(ngaySanXuat, displayFormatter); // Thử parse theo định dạng dd/MM/yyyy
                String dbFormatDate = TimeConverter.toDbFormat(TimeConverter.parseDateFromDisplayFormat(ngaySanXuat));
                consignment.setNgaySanXuat(dbFormatDate);
                logger.info("Ngày sản xuất sau khi chuyển đổi: {}", consignment.getNgaySanXuat());
            } catch (DateTimeParseException e) {
                logger.error("Không đúng format ngày: {}", ngaySanXuat, e);
                throw new IllegalArgumentException("Ngày sản xuất không đúng định dạng dd/MM/yyyy: " + ngaySanXuat);

            }
        }
    }


    private void hanSuDung(Consignment consignment) {
        String hanSuDung = consignment.getHanSuDung();
        if (hanSuDung != null && !hanSuDung.isEmpty()) {
            try {
                DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate.parse(hanSuDung, displayFormatter); // Kiểm tra định dạng trước khi chuyển
                String dbFormatDate = TimeConverter.toDbFormat(TimeConverter.parseDateFromDisplayFormat(hanSuDung));
                consignment.setHanSuDung(dbFormatDate);
                logger.info("HanSuDung: {} -> {}", hanSuDung, dbFormatDate);
            } catch (DateTimeParseException e) {
                logger.error("Không đúng định dạng hạn sử dụng: {}", hanSuDung, e);
                throw new IllegalArgumentException("Hạn sử dụng không đúng định dạng dd/MM/yyyy: " + hanSuDung);
            }
        }
    }

    public String formatDateForClient(String dbDate) {
        if (dbDate != null && !dbDate.trim().isEmpty()) {
            try {
                // Đảm bảo rằng ngày có format yyyy-MM-dd
                LocalDate date = LocalDate.parse(dbDate); // Định dạng yyyy-MM-dd
                return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Chuyển sang dd/MM/yyyy
            } catch (DateTimeParseException e) {
                logger.error("Lỗi khi chuyển đổi ngày cho client: {}", dbDate, e);
            }
        }
        return null; // Trả về null nếu không hợp lệ
    }

}
