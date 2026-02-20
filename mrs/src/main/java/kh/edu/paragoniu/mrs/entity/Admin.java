package kh.edu.paragoniu.mrs.entity;

import jakarta.persistence.*;

/**
 * OOP - INHERITANCE:
 * Admin extends Person, inheriting all shared fields.
 * OOP - POLYMORPHISM:
 * Overrides getRole() and getDashboardUrl() differently from Customer.
 */
@Entity
@Table(name = "admins")
public class Admin extends Person {

    private String adminCode;

    // ---- Constructors ----
    public Admin() {}

    public Admin(String firstName, String lastName, String email, String password,
                 String gender, String adminCode) {
        super(firstName, lastName, email, password, gender);
        this.adminCode = adminCode;
    }

    // ---- OOP - POLYMORPHISM: Override abstract methods ----
    @Override
    public String getRole() {
        return "ADMIN";
    }

    @Override
    public String getDashboardUrl() {
        return "/admin/dashboard";
    }

    // ---- OOP - ENCAPSULATION ----
    public String getAdminCode() { return adminCode; }
    public void setAdminCode(String adminCode) { this.adminCode = adminCode; }
}