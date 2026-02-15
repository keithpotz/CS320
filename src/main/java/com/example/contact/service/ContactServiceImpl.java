/*
 * Keith Pottratz
 * CS320
 * Contact Service Implementation
 * January 2026
 * Updated: Added logging, input validation, and security features
 * 
 */
package com.example.contact.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.contact.Contact;
import com.example.contact.exception.ContactNotFoundException;
import com.example.contact.exception.ContactValidationException;
import com.example.contact.exception.DuplicateContactException;
import com.example.contact.repository.IContactRepository;
import com.example.contact.validation.ContactValidator;

/**
 * Implementation of the IContactService interface.
 * Provides business logic for contact management operations.
 * Includes logging, input validation, and security features.
 * Uses dependency injection for the repository layer.
 */
public class ContactServiceImpl implements IContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);
    private static final Logger auditLogger = LoggerFactory.getLogger("AUDIT");

    private final IContactRepository repository;
    private final ContactValidator validator;

    /**
     * Constructs a ContactServiceImpl with the specified repository.
     * Uses default ContactValidator.
     * @param repository the repository to use for data access
     */
    public ContactServiceImpl(IContactRepository repository) {
        this(repository, new ContactValidator());
    }

    /**
     * Constructs a ContactServiceImpl with the specified repository and validator.
     * @param repository the repository to use for data access
     * @param validator the validator to use for input validation
     */
    public ContactServiceImpl(IContactRepository repository, ContactValidator validator) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
        this.validator = validator != null ? validator : new ContactValidator();
        logger.info("ContactServiceImpl initialized");
    }

    @Override
    public void addContact(Contact contact) {
        logger.debug("Attempting to add contact");

        // Null validation
        if (contact == null) {
            auditLogger.warn("Security: Attempted to add null contact");
            throw new ContactValidationException("Contact cannot be null");
        }

        // Input sanitization/validation
        try {
            validator.validate(contact);
        } catch (ContactValidationException e) {
            auditLogger.warn("Security: Contact validation failed for ID {}: {}",
                    contact.getContactId(), e.getMessage());
            throw e;
        }

        // Check for duplicates
        if (repository.existsById(contact.getContactId())) {
            auditLogger.warn("Duplicate contact ID attempted: {}", contact.getContactId());
            throw new DuplicateContactException(contact.getContactId());
        }

        repository.save(contact);
        logger.info("Contact added successfully: ID={}", contact.getContactId());
    }

    /**
     * Deletes a contact by its ID.
     * @param contactId the ID of the contact to delete
     * @throws ContactNotFoundException if the contact does not exist
     * @throws ContactValidationException if the contactId is null or blank
     * @throws ResourceLimitException if the system reaches its resource limit
     * @throws IllegalArgumentException if any of the fields are invalid during update
     * 
     */
    @Override
    public void deleteContact(String contactId) {
        logger.debug("Attempting to delete contact: {}", contactId);

        // Null validation
        if (contactId == null || contactId.isBlank()) {
            auditLogger.warn("Security: Attempted to delete contact with null/blank ID");
            throw new ContactValidationException("Contact ID cannot be null or blank");
        }

        if (!repository.existsById(contactId)) {
            auditLogger.warn("Delete attempted for non-existent contact: {}", contactId);
            throw new ContactNotFoundException(contactId);
        }

        repository.deleteById(contactId);
        logger.info("Contact deleted: ID={}", contactId);
    }

    /**
     * Updates a contact's information.
     * @param contactId the ID of the contact to update
     * @param firstName the new first name (optional)
     * @param lastName the new last name (optional)
     * @param phone the new phone number (optional)
     * @param address the new address (optional)
     * @throws ContactValidationException if the input is invalid during update
     * @throws ContactNotFoundException if the contact does not exist
     * @throws ResourceLimitException if the system reaches its resource limit
     * @throws IllegalArgumentException if any of the fields are invalid during update
     * 
     */
    @Override
    public void updateContact(String contactId, String firstName, String lastName, String phone, String address) {
        logger.debug("Attempting to update contact: {}", contactId);

        // Null validation for contact ID
        if (contactId == null || contactId.isBlank()) {
            auditLogger.warn("Security: Attempted to update contact with null/blank ID");
            throw new ContactValidationException("Contact ID cannot be null or blank");
        }

        Contact contact = repository.findById(contactId)
                .orElseThrow(() -> {
                    auditLogger.warn("Update attempted for non-existent contact: {}", contactId);
                    return new ContactNotFoundException(contactId);
                });

        // Validate and update fields only if provided and not blank
        boolean updated = false;

        if (firstName != null && !firstName.isBlank()) {
            validator.validateName(firstName, "firstName");
            contact.setFirstName(firstName);
            updated = true;
        }
        if (lastName != null && !lastName.isBlank()) {
            validator.validateName(lastName, "lastName");
            contact.setLastName(lastName);
            updated = true;
        }
        if (phone != null && !phone.isBlank()) {
            validator.validatePhone(phone);
            contact.setPhone(phone);
            updated = true;
        }
        if (address != null && !address.isBlank()) {
            validator.validateAddress(address);
            contact.setAddress(address);
            updated = true;
        }

        if (updated) {
            // Save the updated contact
            repository.save(contact);
            logger.info("Contact updated: ID={}", contactId);
        } else {
            logger.debug("No fields to update for contact: {}", contactId);
        }
    }

    /**
     * Retrieves a contact by its ID.
     * @param contactId the ID of the contact to retrieve
     * @return the contact object, or null if not found
     */
    @Override
    public Contact getContact(String contactId) {
        logger.debug("Retrieving contact: {}", contactId);

        if (contactId == null || contactId.isBlank()) {
            logger.warn("Attempted to get contact with null/blank ID");
            return null;
        }

        return repository.findById(contactId).orElse(null);
    }

    /**
     * Retrieves all contacts.
     * @return a list of all contacts
     */
    @Override
    public List<Contact> getAllContacts() {
        logger.debug("Retrieving all contacts");
        List<Contact> contacts = repository.findAll();
        logger.debug("Retrieved {} contacts", contacts.size());
        return contacts;
    }
}
