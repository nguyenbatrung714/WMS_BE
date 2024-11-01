package org.example.wms_be.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeConverter {
    // Định dạng hiển thị
    private static final DateTimeFormatter displayFullFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter displayDateOnlyFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Định dạng trong database
    private static final DateTimeFormatter dbFullFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dbDateOnlyFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Chuyển đổi timestamp thành chuỗi hiển thị ngày giờ (dd/MM/yyyy HH:mm:ss)
     */
    public static String formatNgayYeuCau(Timestamp timestamp) {
        if (timestamp == null) return null;
        return timestamp.toLocalDateTime().format(displayFullFormatter);
    }

    /**
     * Chuyển đổi timestamp thành chuỗi hiển thị ngày (dd/MM/yyyy)
     */
    public static String formatNgayXuat(Timestamp timestamp) {
        if (timestamp == null) return null;
        return timestamp.toLocalDateTime().format(displayDateOnlyFormatter);
    }

    /**
     * Chuyển đổi chuỗi ngày giờ từ database (yyyy-MM-dd HH:mm:ss) sang Timestamp
     */
    public static Timestamp parseFullDateTime(String dbDateTimeStr) {
        if (dbDateTimeStr == null || dbDateTimeStr.trim().isEmpty()) return null;
        try {
            LocalDateTime dateTime = LocalDateTime.parse(dbDateTimeStr, dbFullFormatter);
            return Timestamp.valueOf(dateTime);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid datetime format. Expected yyyy-MM-dd HH:mm:ss", e);
        }
    }

    /**
     * Chuyển đổi chuỗi ngày từ database (yyyy-MM-dd) sang Timestamp
     */
    public static Timestamp parseDateOnly(String dbDateStr) {
        if (dbDateStr == null || dbDateStr.trim().isEmpty()) return null;
        try {
            LocalDate date = LocalDate.parse(dbDateStr, dbDateOnlyFormatter);
            return Timestamp.valueOf(date.atStartOfDay());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd", e);
        }
    }

    public static LocalDate parseDateFromDisplayFormat(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        try {
            return LocalDate.parse(dateStr, displayDateOnlyFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected dd/MM/yyyy", e);
        }
    }

    /**
     * Chuyển đổi LocalDateTime sang định dạng database
     */
    public static String toDbFormat(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.format(dbFullFormatter);
    }

    /**
     * Chuyển đổi LocalDate sang định dạng database
     */
    public static String toDbFormat(LocalDate date) {
        if (date == null) return null;
        return date.format(dbDateOnlyFormatter);
    }
}