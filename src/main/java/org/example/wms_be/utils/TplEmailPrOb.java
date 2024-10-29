package org.example.wms_be.utils;

import org.example.wms_be.data.response.PurchaseRequestDetailsResp;

import java.util.List;

public class  TplEmailPrOb {
    public static String emailTitle(String maYeuCau){
        return String.format(
                "<h2>Yêu cầu xuất hàng mã: %s đang chờ xử lý.</h2>",
                maYeuCau);
    }
    public static String emailTitleUpdate(String maYeuCau){
        return String.format(
                "<h2>Yêu cầu xuất hàng mã: %s vừa được điều chỉnh.</h2>",
                maYeuCau);
    }
    public static String requestInfor(String nguoiTao, String daiDienPo, String chucVu){
        return String.format(
                "<div style='margin-bottom: 15px; color: black;'>" +
                        "<p><strong> người tạo yêu cầu :</strong> %s</p>" +
                        "<h4><strong>Nội dung:</strong></h4>" +
                        "<p><strong> Yêu cầu đại điện từ phòng Purchase Oder là:</strong> %s</p>" +
                        "<p><strong>Chức Vụ:</strong> %s</p>" +
                        "</div>",
                nguoiTao, daiDienPo, chucVu);
    }
    public static String bangYeuCauXuatHang(List<PurchaseRequestDetailsResp> chiTietXuatHangs){
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1' cellspacing='0' cellpadding='5' style='color: black;'>");
        sb.append("<caption style='font-weight: bold; font-size: 1.5em; margin-bottom: 10px;'>Thông Tin Chi Tiết yêu cầu</caption>")
                .append("<tr>")
                .append("<th>Tên Sản Phẩm</th>")
                .append("<th>Số Lượng(Kg)</th>")
                .append("<th>Giá(VND)</th>")
                .append("<th>Tổng Chi Phí(VND)</th>")
                .append("<th>Ngày Nhập</th>")
                .append("</tr>");

        for (PurchaseRequestDetailsResp chiTietXuatHang : chiTietXuatHangs) {
            sb.append("<tr>")
                    .append("<td>").append(chiTietXuatHang.getTenSanPham()).append("</td>")
                    .append("<td>").append(chiTietXuatHang.getSoLuong()).append("</td>")
                    .append("<td>").append(chiTietXuatHang.getGia()).append("</td>")
                    .append("<td>").append(chiTietXuatHang.getTongChiPhi()).append("</td>")
                    .append("<td>").append(chiTietXuatHang.getNgayXuatDuKien()).append("</td>")
                    .append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();

    }
    public static String emailBody(String title, String requestInfo, String detailsObTable){
        return String.format(
                "<html><body style='color: black; font-family: Arial, sans-serif;'>" +
                        "%s" + //  tiêu đề
                        "<p><strong>Yêu cầu mua hàng từ phòng purchase request:</strong></p>" +
                        "%s" + //  thông tin yêu cầu mua hàng
                        "<p>Vui lòng kiểm tra và xác nhận yêu cầu mua hàng.</p>" +
                        "%s" + //  chi tiết đơn hàng
                        "</body></html>",
                title, requestInfo, detailsObTable);
    }
    public static String emailBodyForUpdate(String title, String requestInfo, String detailsObTable){
        return String.format(
                "<html><body style='color: black; font-family: Arial, sans-serif;'>" +
                        "%s" + // tiêu đề
                        "<p><strong>Thông báo cập nhật:</strong></p>" +
                        "%s" + // thông tin yêu cầu mua hàng đã cập nhật
                        "<p>Vui lòng kiểm tra lại hệ thống để biết thêm chi tiết.</p>" +
                        "%s" + // chi tiết đơn hàng đã cập nhật
                        "</body></html>",
                title, requestInfo, detailsObTable);
    }
    public static String updateInFor(String nguoiTao){
        return String.format(
                "<div style='margin-bottom: 15px; color: black;'>" +
                        "<p><strong>Người thực hiện cập nhật:</strong> %s</p>" +
                        "</div>",
                nguoiTao);
    }
}
