package org.example.wms_be.utils;

import org.example.wms_be.data.response.PurchaseRequestDetailsObResp;

import java.util.List;

public class  TplEmailPrOb {
    public static String emailTitle(String maYeuCau, String trangThai){
        return String.format(
                "<h2>Yêu cầu xuất hàng mã: %s [%s].</h2>",
                maYeuCau, trangThai);
    }
    public static String emailTitleConfirm(String maYeuCau, String trangThai){
        return String.format(
                "<h2>Xác nhận yêu cầu xuất hàng mã: %s [%s].</h2>",
                maYeuCau, trangThai);
    }
    public static String emailTitleReject(String maYeuCau, String trangThai){
        return String.format(
                "<h2> Từ chối yêu cầu xuất hàng mã: %s [%s].</h2>",
                maYeuCau, trangThai);
    }
    public static String requestInfor(String nguoiTao, String daiDienPo, String chucVu){
        return String.format(
                "<div style='margin-bottom: 15px; color: black;'>" +
                        "<p><strong> Người tạo yêu cầu :</strong> %s</p>" +
                        "<h4><strong>Nội dung:</strong></h4>" +
                        "<p><strong> Yêu cầu đại điện từ phòng Quản Lý:</strong> %s</p>" +
                        "<p><strong>Chức Vụ:</strong> %s</p>" +
                        "</div>",
                nguoiTao, daiDienPo, chucVu);
    }
    public static String bangYeuCauXuatHang(List<PurchaseRequestDetailsObResp> chiTietXuatHangs){
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1' cellspacing='0' cellpadding='5' style='color: black;'>");
        sb.append("<caption style='font-weight: bold; font-size: 1.5em; margin-bottom: 10px;'>Thông Tin Chi Tiết yêu cầu</caption>")
                .append("<tr>")
                .append("<th>Tên Sản Phẩm</th>")
                .append("<th>Số Lượng(Kg)</th>")
                .append("<th>Giá(VND)</th>")
                .append("<th>Tổng Chi Phí(VND)</th>")
                .append("<th>Ngày Xuất</th>")
                .append("</tr>");

        for (PurchaseRequestDetailsObResp chiTietXuatHang : chiTietXuatHangs) {
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
                        "<p><strong>Yêu cầu xuất hàng từ requestion:</strong></p>" +
                        "%s" + //  thông tin yêu cầu mua hàng
                        "<p>Vui lòng kiểm tra và xác nhận yêu cầu xuất hàng.</p>" +
                        "%s" + //  chi tiết đơn hàng
                        "</body></html>",
                title, requestInfo, detailsObTable);
    }
    public static String emailBodyConfirm(String title,String detailsObTable){
        return String.format(
                "<html><body style='color: black; font-family: Arial, sans-serif;'>" +
                        "%s" + //  tiêu đề
                        "<p style='color: red; font-weight: bold;'>YÊU CẦU ĐÃ ĐƯỢC PHÊ DUYỆT.</p>" +
                        "%s" + //  chi tiết đơn hàng
                        "</body></html>",
                title, detailsObTable);
    }
    public static String emailBodyReject(String title, String reason, String detailsObTable){
        return String.format(
                "<html><body style='color: black; font-family: Arial, sans-serif;'>" +
                        "%s" + //  tiêu đề
                        "%s" + //  chi tiết đơn hàng
                        "<p style='color: red; font-weight: bold;'>YÊU CẦU ĐÃ BỊ TỪ CHỐI.</p>" +
                        "<p style='color: red; font-weight: bold;'>LÝ DO: %s</p>" +
                        "</body></html>",
                title, reason, detailsObTable);
    }
}
