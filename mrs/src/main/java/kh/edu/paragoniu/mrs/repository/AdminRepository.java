package kh.edu.paragoniu.mrs.repository;

import kh.edu.paragoniu.mrs.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}