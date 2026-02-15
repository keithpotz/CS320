/*
 * Keith Pottratz
 * CS320
 * Contact Builder Test
 * January 2026
 * Tests the ContactBuilder functionality and validation
 * 
 */
package com.example.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.example.contact.exception.ContactValidationException;

public class ContactBuilderTest {

    /** 
     * Test building a valid Contact using the builder.
     * * @throws ContactValidationException if the contact is invalid during construction
     * * @throws IllegalArgumentException if the contact ID is null or already exists
     * * @throws IllegalArgumentException if the first name is null or too long
     * * @throws IllegalArgumentException if the last name is null or too long
     * * @throws IllegalArgumentException if the phone number is null or invalid
     * * @throws IllegalArgumentException if the address is null or too long
     * 
     */
    @Test
    public void testBuildValidContact() {
        Contact contact = new ContactBuilder()
                .withContactId("12345")
                .withFirstName("John")
                .withLastName("Doe")
                .withPhone("1234567890")
                .withAddress("123 Main St")
                .build();

        assertEquals("12345", contact.getContactId());
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());
        assertEquals("1234567890", contact.getPhone());
        assertEquals("123 Main St", contact.getAddress());
    }

    /** 
     * Test building a Contact from an existing Contact.
     * * @throws ContactValidationException if the contact is invalid during construction
     * * @throws IllegalArgumentException if the contact ID is null or already exists
     * * * @throws IllegalArgumentException if the first name is null or too long
     * * * @throws IllegalArgumentException if the last name is null or too long
     * * * @throws IllegalArgumentException if the phone number is null or invalid
     * * @throws IllegalArgumentException if the address is null or too long
     * 
     */
    @Test
    public void testBuildFromExistingContact() {
        Contact original = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");

        // Create a copy with modified first name
        Contact modified = new ContactBuilder(original)
                .withContactId("67890")  // New ID since IDs must be unique
                .withFirstName("Jane")
                .build();

        assertEquals("67890", modified.getContactId());
        assertEquals("Jane", modified.getFirstName());
        assertEquals("Doe", modified.getLastName());  // Unchanged
        assertEquals("1234567890", modified.getPhone());  // Unchanged
        assertEquals("123 Main St", modified.getAddress());  // Unchanged
    }

    /** 
     * Test builder method chaining.
     * * @throws ContactValidationException if the contact is invalid during construction
     * * * @throws IllegalArgumentException if the contact ID is null or already exists
     * * * @throws IllegalArgumentException if the first name is null or too long
     * * * @throws IllegalArgumentException if the last name is null or too long
     * * @throws IllegalArgumentException if the phone number is null or invalid
     * * @throws IllegalArgumentException if the address is null or too long
     */
    @Test
    public void testBuilderMethodChaining() {
        // Verify all methods return the builder for chaining
        ContactBuilder builder = new ContactBuilder();

        assertSame(builder, builder.withContactId("12345"));
        assertSame(builder, builder.withFirstName("John"));
        assertSame(builder, builder.withLastName("Doe"));
        assertSame(builder, builder.withPhone("1234567890"));
        assertSame(builder, builder.withAddress("123 Main St"));
    }

    /**
     * Test building a Contact with invalid contact ID.
     * * @throws ContactValidationException if the contact is invalid during construction
     * * @throws IllegalArgumentException if the first name is null or too long
     * * * @throws IllegalArgumentException if the last name is null or too long
     * * @throws IllegalArgumentException if the phone number is null or invalid
     * * @throws IllegalArgumentException if the address is null or too long
     * 
     */
    @Test
    public void testBuildWithInvalidContactId() {
        ContactBuilder builder = new ContactBuilder()
                .withContactId(null)
                .withFirstName("John")
                .withLastName("Doe")
                .withPhone("1234567890")
                .withAddress("123 Main St");

        ContactValidationException exception = assertThrows(ContactValidationException.class, builder::build);
        assertNotNull(exception);
    }

    /** 
     * Test building a Contact with invalid first name.
     * * @throws ContactValidationException if the contact is invalid during construction
     * * @throws IllegalArgumentException if the last name is null or too long
     * * @throws IllegalArgumentException if the phone number is null or invalid
     * * @throws IllegalArgumentException if the address is null or too long
     */
    @Test
    public void testBuildWithInvalidFirstName() {
        ContactBuilder builder = new ContactBuilder()
                .withContactId("12345")
                .withFirstName("ThisNameIsTooLong")
                .withLastName("Doe")
                .withPhone("1234567890")
                .withAddress("123 Main St");

        ContactValidationException exception = assertThrows(ContactValidationException.class, builder::build);
        assertNotNull(exception);
    }

    /** 
     * Test building a Contact with invalid last name.
     * * @throws ContactValidationException if the contact is invalid during construction
     * * @throws IllegalArgumentException if the phone number is null or invalid
     * * @throws IllegalArgumentException if the address is null or too long
     */
    @Test
    public void testBuildWithInvalidPhone() {
        ContactBuilder builder = new ContactBuilder()
                .withContactId("12345")
                .withFirstName("John")
                .withLastName("Doe")
                .withPhone("123")  // Too short
                .withAddress("123 Main St");

        ContactValidationException exception = assertThrows(ContactValidationException.class, builder::build);
        assertNotNull(exception);
    }

    /** 
     * Test isValid method with valid and invalid data.
     * * @throws ContactValidationException if the contact is invalid during construction
     * * @throws IllegalArgumentException if the contact ID is null or already exists
     * * @throws IllegalArgumentException if the first name is null or too long
     * * @throws IllegalArgumentException if the last name is null or too long
     * * @throws IllegalArgumentException if the phone number is null or invalid
     * * @throws IllegalArgumentException if the address is null or too long
     */
    @Test
    public void testIsValidWithValidData() {
        ContactBuilder builder = new ContactBuilder()
                .withContactId("12345")
                .withFirstName("John")
                .withLastName("Doe")
                .withPhone("1234567890")
                .withAddress("123 Main St");

        assertTrue(builder.isValid());
    }

    /**
     * Test isValid method with invalid data.
     * * @throws ContactValidationException if the contact is invalid during construction
     * * @throws IllegalArgumentException if the contact ID is null or already exists
     * * @throws IllegalArgumentException if the first name is null or too long
     * * @throws IllegalArgumentException if the last name is null or too long
     * * @throws IllegalArgumentException if the phone number is null or invalid
     */
    @Test
    public void testIsValidWithInvalidData() {
        ContactBuilder builder = new ContactBuilder()
                .withContactId("12345")
                .withFirstName(null)  // Invalid
                .withLastName("Doe")
                .withPhone("1234567890")
                .withAddress("123 Main St");

        ContactValidationException exception = assertThrows(ContactValidationException.class, builder::isValid);
        assertNotNull(exception);
    }

    /**
     * Test building a Contact from a null existing Contact.
     * * * @throws ContactValidationException if the contact is invalid during construction
     * * @throws IllegalArgumentException if the contact ID is null or already exists
     * * * @throws IllegalArgumentException if the first name is null or too long
     * * * @throws IllegalArgumentException if the last name is null or too long
     * * * @throws IllegalArgumentException if the phone number is null or invalid
     * * @throws IllegalArgumentException if the address is null or too long
     */
    @Test
    public void testBuilderFromNullContact() {
        // Should not throw, just create empty builder
        ContactBuilder builder = new ContactBuilder(null);
        ContactValidationException exception = assertThrows(ContactValidationException.class, builder::build);
        assertNotNull(exception);
    }
}
