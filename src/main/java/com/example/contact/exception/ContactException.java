/*
 * Keith Pottratz
 * Updated: January 2026 - Added common parent for specific exception types
 * 
 */
package com.example.contact.exception;

/**
 * Base exception class for all contact-related exceptions.
 * Provides a common parent for specific exception types.
 */
public class ContactException extends RuntimeException {
    /**
     * Constructs a new contactException with the specified detail message.
     * @param message the detail message
     */
    public ContactException(String message) {
        super(message);
    }
    /** 
     * Constructs a new ContactException with the specified detail message and cause.
     * @param message the detail message
     * @param cause the cause of the exception
     */

    public ContactException(String message, Throwable cause) {
        super(message, cause);
    }
}
