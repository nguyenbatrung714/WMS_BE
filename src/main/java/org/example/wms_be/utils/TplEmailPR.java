package org.example.wms_be.utils;
import org.example.wms_be.entity.inbound.PurchaseDetailsIb;

import java.util.List;

public class TplEmailPR {
    // Tạo tiêu đề cho email
    public static String buildEmailTitle(String purchaseRequestId) {
        return String.format(
                "<h2>Số yêu cầu mua hàng mã: %s đang chờ xử lý.</h2>",
                purchaseRequestId);
    }
    public static String buildEmailTitleUpdate(String purchaseRequestId) {
        return String.format(
                "<h2>Số yêu cầu mua hàng mã: %s vừa được điều chỉnh (Đang chờ duyệt).</h2>",
                purchaseRequestId);
    }

    // Tạo  thông tin yêu cầu mua hàng
    public static String buildRequestInfo(String userRequesting, String fullName, String role) {
        return String.format(
                "<div style='margin-bottom: 15px; color: black;'>" +
                        "<p><strong> người tạo yêu cầu :</strong> %s</p>" +
                        "<h4><strong>Nội dung:</strong></h4>" +
                        "<p><strong> Yêu cầu đại điện từ phòng Purchase Oder là:</strong> %s</p>" +
                        "<p><strong>Chức Vụ:</strong> %s</p>" +
                        "</div>",
                userRequesting, fullName, role);
    }

    // Tạo bảng chi tiết đơn hàng
    public static String buildOrderDetailsTable(List<PurchaseDetailsIb> details) {
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
        for (PurchaseDetailsIb detail : details) {
            sb.append("<tr>")
                    .append("<td>").append(detail.getSysIdSanPham()).append("</td>")
                    .append("<td>").append(detail.getSoLuong()).append("</td>")
                    .append("<td>").append(detail.getGia()).append("</td>")
                    .append("<td>").append(detail.getTongChiPhi()).append("</td>")
                    .append("<td>").append(detail.getNgayNhapDuKien()).append("</td>")
                    .append("</tr>");
        }
        sb.append("</table>");
        return sb.toString();
    }
    // Tạo body email
    public static String buildEmailBody(String title, String requestInfo, String orderDetailsTable) {
        return String.format(
                "<html><body style='color: black; font-family: Arial, sans-serif;'>" +
                        "%s" + //  tiêu đề
                        "<p><strong>Yêu cầu mua hàng từ phòng purchase request:</strong></p>" +
                        "%s" + //  thông tin yêu cầu mua hàng
                        "<p>Vui lòng kiểm tra và xác nhận yêu cầu mua hàng.</p>" +
                        "%s" + //  chi tiết đơn hàng
                        "</body></html>",
                title, requestInfo, orderDetailsTable);
    }
    // Tạo body email cho trường hợp cập nhật
    public static String buildEmailBodyForUpdate(String title, String updatedInfo, String orderDetailsTable) {
        return String.format(
                "<html><body style='color: black; font-family: Arial, sans-serif;'>" +
                        "%s" + // tiêu đề
                        "<p><strong>Thông báo cập nhật:</strong></p>" +
                        "%s" + // thông tin yêu cầu mua hàng đã cập nhật
                        "<p>Vui lòng kiểm tra lại hệ thống để biết thêm chi tiết.</p>" +
                        "%s" + // chi tiết đơn hàng đã cập nhật
                        "</body></html>",
                title, updatedInfo, orderDetailsTable);
    }
    // Tạo thông tin cập nhật đơn hàng
    public static String buildUpdateInfo(String userRequesting) {
        return String.format(
                "<div style='margin-bottom: 15px; color: black;'>" +
                        "<p><strong>Người thực hiện cập nhật:</strong> %s</p>" +
                        "</div>",
                userRequesting);
    }
}
