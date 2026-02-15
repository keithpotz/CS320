/*
 * Keith Pottratz
 * CS320
 * Contact Validator
 * January 2026
 */
package com.example.contact.validation;

import com.example.contact.Contact;
import com.example.contact.exception.ContactValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * Validates contact data for security and data integrity.
 * Checks for malicious content, XSS patterns, and invalid characters.
 */
public class ContactValidator {

    private static final Logger logger = LoggerFactory.getLogger(ContactValidator.class);
    private static final Logger auditLogger = LoggerFactory.getLogger("AUDIT");

    // Patterns for detecting potentially malicious content
    private static final Pattern XSS_PATTERN = Pattern.compile(
            ".*(<script|javascript:|onerror=|onclick=|onload=|onmouseover=|<iframe|<object|<embed).*",
            Pattern.CASE_INSENSITIVE
    );

    // SQL injection patterns - excludes single apostrophe to allow names like O'Brien
    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
            ".*(;\\s*--|--\\s*$|'\\s*(or|and)\\s+'|\"\\s*(or|and)\\s*\"?\\d|union\\s+select|drop\\s+table).*",
            Pattern.CASE_INSENSITIVE
    );

    // Pattern for valid name characters (letters, spaces, hyphens, apostrophes)
    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("^[a-zA-Z\\s\\-']+$");

    // Pattern for control characters (except common whitespace)
    private static final Pattern CONTROL_CHAR_PATTERN = Pattern.compile(".*[\\x00-\\x08\\x0B\\x0C\\x0E-\\x1F\\x7F].*");

    /**
     * Validates a contact for security issues.
     * @param contact the contact to validate
     * @throws ContactValidationException if validation fails
     */
    public void validate(Contact contact) {
        if (contact == null) {
            throw new ContactValidationException("Contact cannot be null");
        }

        validateContactId(contact.getContactId());
        validateName(contact.getFirstName(), "firstName");
        validateName(contact.getLastName(), "lastName");
        validatePhone(contact.getPhone());
        validateAddress(contact.getAddress());

        logger.debug("Contact validation passed for ID: {}", contact.getContactId());
    }

    /**
     * Validates a contact ID for security issues.
     * @param contactId the contact ID to validate
     * @throws ContactValidationException if validation fails
     */
    public void validateContactId(String contactId) {
        if (contactId == null || contactId.isBlank()) {
            throw new ContactValidationException("contactId", "Contact ID cannot be null or blank");
        }

        checkForMaliciousContent(contactId, "contactId");
        checkForControlCharacters(contactId, "contactId");
    }

    /**
     * Validates a name field (first name or last name).
     * @param name the name to validate
     * @param fieldName the field name for error messages
     * @throws ContactValidationException if validation fails
     */
    public void validateName(String name, String fieldName) {
        if (name == null || name.isBlank()) {
            throw new ContactValidationException(fieldName, fieldName + " cannot be null or blank");
        }

        checkForMaliciousContent(name, fieldName);
        checkForControlCharacters(name, fieldName);

        // Check for valid name characters
        if (!VALID_NAME_PATTERN.matcher(name).matches()) {
            auditLogger.warn("Security: Invalid characters in {} - value contained non-alphabetic characters", fieldName);
            throw new ContactValidationException(fieldName, fieldName + " contains invalid characters (only letters, spaces, hyphens, and apostrophes allowed)");
        }
    }

    /**
     * Validates a phone number.
     * @param phone the phone number to validate
     * @throws ContactValidationException if validation fails
     */
    public void validatePhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new ContactValidationException("phone", "Phone cannot be null or blank");
        }

        // Phone should only contain digits (already validated by Contact class)
        if (!phone.matches("\\d+")) {
            auditLogger.warn("Security: Invalid phone number format attempted");
            throw new ContactValidationException("phone", "Phone must contain only digits");
        }
    }

    /**
     * Validates an address.
     * @param address the address to validate
     * @throws ContactValidationException if validation fails
     */
    public void validateAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new ContactValidationException("address", "Address cannot be null or blank");
        }

        checkForMaliciousContent(address, "address");
        checkForControlCharacters(address, "address");
    }

    /**
     * Checks a string for potentially malicious content (XSS, SQL injection).
     * @param value the value to check
     * @param fieldName the field name for error messages
     * @throws ContactValidationException if malicious content is detected
     */
    private void checkForMaliciousContent(String value, String fieldName) {
        if (XSS_PATTERN.matcher(value).matches()) {
            auditLogger.warn("Security: XSS pattern detected in {} - input rejected", fieldName);
            throw new ContactValidationException(fieldName, fieldName + " contains potentially unsafe content");
        }

        if (SQL_INJECTION_PATTERN.matcher(value).matches()) {
            auditLogger.warn("Security: SQL injection pattern detected in {} - input rejected", fieldName);
            throw new ContactValidationException(fieldName, fieldName + " contains potentially unsafe content");
        }
    }

    /**
     * Checks a string for control characters.
     * @param value the value to check
     * @param fieldName the field name for error messages
     * @throws ContactValidationException if control characters are found
     */
    private void checkForControlCharacters(String value, String fieldName) {
        if (CONTROL_CHAR_PATTERN.matcher(value).matches()) {
            auditLogger.warn("Security: Control characters detected in {} - input rejected", fieldName);
            throw new ContactValidationException(fieldName, fieldName + " contains invalid control characters");
        }
    }
}
