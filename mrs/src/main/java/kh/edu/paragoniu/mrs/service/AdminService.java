package kh.edu.paragoniu.mrs.service;

import kh.edu.paragoniu.mrs.entity.Admin;
import kh.edu.paragoniu.mrs.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * OOP - ABSTRACTION:
 * Implements PersonService interface for Admin.
 */
@Service
public class AdminService implements PersonService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // ---- CRUD: Create ----
    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // ---- CRUD: Read ----
    public Optional<Admin> login(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password);
    }

    public Optional<Admin> getAdminById(Integer id) {
        return adminRepository.findById(id);
    }

    // ---- OOP - ABSTRACTION: Implementing interface method ----
    @Override
    public boolean existsByEmail(String email) {
        return adminRepository.existsByEmail(email);
    }
}