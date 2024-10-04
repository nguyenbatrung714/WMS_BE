package org.example.wms_be.exception;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ErrorException {

    private String errorMessage;
    private Timestamp timestamp;

    public ErrorException(String errorMessage) {
        this.errorMessage = errorMessage;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

}
