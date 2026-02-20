package kh.edu.paragoniu.mrs.service;

/**
 * OOP - ABSTRACTION:
 * Interface that defines the contract for any Person-based service.
 * Hides implementation details from the caller.
 */
public interface PersonService {
    boolean existsByEmail(String email);
}