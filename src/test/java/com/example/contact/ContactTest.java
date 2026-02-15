/*
 * Keith Pottratz
 * CS320
 * Java Contact
 * December 8, 2024
 * Updated: January 2026 - Updated to use custom exceptions
 * 
 * 
 */

package com.example.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import com.example.contact.exception.ContactValidationException;

public class ContactTest {

    /** 
     * Test creating a valid Contact.
     * * @throws ContactValidationException if the contact is invalid during construction
     * @throws IllegalArgumentException if the contact ID is null or already exists
     * @throws IllegalArgumentException if the first name is null or too long
     * @throws IllegalArgumentException if the last name is null or too long
     * @throws IllegalArgumentException if the phone number is null or invalid
     * @throws IllegalArgumentException if the address is null or too long
     */
    @Test
    public void testContactCreationValid() {
        // Test valid contact creation
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        assertEquals("12345", contact.getContactId());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("123 Main St", contact.getAddress());
    }

    /** 
     * Test creating a Contact with invalid fields.
     * * @throws ContactValidationException if the contact is invalid during construction
     * @throws IllegalArgumentException if the contact ID is null or already exists
     * @throws IllegalArgumentException if the first name is null or too long
     * @throws IllegalArgumentException if the last name is null or too long
     * @throws IllegalArgumentException if the phone number is null or invalid
     * @throws IllegalArgumentException if the address is null or too long
     */
    @Test
    public void testInvalidContactId() {
        // Test invalid contact ID
        assertThrows(ContactValidationException.class,
            () -> new Contact(null, "John", "Doe", "1234567890", "123 Main St"));
        assertThrows(ContactValidationException.class,
            () -> new Contact("12345678901", "John", "Doe", "1234567890", "123 Main St"));
    }

    /** 
     * Test creating a Contact with invalid first name.
     * * @throws ContactValidationException if the contact is invalid during construction
     * @throws IllegalArgumentException if the contact ID is null or already exists
     * @throws IllegalArgumentException if the last name is null or too long
     * @throws IllegalArgumentException if the phone number is null or invalid
     * @throws IllegalArgumentException if the address is null or too long
     */
    @Test
    public void testInvalidFirstName() {
        // Test invalid first name
        assertThrows(ContactValidationException.class,
            () -> new Contact("12345", null, "Doe", "1234567890", "123 Main St"));
        assertThrows(ContactValidationException.class,
            () -> new Contact("12345", "ThisNameIsTooLong", "Doe", "1234567890", "123 Main St"));
    }

    /** 
     * Test creating a Contact with invalid last name.
     * * @throws ContactValidationException if the contact is invalid during construction
     * @throws IllegalArgumentException if the contact ID is null or already exists
     * @throws IllegalArgumentException if the phone number is null or invalid
     * @throws IllegalArgumentException if the address is null or too long
     */
    @Test
    public void testInvalidLastName() {
        // Test invalid last name
        assertThrows(ContactValidationException.class,
            () -> new Contact("12345", "John", null, "1234567890", "123 Main St"));
        assertThrows(ContactValidationException.class,
            () -> new Contact("12345", "John", "ThisNameIsTooLong", "1234567890", "123 Main St"));
    }

    /** 
     * Test creating a Contact with invalid phone number.
     * * @throws ContactValidationException if the contact is invalid during construction
     * @throws IllegalArgumentException if the contact ID is null or already exists
     * @throws IllegalArgumentException if the phone number is null or invalid
     * @throws IllegalArgumentException if the address is null or too long
     */
    @Test
    public void testInvalidPhone() {
        // Test invalid phone numbers
        assertThrows(ContactValidationException.class,
            () -> new Contact("12345", "John", "Doe", null, "123 Main St"));
        assertThrows(ContactValidationException.class,
            () -> new Contact("12345", "John", "Doe", "123", "123 Main St"));
        assertThrows(ContactValidationException.class,
            () -> new Contact("12345", "John", "Doe", "12345678901", "123 Main St"));
    }

    /** 
     * Test creating a Contact with invalid address.
     * * @throws ContactValidationException if the contact is invalid during construction
     * @throws IllegalArgumentException if the contact ID is null or already exists
     * @throws IllegalArgumentException if the phone number is null or invalid
     * @throws IllegalArgumentException if the address is null or too long
     */
    @Test
    public void testInvalidAddress() {
        // Test invalid address
        assertThrows(ContactValidationException.class,
            () -> new Contact("12345", "John", "Doe", "1234567890", null));
        assertThrows(ContactValidationException.class,
            () -> new Contact("12345", "John", "Doe", "1234567890", "This address is way too long to be valid and should throw an error."));
    }
}
