package kh.edu.paragoniu.mrs.entity;

import jakarta.persistence.*;
import java.util.List;

/**
 * OOP - INHERITANCE:
 * Customer extends Person, inheriting all shared fields.
 * OOP - POLYMORPHISM:
 * Overrides getRole() and getDashboardUrl() with its own behavior.
 */
@Entity
@Table(name = "customers")
public class Customer extends Person {

    private String phone;
    private String address;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    // ---- Constructors ----
    public Customer() {}

    public Customer(String firstName, String lastName, String email, String password,
                    String gender, String phone, String address) {
        super(firstName, lastName, email, password, gender);
        this.phone = phone;
        this.address = address;
    }

    // ---- OOP - POLYMORPHISM: Override abstract methods ----
    @Override
    public String getRole() {
        return "CUSTOMER";
    }

    @Override
    public String getDashboardUrl() {
        return "/customer/home";
    }

    // ---- OOP - ENCAPSULATION ----
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }
}