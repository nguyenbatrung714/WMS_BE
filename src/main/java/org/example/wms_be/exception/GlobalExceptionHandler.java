package org.example.wms_be.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    //handle exception for system errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorException> handleSystemError(Exception ex) {
        ErrorException error = new ErrorException(ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // File Exception
    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorException> handleFileStorageException(FileStorageException ex) {
        ErrorException errorException = new ErrorException(ex.getMessage());
        return new ResponseEntity<>(errorException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
