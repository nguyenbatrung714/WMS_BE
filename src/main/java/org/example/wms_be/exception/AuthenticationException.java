package org.example.wms_be.exception;

public class AuthenticationException extends RuntimeException  {
    public AuthenticationException(String message) {
        super(message);
    }

}
