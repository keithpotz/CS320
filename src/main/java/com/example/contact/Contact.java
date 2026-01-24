/*
 * Keith Pottratz
 * CS320
 * Java Contact
 * December 8, 2024
 * Updated: January 2026 - Added custom exception handling
 * 
 */
package com.example.contact;

import com.example.contact.exception.ContactValidationException;

/**
 * Represents a contact with validated fields.
 * The contactId is immutable once set in the constructor.
 */
public class Contact {
    private final String contactId; // Immutable field for unique contact ID
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    /**
     * constructs a new Contact with the specified field values.
     * @param contactId the unique identifier for the contact (max 10 characters, non-null)
     * @param firstName the first name of the contact (max 10 characters, non-null)
     * @param lastName the last name of the contact (max 10 characters, non-null)
     * @param phone the phone number of the contact (exactly 10 digits, non-null)
     * @param address the address of the contact (max 30 characters, non-null)
     * @throws ContactValidationException if any of the fields are invalid
     */
    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        if (contactId == null || contactId.length() > 10) {
            throw new ContactValidationException("contactId", "Invalid contact ID: must be non-null and max 10 characters");
        }
        if (firstName == null || firstName.length() > 10) {
            throw new ContactValidationException("firstName", "Invalid first name: must be non-null and max 10 characters");
        }
        if (lastName == null || lastName.length() > 10) {
            throw new ContactValidationException("lastName", "Invalid last name: must be non-null and max 10 characters");
        }
        if (phone == null || phone.length() != 10 || !phone.matches("\\d+")) {
            throw new ContactValidationException("phone", "Invalid phone number: must be exactly 10 digits");
        }
        if (address == null || address.length() > 30) {
            throw new ContactValidationException("address", "Invalid address: must be non-null and max 30 characters");
        }

        /**
         * Returns th eunique identifier for this contact.
         * the contact ID is immutable and set during construction. It cannot be changed after construction
         * @return the contact ID
         */
        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    // Getter for contactId (immutable field)
    public String getContactId() {
        return contactId;
    }

    /**
     * Returns the contacts first name.
     * @return the first name of the contact
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the contact's first name.
     * @return the first name of the contact
     * @throws ContactValidationException if the new first name is invalid
     */
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.length() > 10) {
            throw new ContactValidationException("firstName", "Invalid first name: must be non-null and max 10 characters");
        }
        this.firstName = firstName;
    }

    /**
     * Returns the contacts last name.
     * @return the last name of the contact
     */
    public String getLastName() {
        return lastName;
    }

    /** 
     * Sets the contacts last name.
     * @return the last name of the contact
     * @throws ContactValidationException if the new last name is invalid
     */
    public void setLastName(String lastName) {
        if (lastName == null || lastName.length() > 10) {
            throw new ContactValidationException("lastName", "Invalid last name: must be non-null and max 10 characters");
        }
        this.lastName = lastName;
    }

    /**
     * Returns the contacts phone number.
     * @return the phone number of the contact
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the contacts phone number.
     * @return the phone number of the contact
     * @throws ContactValidationException if the new phone number is invalid
     */

    public void setPhone(String phone) {
        if (phone == null || phone.length() != 10 || !phone.matches("\\d+")) {
            throw new ContactValidationException("phone", "Invalid phone number: must be exactly 10 digits");
        }
        this.phone = phone;
    }

    /**
     * Returns the contacts address.
     * @return the address of the contact
     */
    public String getAddress() {
        return address;
    }
    /**
     * Sets the contacts address.
     * @return the address of the contact
     * @throws ContactValidationException if the new address is invalid
     */

    public void setAddress(String address) {
        if (address == null || address.length() > 30) {
            throw new ContactValidationException("address", "Invalid address: must be non-null and max 30 characters");
        }
        this.address = address;
    }
}
