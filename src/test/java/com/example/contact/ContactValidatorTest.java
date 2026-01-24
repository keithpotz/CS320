/*
 * Keith Pottratz
 * CS320
 * Contact Validator Test
 * January 2026
 * Tests input validation and security features
 * 
 */
package com.example.contact;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.contact.exception.ContactValidationException;
import com.example.contact.validation.ContactValidator;

/**
 * Unit tests for ContactValidator security features.
 * Tests input sanitization, XSS prevention, and validation rules.
 */
public class ContactValidatorTest {

    private ContactValidator validator;

        /** 
         * Set up the ContactValidator before each test.
         * @throws Exception if setup fails
         */
    @BeforeEach
    public void setUp() {
        validator = new ContactValidator();
    }

    // ===== Contact Validation Tests =====

        /** 
         * Test validating a complete Contact object.
         * @throws ContactValidationException if the contact is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidateValidContact() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        assertDoesNotThrow(() -> validator.validate(contact));
    }

        /** 
         * Test validating a null Contact object.
         * @throws ContactValidationException if the contact is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidateNullContact() {
        assertThrows(ContactValidationException.class, () -> validator.validate(null));
    }

    // ===== Name Validation Tests =====

        /**
         * Test validating names with various valid and invalid inputs.
         * @throws ContactValidationException if the name is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidateName_Valid() {
        assertDoesNotThrow(() -> validator.validateName("John", "firstName"));
        assertDoesNotThrow(() -> validator.validateName("Mary-Jane", "firstName"));
        assertDoesNotThrow(() -> validator.validateName("O'Brien", "lastName"));
        assertDoesNotThrow(() -> validator.validateName("Jean Pierre", "firstName"));
    }

        /**
         * Test validating names with various invalid inputs.
         * @throws ContactValidationException if the name is invalid
         */
    @Test
    public void testValidateName_Null() {
        assertThrows(ContactValidationException.class, () ->
                validator.validateName(null, "firstName"));
    }

/** 
 * Test validating names with blank input.
 * @throws ContactValidationException if the name is invalid
 * @throws IllegalArgumentException if any of the fields are invalid
 */
    @Test
    public void testValidateName_Blank() {
        assertThrows(ContactValidationException.class, () ->
                validator.validateName("   ", "firstName"));
    }

        /**
         * Test validating names with invalid characters.
         * @throws ContactValidationException if the name is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidateName_InvalidCharacters() {
        // Numbers not allowed in names
        assertThrows(ContactValidationException.class, () ->
                validator.validateName("John123", "firstName"));

        // Special characters not allowed
        assertThrows(ContactValidationException.class, () ->
                validator.validateName("John@Doe", "firstName"));

        assertThrows(ContactValidationException.class, () ->
                validator.validateName("John<script>", "firstName"));
    }

    // ===== XSS Prevention Tests =====

        /**
         * Test validating address with XSS attack vectors.
         * @throws ContactValidationException if the address is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidateAddress_XSSPrevention() {
        // Script tags
        assertThrows(ContactValidationException.class, () ->
                validator.validateAddress("<script>alert('xss')</script>"));

        // JavaScript protocol
        assertThrows(ContactValidationException.class, () ->
                validator.validateAddress("javascript:alert('xss')"));

        // Event handlers
        assertThrows(ContactValidationException.class, () ->
                validator.validateAddress("<img onerror=alert('xss')>"));

        assertThrows(ContactValidationException.class, () ->
                validator.validateAddress("<div onclick=alert('xss')>"));
    }

        /** 
         * Test validating contact ID with XSS attack vectors.
         * @throws ContactValidationException if the contact ID is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidateContactId_XSSPrevention() {
        assertThrows(ContactValidationException.class, () ->
                validator.validateContactId("<script>"));
    }

    // ===== SQL Injection Prevention Tests =====

        /** 
         * Test validating address with SQL injection patterns.
         * @throws ContactValidationException if the address is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidateAddress_SQLInjectionPrevention() {
        // SQL injection patterns
        assertThrows(ContactValidationException.class, () ->
                validator.validateAddress("123; DROP TABLE contacts;--"));

        assertThrows(ContactValidationException.class, () ->
                validator.validateAddress("' OR '1'='1"));

        assertThrows(ContactValidationException.class, () ->
                validator.validateAddress("1 UNION SELECT * FROM users"));
    }

    // ===== Control Character Tests =====

        /**
         * Test validating address with control characters.
         * @throws ContactValidationException if the address is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidateAddress_ControlCharacters() {
        // Null byte
        assertThrows(ContactValidationException.class, () ->
                validator.validateAddress("123 Main\0 St"));

        // Bell character
        assertThrows(ContactValidationException.class, () ->
                validator.validateAddress("123 Main\u0007 St"));
    }

    // ===== Phone Validation Tests =====

        /** 
         * Test validating phone numbers with various valid and invalid inputs.
         * @throws ContactValidationException if the phone number is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidatePhone_Valid() {
        assertDoesNotThrow(() -> validator.validatePhone("1234567890"));
    }

        /** 
         * Test validating phone numbers with various invalid inputs.
         * @throws ContactValidationException if the phone number is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidatePhone_Null() {
        assertThrows(ContactValidationException.class, () ->
                validator.validatePhone(null));
    }
        /** 
         * Test validating phone numbers with blank input.
         * @throws ContactValidationException if the phone number is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidatePhone_Blank() {
        assertThrows(ContactValidationException.class, () ->
                validator.validatePhone("   "));
    }
        
        /** 
         * Test validating phone numbers with non-digit characters.
         * @throws ContactValidationException if the phone number is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidatePhone_NonDigits() {
        assertThrows(ContactValidationException.class, () ->
                validator.validatePhone("123-456-7890"));

        assertThrows(ContactValidationException.class, () ->
                validator.validatePhone("(123)4567890"));
    }

    // ===== Address Validation Tests =====

        /** 
         * Test validating addresses with various valid and invalid inputs.
         * @throws ContactValidationException if the address is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidateAddress_Valid() {
        assertDoesNotThrow(() -> validator.validateAddress("123 Main St"));
        assertDoesNotThrow(() -> validator.validateAddress("456 Oak Ave, Apt 7"));
    }

        /** 
         * Test validating addresses with null input.
         * @throws ContactValidationException if the address is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidateAddress_Null() {
        assertThrows(ContactValidationException.class, () ->
                validator.validateAddress(null));
    }

        /** 
         * Test validating addresses with blank input.
         * @throws ContactValidationException if the address is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidateAddress_Blank() {
        assertThrows(ContactValidationException.class, () ->
                validator.validateAddress("   "));
    }

    // ===== Contact ID Validation Tests =====

        /** 
         * Test validating contact IDs with various valid and invalid inputs.
         * @throws ContactValidationException if the contact ID is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidateContactId_Valid() {
        assertDoesNotThrow(() -> validator.validateContactId("12345"));
        assertDoesNotThrow(() -> validator.validateContactId("ABC123"));
    }
        /** 
         * Test validating contact IDs with null input.
         * @throws ContactValidationException if any of the fields are invalid
         * @throws IllegalArgumentException if any of the fields are invalid
         */
    @Test
    public void testValidateContactId_Null() {
        assertThrows(ContactValidationException.class, () ->
                validator.validateContactId(null));
    }
    
        /**  
         * Test validating contact IDs with blank input.
         * @throws ContactValidationException if the contact ID is invalid
         * @throws IllegalArgumentException if any of the fields are invalid
        */
    @Test
    public void testValidateContactId_Blank() {
        assertThrows(ContactValidationException.class, () ->
                validator.validateContactId("   "));
    }
}
