/*
 * Keith Pottratz
 * January 2026
 * Duplicate Contact Exception
 * Enforces uniqueness of contact IDs in the system.
 * 
 */
package com.example.contact.exception;

/**
 * Exception thrown when attempting to add a contact with an ID that already exists.
 * Enforces uniqueness of contact IDs in the system.
 */
public class DuplicateContactException extends ContactException {

    /**
     * Constructs a new DuplicateContactException with the specified contact ID.
     * @param contactId the ID of the duplicate contact
     */
    public DuplicateContactException(String contactId) {
        super("Contact already exists with ID: " + contactId);
    }

    /** 
     * Constructs a new DuplicateContactException with the specified detail message and cause.
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public DuplicateContactException(String message, Throwable cause) {
        super(message, cause);
    }
}
