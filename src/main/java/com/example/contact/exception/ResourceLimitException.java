/*
 * Keith Pottratz
  January 2026
 * Resource Limit Exception
 * Enforces resource limits in the system.
 * Prevents denial of service attacks by limiting resources.
 */
package com.example.contact.exception;

/**
 * Exception thrown when a resource limit is exceeded.
 * Used to prevent denial of service attacks by limiting resources.
 */
public class ResourceLimitException extends ContactException {

    /** 
     * Constructs a new ResourceLimitException with the specified detail message.
     * @param message the detail message describing the resource limit violation
     */
    public ResourceLimitException(String message) {
        super(message);
    }

    /** 
     * Constructs a new ResourceLimitException with the specified detail message and cause.
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public ResourceLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
