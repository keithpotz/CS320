/*
 * Keith Pottratz
 * CS320
 * Java Contact Service Test
 * November 15, 2024
 * Updated: January 2026 - Updated to use new architecture with dependency injection
 *              - Added tests for exception handling
 * 
 */

package com.example.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.contact.exception.ContactNotFoundException;
import com.example.contact.exception.ContactValidationException;
import com.example.contact.exception.DuplicateContactException;
import com.example.contact.repository.IContactRepository;
import com.example.contact.repository.InMemoryContactRepository;
import com.example.contact.service.ContactServiceImpl;
import com.example.contact.service.IContactService;

public class ContactServiceTest {

    private IContactService service;
    private IContactRepository repository;

    /** 
     * Set up the ContactService with an InMemoryContactRepository before each test.
     * @throws Exception if setup fails
     * 
     */
    @BeforeEach
    public void setUp() {
        // Create repository and inject into service
        repository = new InMemoryContactRepository();
        service = new ContactServiceImpl(repository);
    }

    /** 
     * Test adding a contact successfully.
     * * @throws ContactValidationException if the contact is invalid
     * @throws ContactNotFoundException if the contact is not found during deletion
     * @throws DuplicateContactException if a contact with the same ID already exists
     * @throws ResourceLimitException if the system reaches its resource limit
     */
    @Test
    public void testAddContact() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");

        // Add contact and verify it exists in the service
        service.addContact(contact);
        assertEquals(contact, service.getContact("12345"));
    }

    /**
     * Test deleting a contact successfully.
     * * @throws ContactNotFoundException if the contact to delete does not exist
     * @throws ResourceLimitException if the system reaches its resource limit
     */
    @Test
    public void testDeleteContact() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");

        // Add and delete contact, then verify it's removed
        service.addContact(contact);
        service.deleteContact("12345");
        assertNull(service.getContact("12345"));
    }

    /**
     * Test updating a contact successfully.
     * * @throws ContactNotFoundException if the contact to update does not exist
     * @throws ResourceLimitException if the system reaches its resource limit
     */
    @Test
    public void testUpdateContact() {
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

    /** 
     * Test adding a duplicate contact throws DuplicateContactException.
     * * @throws DuplicateContactException if a contact with the same ID already exists
     * @throws ContactValidationException if the contact is null during addition
     */
    @Test
    public void testAddDuplicateContact() {
        Contact contact1 = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        Contact contact2 = new Contact("12345", "Jane", "Smith", "0987654321", "456 Oak St");

        // Add the first contact
        service.addContact(contact1);

        // Adding a contact with duplicate ID should throw DuplicateContactException
        assertThrows(DuplicateContactException.class, () -> service.addContact(contact2));
    }

    /** 
     * Test deleting a non-existent contact throws ContactNotFoundException.
     * * @throws ContactNotFoundException if the contact to delete does not exist
     * @throws ResourceLimitException if the system reaches its resource limit
     */
    @Test
    public void testDeleteNonexistentContact() {
        // Attempting to delete a non-existent contact should throw ContactNotFoundException
        assertThrows(ContactNotFoundException.class, () -> service.deleteContact("99999"));
    }

    /** 
     * Test updating a non-existent contact throws ContactNotFoundException.
     * * @throws ContactNotFoundException if the contact to update does not exist
     * @throws ResourceLimitException if the system reaches its resource limit
     */
    @Test
    public void testUpdateNonexistentContact() {
        // Attempting to update a non-existent contact should throw ContactNotFoundException
        assertThrows(ContactNotFoundException.class, () ->
            service.updateContact("99999", "Jane", "Doe", "1234567890", "456 Oak St")
        );
    }

    /** 
     * Test adding a null contact throws ContactValidationException.
     * * @throws ContactValidationException if the contact is null during addition
     * @throws ResourceLimitException if the system reaches its resource limit
     */
    @Test
    public void testAddNullContact() {
        // Attempting to add a null contact should throw ContactValidationException
        assertThrows(ContactValidationException.class, () -> service.addContact(null));
    }

    /** 
     * Test retrieving all contacts.
     * * @throws ContactValidationException if an error occurs during retrieval
     * @throws ResourceLimitException if the system reaches its resource limit
     */
    @Test
    public void testGetAllContacts() {
        Contact contact1 = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        Contact contact2 = new Contact("67890", "Jane", "Smith", "0987654321", "456 Oak St");

        service.addContact(contact1);
        service.addContact(contact2);

        assertEquals(2, service.getAllContacts().size());
    }
}
