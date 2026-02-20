package kh.edu.paragoniu.mrs.entity;

import jakarta.persistence.*;

/**
 * OOP - ABSTRACTION & INHERITANCE:
 * Abstract base class that defines shared fields for Customer and Admin.
 * Cannot be instantiated directly.
 */
@MappedSuperclass
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;

    // ---- Constructors ----
    public Person() {}

    public Person(String firstName, String lastName, String email, String password, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
    }

    /**
     * OOP - POLYMORPHISM (Abstract Method):
     * Each subclass must implement its own role label.
     */
    public abstract String getRole();

    /**
     * OOP - POLYMORPHISM (Abstract Method):
     * Each subclass returns its own dashboard redirect URL.
     */
    public abstract String getDashboardUrl();

    // ---- OOP - ENCAPSULATION: Private fields with public getters/setters ----

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}