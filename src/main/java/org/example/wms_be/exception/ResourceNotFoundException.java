package org.example.wms_be.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, String value) {
        super(String.format("Resource '%s' not found with '%s' = '%s'", resourceName, fieldName, value));
    }

}
