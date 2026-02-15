/*
 * Keith Pottratz
 * CS320
 * Java Contact
 * November 15, 2024
 * 
 * 
 * */

package com.example.contact;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceTest {

    @Test
    public void testAddContact() {
        ContactService service = new ContactService();
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        
        // Add contact and verify it exists in the service
        service.addContact(contact);
        assertEquals(contact, service.getContact("12345"));
    }

    @Test
    public void testDeleteContact() {
        ContactService service = new ContactService();
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        
        // Add and delete contact, then verify it's removed
        service.addContact(contact);
        service.deleteContact("12345");
        assertNull(service.getContact("12345"));
    }

    @Test
    public void testUpdateContact() {
        ContactService service = new ContactService();
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        
        // Add contact and update specific fields
        service.addContact(contact);
        service.updateContact("12345", "Jane", null, "0987654321", null);
        
        // Verify updated fields
        assertEquals("Jane", contact.getFirstName());
        assertEquals("0987654321", contact.getPhone());
        
        // Verify unchanged fields
        assertEquals("Doe", contact.getLastName());
        assertEquals("123 Main St", contact.getAddress());
    }

    @Test
    public void testAddDuplicateContact() {
        ContactService service = new ContactService();
        Contact contact1 = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        Contact contact2 = new Contact("12345", "Jane", "Smith", "0987654321", "456 Oak St");
        
        // Add the first contact
        service.addContact(contact1);
        
        // Adding a contact with duplicate ID should throw an exception
        assertThrows(IllegalArgumentException.class, () -> service.addContact(contact2));
    }

    @Test
    public void testDeleteNonexistentContact() {
        ContactService service = new ContactService();
        
        // Attempting to delete a non-existent contact should throw an exception
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("99999"));
    }

    @Test
    public void testUpdateNonexistentContact() {
        ContactService service = new ContactService();
        
        // Attempting to update a non-existent contact should throw an exception
        assertThrows(IllegalArgumentException.class, () -> 
            service.updateContact("99999", "Jane", "Doe", "1234567890", "456 Oak St")
        );
    }
}
