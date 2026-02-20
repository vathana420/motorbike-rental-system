package kh.edu.paragoniu.mrs.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * OOP - ENCAPSULATION:
 * All fields private, accessed via getters/setters.
 * Also demonstrates relationships (Many-to-One).
 */
@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "motorbike_id")
    private Motorbike motorbike;

    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate createdAt;

    public enum Status {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }

    // ---- Constructors ----
    public Booking() {
        this.status = Status.PENDING;
        this.createdAt = LocalDate.now();
    }

    public Booking(Customer customer, Motorbike motorbike, LocalDate startDate, LocalDate endDate) {
        this.customer = customer;
        this.motorbike = motorbike;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = Status.PENDING;
        this.createdAt = LocalDate.now();
        calculateTotalPrice();
    }

    // Auto-calculate total price based on days and price per day
    public void calculateTotalPrice() {
        if (startDate != null && endDate != null && motorbike != null) {
            long days = ChronoUnit.DAYS.between(startDate, endDate);
            if (days <= 0) days = 1;
            this.totalPrice = motorbike.getPricePerDay().multiply(BigDecimal.valueOf(days));
        }
    }

    // ---- OOP - ENCAPSULATION: Getters & Setters ----
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Motorbike getMotorbike() { return motorbike; }
    public void setMotorbike(Motorbike motorbike) { this.motorbike = motorbike; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    public long getRentalDays() {
        if (startDate == null || endDate == null) return 0;
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return days <= 0 ? 1 : days;
    }
}