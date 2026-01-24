/*
 * Keith Pottratz
 * CS320
 * In-Memory Contact Repository Implementation
 * January 2026
 * Updated: Added thread safety, logging, and resource limits
 */
package com.example.contact.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.contact.Contact;
import com.example.contact.exception.ResourceLimitException;

/**
 * Thread-safe in-memory implementation of the IContactRepository interface.
 * Uses ConcurrentHashMap for thread-safe O(1) contact lookups by ID.
 * Includes resource limits to prevent denial of service attacks.
 * Data is not persisted and will be lost when the application stops.
 */
public class InMemoryContactRepository implements IContactRepository {

    private static final Logger logger = LoggerFactory.getLogger(InMemoryContactRepository.class);
    private static final Logger auditLogger = LoggerFactory.getLogger("AUDIT");

    /** Maximum number of contacts allowed (DoS prevention) */
    public static final int MAX_CONTACTS = 10000;

    private final Map<String, Contact> contacts = new ConcurrentHashMap<>();

    @Override
    public void save(Contact contact) {
        if (contact == null) {
            logger.warn("Attempted to save null contact");
            throw new IllegalArgumentException("Contact cannot be null");
        }

        // Check resource limit before adding new contact
        if (!contacts.containsKey(contact.getContactId()) && contacts.size() >= MAX_CONTACTS) {
            auditLogger.warn("Security: Maximum contact limit ({}) reached - save rejected for ID: {}",
                    MAX_CONTACTS, contact.getContactId());
            throw new ResourceLimitException("Maximum contact limit reached: " + MAX_CONTACTS);
        }

        Contact previous = contacts.put(contact.getContactId(), contact);

        if (previous == null) {
            auditLogger.info("Contact created: ID={}, Name={} {}",
                    contact.getContactId(), contact.getFirstName(), contact.getLastName());
            logger.debug("New contact saved with ID: {}", contact.getContactId());
        } else {
            auditLogger.info("Contact updated: ID={}, Name={} {}",
                    contact.getContactId(), contact.getFirstName(), contact.getLastName());
            logger.debug("Contact updated with ID: {}", contact.getContactId());
        }
    }

    /**
     * Finds a contact by its unique ID.
     * @param contactId the contact ID to search for
     * @return an Optional containing the contact if found, empty otherwise
     */
    @Override
    public Optional<Contact> findById(String contactId) {
        if (contactId == null) {
            logger.warn("Attempted to find contact with null ID");
            return Optional.empty();
        }

        Optional<Contact> result = Optional.ofNullable(contacts.get(contactId));
        logger.debug("Find by ID {}: {}", contactId, result.isPresent() ? "found" : "not found");
        return result;
    }

    /**
     * Checks if a contact exists with the given ID.
     * @param contactId the contact ID to check
     * @return true if a contact with the given ID exists, false otherwise
     */
    @Override
    public boolean existsById(String contactId) {
        if (contactId == null) {
            return false;
        }
        return contacts.containsKey(contactId);
    }

    /**
     * Deletes a contact by its ID.
     * @param contactId the ID of the contact to delete
     * @return true if the contact was deleted, false if not found
     * 
     */
    @Override
    public boolean deleteById(String contactId) {
        if (contactId == null) {
            logger.warn("Attempted to delete contact with null ID");
            return false;
        }

        Contact removed = contacts.remove(contactId);

        if (removed != null) {
            auditLogger.info("Contact deleted: ID={}, Name={} {}",
                    contactId, removed.getFirstName(), removed.getLastName());
            logger.debug("Contact deleted with ID: {}", contactId);
            return true;
        }

        logger.debug("Delete attempted for non-existent ID: {}", contactId);
        return false;
    }

    /**
     * Returns all contacts in the repository.
     * @return a list of all contacts
     */
    @Override
    public List<Contact> findAll() {
        logger.debug("Finding all contacts, count: {}", contacts.size());
        return new ArrayList<>(contacts.values());
    }

    /**
     * Returns the total number of contacts in the repository.
     * @return the count of contacts
     */
    @Override
    public int count() {
        return contacts.size();
    }

    /**
     * Returns the maximum number of contacts allowed.
     * @return the maximum contact limit
     */
    public int getMaxContacts() {
        return MAX_CONTACTS;
    }

    /**
     * Clears all contacts from the repository.
     * Primarily used for testing purposes.
     */
    public void clear() {
        int count = contacts.size();
        contacts.clear();
        auditLogger.info("Repository cleared: {} contacts removed", count);
        logger.debug("Repository cleared");
    }
}
