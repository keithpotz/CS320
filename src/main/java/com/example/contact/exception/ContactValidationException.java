/*
 * Keith Pottratz
 * January 2026
 * Contact Validation Exception
 * Enforces validation of contact data. 
 * Added common parent for specific exception types
 */
package com.example.contact.exception;

/**
 * Exception thrown when contact data fails validation.
 * Used for invalid field values such as null, empty, or out-of-bounds data.
 */
public class ContactValidationException extends ContactException {

    private final String fieldName;

    /** 
     * Constructs a new ContactValidationException with the specified detail message.
     * @param message the detail message describing the validation failure
     * 
     */
    public ContactValidationException(String message) {
        super(message);
        this.fieldName = null;
    }

    /** 
     * Constructs a new ContactValidationException for a specific field with the given message.
     * @param fieldName the name of the field that failed validation
     * @param message the detail message describing the validation failure
     */
    public ContactValidationException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    /**  
     * Constructs a new ContactValidationException with the specified detail message and cause.
     * @param message the detail message
     * @param cause the cause of the exception
     * 
     */
    public ContactValidationException(String message, Throwable cause) {
        super(message, cause);
        this.fieldName = null;
    }

    /**
     * Returns the name of the field that failed validation, if available.
     * @return the field name, or null if not specified
     */
    public String getFieldName() {
        return fieldName;
    }
}
