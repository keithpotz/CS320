/*
 * Keith Pottratz
 * CS320
 * Contact Repository Interface
 * January 2026
 */
package com.example.contact.repository;

import com.example.contact.Contact;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Contact data access operations.
 * Abstracts the storage mechanism from the service layer,
 * allowing for different implementations (in-memory, database, file, etc.).
 */
public interface IContactRepository {

    /**
     * Saves a contact to the repository.
     * @param contact the contact to save
     */
    void save(Contact contact);

    /**
     * Finds a contact by its unique ID.
     * @param contactId the contact ID to search for
     * @return an Optional containing the contact if found, empty otherwise
     */
    Optional<Contact> findById(String contactId);

    /**
     * Checks if a contact exists with the given ID.
     * @param contactId the contact ID to check
     * @return true if a contact exists with this ID, false otherwise
     */
    boolean existsById(String contactId);

    /**
     * Deletes a contact by its ID.
     * @param contactId the ID of the contact to delete
     * @return true if the contact was deleted, false if it didn't exist
     */
    boolean deleteById(String contactId);

    /**
     * Returns all contacts in the repository.
     * @return a list of all contacts
     */
    List<Contact> findAll();

    /**
     * Returns the total number of contacts in the repository.
     * @return the count of contacts
     */
    int count();
}
