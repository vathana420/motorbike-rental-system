package kh.edu.paragoniu.mrs.service;

import kh.edu.paragoniu.mrs.entity.Customer;
import kh.edu.paragoniu.mrs.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * OOP - ABSTRACTION:
 * Implements PersonService interface, hiding logic from controllers.
 */
@Service
public class CustomerService implements PersonService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // ---- CRUD: Create ----
    public Customer register(Customer customer) {
        return customerRepository.save(customer);
    }

    // ---- CRUD: Read ----
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> login(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password);
    }

    // ---- CRUD: Update ----
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // ---- CRUD: Delete ----
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    // ---- OOP - ABSTRACTION: Implementing interface method ----
    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    public long countCustomers() {
        return customerRepository.count();
    }
}