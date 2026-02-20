package kh.edu.paragoniu.mrs.repository;

import kh.edu.paragoniu.mrs.entity.Booking;
import kh.edu.paragoniu.mrs.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByCustomer(Customer customer);
    List<Booking> findByStatus(Booking.Status status);
    long countByStatus(Booking.Status status);
}