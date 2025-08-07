package com.ecommerce.sbEcommerce.Exceptions;

public class ResourseNotFoundException extends RuntimeException {

    String resourceName;
    String field;
    String fieldName;
    Long FieldId;

    public ResourseNotFoundException() {

    }

    public ResourseNotFoundException(String resourceName, String field , String fieldName) {
        super(String.format("%s not found with %s: %s", resourceName, field, fieldName));
        this.field = field;
        this.resourceName = resourceName;
        this.fieldName = fieldName;
    }

    public ResourseNotFoundException(String resourceName ,String field , Long fieldId) {
        super(String.format("%s not found with %s: %d", resourceName, field, fieldId));
        FieldId = fieldId;
        this.field = field;
        this.resourceName = resourceName;
    }
}
