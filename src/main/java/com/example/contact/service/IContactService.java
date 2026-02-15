/*
 * Keith Pottratz
 * CS320
 * Contact Service Interface
 * January 2026
 * Updated: Added exception handling for contact operations
 */
package com.example.contact.service;

import java.util.List;

import com.example.contact.Contact;
import com.example.contact.exception.ContactNotFoundException;
import com.example.contact.exception.ContactValidationException;
import com.example.contact.exception.DuplicateContactException;

/**
 * Service interface for Contact business operations.
 * Defines the contract for contact management operations,
 * allowing for different service implementations.
 */
public interface IContactService {

    /**
     * Adds a new contact to the system.
     * @param contact the contact to add
     * @throws DuplicateContactException if a contact with the same ID already exists
     * @throws ContactValidationException if the contact is null or invalid
     */
    void addContact(Contact contact);

    /**
     * Deletes a contact by its ID.
     * @param contactId the ID of the contact to delete
     * @throws ContactNotFoundException if no contact exists with the given ID
     */
    void deleteContact(String contactId);

    /**
     * Updates an existing contact's fields.
     * Only non-null and non-blank fields will be updated.
     * @param contactId the ID of the contact to update
     * @param firstName the new first name (or null to keep unchanged)
     * @param lastName the new last name (or null to keep unchanged)
     * @param phone the new phone number (or null to keep unchanged)
     * @param address the new address (or null to keep unchanged)
     * @throws ContactNotFoundException if no contact exists with the given ID
     * @throws ContactValidationException if any provided field value is invalid
     */
    void updateContact(String contactId, String firstName, String lastName, String phone, String address);

    /**
     * Retrieves a contact by its ID.
     * @param contactId the ID of the contact to retrieve
     * @return the contact, or null if not found
     */
    Contact getContact(String contactId);

    /**
     * Returns all contacts in the system.
     * @return a list of all contacts
     */
    List<Contact> getAllContacts();
}
