package kh.edu.paragoniu.mrs.repository;

import kh.edu.paragoniu.mrs.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}