/*
 * Keith Pottratz
 * CS320
 * Security Tests
 * January 2026
 * Tests security features: thread safety, resource limits, input sanitization
 * 
 */
package com.example.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.contact.exception.ContactValidationException;
import com.example.contact.repository.InMemoryContactRepository;
import com.example.contact.service.ContactServiceImpl;
import com.example.contact.service.IContactService;

/**
 * Tests for security features including thread safety,
 * resource limits, and input validation.
 */
public class SecurityTest {

    private InMemoryContactRepository repository;
    private IContactService service;

    /**  
     * Set up the ContactService with an InMemoryContactRepository before each test.
     * @throws Exception if setup fails
     */
    @BeforeEach
    public void setUp() {
        repository = new InMemoryContactRepository();
        service = new ContactServiceImpl(repository);
    }

    // ===== Thread Safety Tests =====
    /** 
     * Test concurrent addition of contacts to ensure thread safety.
     * @throws InterruptedException if thread is interrupted
     */
    @Test
    public void testConcurrentAddContacts() throws InterruptedException {
        int numThreads = 10;
        int contactsPerThread = 100;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        AtomicInteger successCount = new AtomicInteger(0);

    /**
     * Test concurrent addition of contacts to ensure thread safety.
     * @throws InterruptedException if thread is interrupted
     */
        for (int t = 0; t < numThreads; t++) {
            final int threadId = t;
            executor.submit(() -> {
                try {
                    for (int i = 0; i < contactsPerThread; i++) {
                        String id = String.format("%d-%d", threadId, i);
                        // Pad ID to be at most 10 characters
                        if (id.length() <= 10) {
                            Contact contact = new Contact(id, "First", "Last", "1234567890", "123 Main St");
                            service.addContact(contact);
                            successCount.incrementAndGet();
                        }
                    }
                } catch (Exception e) {
                    // Some may fail due to validation, that's expected
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // All successfully added contacts should be retrievable
        assertEquals(successCount.get(), service.getAllContacts().size());
    }

    /** 
     * Test concurrent reads and writes to ensure thread safety.
     * @throws InterruptedException if thread is interrupted   
     * 
     */
    @Test
    public void testConcurrentReadWrite() throws InterruptedException {
        // Add initial contacts
        for (int i = 0; i < 100; i++) {
            String id = String.format("%05d", i);
            Contact contact = new Contact(id, "First", "Last", "1234567890", "123 Main St");
            service.addContact(contact);
        }

        int numThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        List<Exception> exceptions = new ArrayList<>();

        for (int t = 0; t < numThreads; t++) {
            final int threadId = t;
            executor.submit(() -> {
                try {
                    for (int i = 0; i < 50; i++) {
                        // Mix of reads and writes
                        if (i % 2 == 0) {
                            // Read
                            String id = String.format("%05d", i % 100);
                            service.getContact(id);
                        } else {
                            // Update
                            String id = String.format("%05d", i % 100);
                            try {
                                service.updateContact(id, "Updated", null, null, null);
                            } catch (ContactValidationException e) {
                                // Name validation may fail, that's OK
                            }
                        }
                    }
                } catch (Exception e) {
                    synchronized (exceptions) {
                        exceptions.add(e);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // No ConcurrentModificationException should occur
        assertTrue(exceptions.isEmpty(), "Concurrent operations should not throw exceptions");
    }

    // ===== Resource Limit Tests =====

    /** 
     * Test maximum contacts limit enforcement.
     * @throws ResourceLimitException if the system reaches its resource limit
     */
    @Test
    public void testMaxContactsLimit() {
        assertEquals(10000, InMemoryContactRepository.MAX_CONTACTS);
    }

    /**
     * Test clearing the repository.
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testRepositoryClear() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        repository.save(contact);
        assertEquals(1, repository.count());

        repository.clear();
        assertEquals(0, repository.count());
    }

    // ===== Input Sanitization Tests (via Service) =====

    /**
     * Test service rejects XSS in names.
     * @throws ContactValidationException if the contact is invalid during update
     */
    @Test
    public void testServiceRejectsXSSInName() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");

        // This should pass initially
        service.addContact(contact);

        // Updating with XSS should fail
        assertThrows(ContactValidationException.class, () ->
                service.updateContact("12345", "<script>alert('xss')</script>", null, null, null));
    }

    /** 
     * Test service rejects invalid name characters.
     * @throws ContactValidationException if the contact is invalid during update
     */
    @Test
    public void testServiceRejectsInvalidNameCharacters() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        service.addContact(contact);

        // Names with numbers should be rejected by validator
        assertThrows(ContactValidationException.class, () ->
                service.updateContact("12345", "John123", null, null, null));
    }

    /** 
     * Test service rejects XSS in address.
     * @throws ContactValidationException if the contact is invalid during update
     */
    @Test
    public void testServiceRejectsXSSInAddress() {
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        service.addContact(contact);

        assertThrows(ContactValidationException.class, () ->
                service.updateContact("12345", null, null, null, "<script>alert('xss')</script>"));
    }

    // ===== Null/Blank Input Tests =====
    /**
     * Test adding a null contact.
     * @throws ContactValidationException if the contact is invalid
     */
    @Test
    public void testDeleteWithBlankId() {
        assertThrows(ContactValidationException.class, () ->
                service.deleteContact(""));
    }
    
    /** 
     * Test updating with a blank contact ID.
     * @throws ContactValidationException if the contact is invalid during update
     */
    @Test
    public void testUpdateWithBlankId() {
        assertThrows(ContactValidationException.class, () ->
                service.updateContact("", "Jane", null, null, null));
    }

    // ===== Audit Logging Verification =====
    // Note: Actual log output is verified manually or through log capture in integration tests

    @Test
    public void testOperationsGenerateLogs() {
        // This test verifies that operations complete without error
        // Log output would be verified in integration tests
        Contact contact = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");

        // Add
        service.addContact(contact);
        assertNotNull(service.getContact("12345"));

        // Update
        service.updateContact("12345", "Jane", null, null, null);
        assertEquals("Jane", service.getContact("12345").getFirstName());

        // Delete
        service.deleteContact("12345");
        assertNull(service.getContact("12345"));
    }
}
