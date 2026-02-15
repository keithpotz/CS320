/*
 * Keith Pottratz
 * CS320
 * Contact Service Mock Test
 * January 2026
 * Tests service layer with mocked repository using Mockito
 */
package com.example.contact;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.contact.exception.ContactNotFoundException;
import com.example.contact.exception.ContactValidationException;
import com.example.contact.exception.DuplicateContactException;
import com.example.contact.repository.IContactRepository;
import com.example.contact.service.ContactServiceImpl;
import com.example.contact.service.IContactService;
import com.example.contact.validation.ContactValidator;

/**
 * Unit tests for ContactServiceImpl using Mockito to mock the repository.
 * These tests verify service behavior in isolation from the actual repository.
 */
@ExtendWith(MockitoExtension.class)
public class ContactServiceMockTest {

    /** 
     * Mocked repository for isolating service tests.
     */
    @Mock
    private IContactRepository mockRepository;

    private IContactService service;
    private ContactValidator validator;

    /** 
     * Set up the service with the mocked repository before each test.
     * @throws Exception if an error occurs during setup
     * 
     */
    @BeforeEach
    public void setUp() {
        validator = new ContactValidator();
        service = new ContactServiceImpl(mockRepository, validator);
    }

    /** 
     * Test adding a contact successfully.
     * * @throws ContactValidationException if the contact is invalid during addition
     * * @throws ContactValidationException if the contact is null during addition
     */
    @Test
    public void testAddContact_Success() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");

        when(mockRepository.existsById("12345")).thenReturn(false);

        service.addContact(contact);

        verify(mockRepository).existsById("12345");
        verify(mockRepository).save(contact);
    }

    /**
     * Test adding a duplicate contact throws DuplicateContactException.
     * * @throws DuplicateContactException if a contact with the same ID already exists
     * * @throws ContactValidationException if the contact is null during addition
     */
    @Test
    public void testAddContact_DuplicateThrowsException() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");

        when(mockRepository.existsById("12345")).thenReturn(true);

        DuplicateContactException exception = assertThrows(DuplicateContactException.class, () -> service.addContact(contact));
        assertNotNull(exception);

        verify(mockRepository).existsById("12345");
        verify(mockRepository, never()).save(any());
    }

    /** 
     * Test adding a null contact throws ContactValidationException.
     * * @throws ContactValidationException if the contact is null during addition
     */
    @Test
    public void testAddContact_NullThrowsException() {
        ContactValidationException exception = assertThrows(ContactValidationException.class, () -> service.addContact(null));
        assertNotNull(exception);

        verify(mockRepository, never()).existsById(any());
        verify(mockRepository, never()).save(any());
    }

    /** 
     * Test deleting a contact successfully.
     * * @throws ContactNotFoundException if the contact to delete does not exist
     * * @throws ContactValidationException if the contact to delete is null or blank
     */
    @Test
    public void testDeleteContact_Success() {
        when(mockRepository.existsById("12345")).thenReturn(true);
        when(mockRepository.deleteById("12345")).thenReturn(true);

        service.deleteContact("12345");

        verify(mockRepository).existsById("12345");
        verify(mockRepository).deleteById("12345");
    }

    /** 
     * Test deleting a non-existent contact throws ContactNotFoundException.
     * * @throws ContactNotFoundException if the contact to delete does not exist
     */
    @Test
    public void testDeleteContact_NotFoundThrowsException() {
        when(mockRepository.existsById("99999")).thenReturn(false);

        ContactNotFoundException exception = assertThrows(ContactNotFoundException.class, () -> service.deleteContact("99999"));
        assertNotNull(exception);

        verify(mockRepository).existsById("99999");
        verify(mockRepository, never()).deleteById(any());
    }

    /**
     * Test deleting a contact with null or blank ID throws ContactValidationException.
     * * @throws ContactValidationException if the contact to delete is null or blank
     */
    @Test
    public void testDeleteContact_NullIdThrowsException() {
        ContactValidationException exception = assertThrows(ContactValidationException.class, () -> service.deleteContact(null));
        assertNotNull(exception);

        verify(mockRepository, never()).existsById(any());
        verify(mockRepository, never()).deleteById(any());
    }

    /**
     * Test deleting a contact with blank ID throws ContactValidationException.
     * * @throws ContactValidationException if the contact to delete is null or blank
     */
    @Test
    public void testDeleteContact_BlankIdThrowsException() {
        ContactValidationException exception = assertThrows(ContactValidationException.class, () -> service.deleteContact("   "));
        assertNotNull(exception);

        verify(mockRepository, never()).existsById(any());
        verify(mockRepository, never()).deleteById(any());
    }

    /** 
     * Test updating a contact successfully.
     * * @throws ContactNotFoundException if the contact to update does not exist
     * * @throws ContactValidationException if any provided field value is invalid
     */
    @Test
    public void testUpdateContact_Success() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");

        when(mockRepository.findById("12345")).thenReturn(Optional.of(contact));

        service.updateContact("12345", "Jane", null, "0987654321", null);

        assertEquals("Jane", contact.getFirstName());
        assertEquals("0987654321", contact.getPhone());
        assertEquals("Doe", contact.getLastName()); // Unchanged
        assertEquals("123 Main St", contact.getAddress()); // Unchanged

        verify(mockRepository).findById("12345");
        verify(mockRepository).save(contact);
    }

    /**
     * Test updating a non-existent contact throws ContactNotFoundException.
     * * @throws ContactNotFoundException if the contact to update does not exist
     */
    @Test
    public void testUpdateContact_NotFoundThrowsException() {
        when(mockRepository.findById("99999")).thenReturn(Optional.empty());

        ContactNotFoundException exception = assertThrows(ContactNotFoundException.class, () ->
                service.updateContact("99999", "Jane", null, null, null));
        assertNotNull(exception);

        verify(mockRepository).findById("99999");
        verify(mockRepository, never()).save(any());
    }

    /** 
     * Test updating a contact with null ID throws ContactValidationException.
     * * @throws ContactValidationException if the contact to update is null or blank
     */
    @Test
    public void testUpdateContact_NullIdThrowsException() {
        ContactValidationException exception = assertThrows(ContactValidationException.class, () ->
                service.updateContact(null, "Jane", null, null, null));
        assertNotNull(exception);

        verify(mockRepository, never()).findById(any());
    }

    /**
     * Test updating a contact with blank ID throws ContactValidationException.
     * * @throws ContactValidationException if the contact to update is null or blank
     */
    @Test
    public void testGetContact_Success() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");

        when(mockRepository.findById("12345")).thenReturn(Optional.of(contact));

        Contact result = service.getContact("12345");

        assertNotNull(result);
        assertEquals("12345", result.getContactId());
        verify(mockRepository).findById("12345");
    }

    /**
     * Test getting a non-existent contact returns null.
     * * @throws ContactValidationException if the contact ID is null or blank
     */
    @Test
    public void testGetContact_NotFound() {
        when(mockRepository.findById("99999")).thenReturn(Optional.empty());

        Contact result = service.getContact("99999");

        assertNull(result);
        verify(mockRepository).findById("99999");
    }

    /**
     * Test getting a contact with blank ID returns null.
     * * @throws ContactValidationException if the contact ID is null or blank
     */
    @Test
    public void testGetContact_NullIdReturnsNull() {
        Contact result = service.getContact(null);

        assertNull(result);
        verify(mockRepository, never()).findById(any());
    }

    /**
     * Test getting a contact with blank ID returns null.
     * * @throws ContactValidationException if the contact ID is null or blank
     */
    @Test
    public void testGetAllContacts_Success() {
        Contact contact1 = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        Contact contact2 = new Contact("67890", "Jane", "Smith", "0987654321", "456 Oak St");

        when(mockRepository.findAll()).thenReturn(Arrays.asList(contact1, contact2));

        List<Contact> results = service.getAllContacts();

        assertEquals(2, results.size());
        verify(mockRepository).findAll();
    }

    /** 
     * Test getting all contacts when repository is empty.
     * * @throws ContactValidationException if an error occurs during retrieval
     */
    @Test
    public void testGetAllContacts_EmptyList() {
        when(mockRepository.findAll()).thenReturn(Arrays.asList());

        List<Contact> results = service.getAllContacts();

        assertTrue(results.isEmpty());
        verify(mockRepository).findAll();
    }

    /** 
     * Test service constructor with null repository throws IllegalArgumentException.
     * * @throws IllegalArgumentException if the repository is null
     */
    @Test
    public void testServiceRejectsNullRepository() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new ContactServiceImpl(null));
        assertNotNull(exception);
    }

    /** 
     * Test updating a contact with no fields provided (all null).
     * * @throws ContactNotFoundException if the contact to update does not exist
     * * @throws ContactValidationException if any provided field value is invalid
     * 
     */
    @Test
    public void testUpdateContact_NoFieldsProvided() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");

        when(mockRepository.findById("12345")).thenReturn(Optional.of(contact));

        // All fields null - nothing to update
        service.updateContact("12345", null, null, null, null);

        // Contact should remain unchanged
        assertEquals("John", contact.getFirstName());
        assertEquals("Doe", contact.getLastName());

        verify(mockRepository).findById("12345");
        // save should NOT be called when no fields are updated
        verify(mockRepository, never()).save(any());
    }
}
