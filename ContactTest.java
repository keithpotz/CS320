/*
 * Keith Pottratz
 * CS320
 * Java Contact
 * December 8, 2024
 * 
 * 
 * */

package com.example.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ContactTest {

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

    @Test
    public void testInvalidContactId() {
        // Test invalid contact ID
        assertThrows(IllegalArgumentException.class, 
            () -> new Contact(null, "John", "Doe", "1234567890", "123 Main St"));
        assertThrows(IllegalArgumentException.class, 
            () -> new Contact("12345678901", "John", "Doe", "1234567890", "123 Main St"));
    }

    @Test
    public void testInvalidFirstName() {
        // Test invalid first name
        assertThrows(IllegalArgumentException.class, 
            () -> new Contact("12345", null, "Doe", "1234567890", "123 Main St"));
        assertThrows(IllegalArgumentException.class, 
            () -> new Contact("12345", "ThisNameIsTooLong", "Doe", "1234567890", "123 Main St"));
    }

    @Test
    public void testInvalidLastName() {
        // Test invalid last name
        assertThrows(IllegalArgumentException.class, 
            () -> new Contact("12345", "John", null, "1234567890", "123 Main St"));
        assertThrows(IllegalArgumentException.class, 
            () -> new Contact("12345", "John", "ThisNameIsTooLong", "1234567890", "123 Main St"));
    }

    @Test
    public void testInvalidPhone() {
        // Test invalid phone numbers
        assertThrows(IllegalArgumentException.class, 
            () -> new Contact("12345", "John", "Doe", null, "123 Main St"));
        assertThrows(IllegalArgumentException.class, 
            () -> new Contact("12345", "John", "Doe", "123", "123 Main St"));
        assertThrows(IllegalArgumentException.class, 
            () -> new Contact("12345", "John", "Doe", "12345678901", "123 Main St"));
    }

    @Test
    public void testInvalidAddress() {
        // Test invalid address
        assertThrows(IllegalArgumentException.class, 
            () -> new Contact("12345", "John", "Doe", "1234567890", null));
        assertThrows(IllegalArgumentException.class, 
            () -> new Contact("12345", "John", "Doe", "1234567890", "This address is way too long to be valid and should throw an error."));
    }
}
