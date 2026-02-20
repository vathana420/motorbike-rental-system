package kh.edu.paragoniu.mrs.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * OOP - ENCAPSULATION:
 * All fields are private, accessed via public getters/setters.
 */
@Entity
@Table(name = "motorbikes")
public class Motorbike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String brand;
    private String model;
    private String plateNumber;
    private String color;
    private Integer year;
    private BigDecimal pricePerDay;
    private String imageUrl;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        AVAILABLE, RENTED, MAINTENANCE
    }

    // ---- Constructors ----
    public Motorbike() {
        this.status = Status.AVAILABLE;
    }

    public Motorbike(String brand, String model, String plateNumber, String color,
                     Integer year, BigDecimal pricePerDay, String description) {
        this.brand = brand;
        this.model = model;
        this.plateNumber = plateNumber;
        this.color = color;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.description = description;
        this.status = Status.AVAILABLE;
    }

    // ---- OOP - ENCAPSULATION: Getters & Setters ----
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public BigDecimal getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(BigDecimal pricePerDay) { this.pricePerDay = pricePerDay; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public String getBrandModel() {
        return brand + " " + model;
    }
}