/*
 * Keith Pottratz
 * CS320
 * Contact Builder
 * January 2026
 * Provides a fluent interface for constructing Contact objects.
 * 
 */
package com.example.contact;

import com.example.contact.exception.ContactValidationException;

/**
 * Builder class for creating Contact objects using a fluent interface.
 * Provides a more readable and flexible way to construct Contact instances.
 *
 * Example usage:
 * <pre>
 * Contact contact = new ContactBuilder()
 *     .withContactId("12345")
 *     .withFirstName("John")
 *     .withLastName("Doe")
 *     .withPhone("1234567890")
 *     .withAddress("123 Main St")
 *     .build();
 * </pre>
 */
public class ContactBuilder {

    private String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    /**
     * Creates a new ContactBuilder instance.
     */
    public ContactBuilder() {
        // Default constructor
    }

    /**
     * Creates a new ContactBuilder initialized with values from an existing Contact.
     * Useful for creating modified copies of contacts.
     * @param contact the contact to copy values from
     */
    public ContactBuilder(Contact contact) {
        if (contact != null) {
            this.contactId = contact.getContactId();
            this.firstName = contact.getFirstName();
            this.lastName = contact.getLastName();
            this.phone = contact.getPhone();
            this.address = contact.getAddress();
        }
    }

    /**
     * Sets the contact ID.
     * @param contactId the unique identifier (max 10 characters)
     * @return this builder for method chaining
     */
    public ContactBuilder withContactId(String contactId) {
        this.contactId = contactId;
        return this;
    }

    /**
     * Sets the first name.
     * @param firstName the first name (max 10 characters)
     * @return this builder for method chaining
     */
    public ContactBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * Sets the last name.
     * @param lastName the last name (max 10 characters)
     * @return this builder for method chaining
     */
    public ContactBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * Sets the phone number.
     * @param phone the phone number (exactly 10 digits)
     * @return this builder for method chaining
     */
    public ContactBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Sets the address.
     * @param address the address (max 30 characters)
     * @return this builder for method chaining
     */
    public ContactBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    /**
     * Builds and returns a new Contact instance with the configured values.
     * @return a new Contact object
     * @throws ContactValidationException if any field fails validation
     */
    public Contact build() {
        return new Contact(contactId, firstName, lastName, phone, address);
    }

    /**
     * Validates the current builder state without creating a Contact.
     * @return true if all fields are valid
     * @throws ContactValidationException if any field is invalid
     */
    public boolean isValid() {
        // Validate contactId
        if (contactId == null || contactId.length() > 10) {
            throw new ContactValidationException("contactId", "Invalid contact ID: must be non-null and max 10 characters");
        }
        // Validate firstName
        if (firstName == null || firstName.length() > 10) {
            throw new ContactValidationException("firstName", "Invalid first name: must be non-null and max 10 characters");
        }
        // Validate lastName
        if (lastName == null || lastName.length() > 10) {
            throw new ContactValidationException("lastName", "Invalid last name: must be non-null and max 10 characters");
        }
        // Validate phone
        if (phone == null || phone.length() != 10 || !phone.matches("\\d+")) {
            throw new ContactValidationException("phone", "Invalid phone number: must be exactly 10 digits");
        }
        // Validate address
        if (address == null || address.length() > 30) {
            throw new ContactValidationException("address", "Invalid address: must be non-null and max 30 characters");
        }
        return true;
    }
}
