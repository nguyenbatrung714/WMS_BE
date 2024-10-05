package org.example.wms_be.data.mgt;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ApiResponse<T> {
    private String path;
    private int status;
    private String message;
    private Timestamp timestamp;
    private T data;

    public ApiResponse(String path, int status, String message, T data) {
        this.path = path;
        this.status = status;
        this.message = message;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.data = data;
    }
}
