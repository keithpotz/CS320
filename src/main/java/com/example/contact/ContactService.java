/*
 * Keith Pottratz
 * CS320
 * Java Contact
 * December 8, 2024
 * 
 * 
 * */

package com.example.contact;

import java.util.HashMap;
import java.util.Map;

public class ContactService {
    private final Map<String, Contact> contacts = new HashMap<>();

    // Add a contact if the ID does not already exist
    public void addContact(Contact contact) {
        if (contacts.containsKey(contact.getContactId())) {
            throw new IllegalArgumentException("Contact ID already exists");
        }
        contacts.put(contact.getContactId(), contact);
    }

    // Delete a contact by ID
    public void deleteContact(String contactId) {
        if (!contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("Contact ID not found");
        }
        contacts.remove(contactId);
    }

    // Update contact details by ID
    public void updateContact(String contactId, String firstName, String lastName, String phone, String address) {
        Contact contact = contacts.get(contactId);
        if (contact == null) {
            throw new IllegalArgumentException("Contact ID not found");
        }

        // Update fields only if provided
        if (firstName != null && !firstName.isBlank()) {
            contact.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isBlank()) {
            contact.setLastName(lastName);
        }
        if (phone != null && !phone.isBlank()) {
            contact.setPhone(phone);
        }
        if (address != null && !address.isBlank()) {
            contact.setAddress(address);
        }
    }

    // Retrieve a contact by ID
    public Contact getContact(String contactId) {
        return contacts.get(contactId);
    }
}