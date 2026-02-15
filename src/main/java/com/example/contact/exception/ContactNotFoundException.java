/*
 * Keith Pottratz
   January 2026 - Added common parent for specific exception types
 */
package com.example.contact.exception;

/**
 * Exception thrown when a contact cannot be found by its ID.
 * Used by service and repository operations that require an existing contact.
 */
public class ContactNotFoundException extends ContactException {

    /**
     * Constructs a new ContactNotFoundException with the specified contact ID.
     * @param contactId the ID of the contact that was not found
     */
    public ContactNotFoundException(String contactId) {
        super("Contact not found with ID: " + contactId);
    }

    /** 
     * Constructs a new ContactNotFoundException with the specified detail message and cause.
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public ContactNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
