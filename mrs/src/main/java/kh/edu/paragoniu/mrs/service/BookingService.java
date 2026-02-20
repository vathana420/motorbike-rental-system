package kh.edu.paragoniu.mrs.service;

import kh.edu.paragoniu.mrs.entity.Booking;
import kh.edu.paragoniu.mrs.entity.Customer;
import kh.edu.paragoniu.mrs.entity.Motorbike;
import kh.edu.paragoniu.mrs.repository.BookingRepository;
import kh.edu.paragoniu.mrs.repository.MotorbikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MotorbikeRepository motorbikeRepository;

    public BookingService(BookingRepository bookingRepository, MotorbikeRepository motorbikeRepository) {
        this.bookingRepository = bookingRepository;
        this.motorbikeRepository = motorbikeRepository;
    }

    // ---- CRUD: Create ----
    public Booking createBooking(Booking booking) {
        booking.calculateTotalPrice();
        // Mark motorbike as RENTED
        Motorbike motorbike = booking.getMotorbike();
        motorbike.setStatus(Motorbike.Status.RENTED);
        motorbikeRepository.save(motorbike);
        return bookingRepository.save(booking);
    }

    // ---- CRUD: Read ----
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByCustomer(Customer customer) {
        return bookingRepository.findByCustomer(customer);
    }

    public Optional<Booking> getBookingById(Integer id) {
        return bookingRepository.findById(id);
    }

    // ---- CRUD: Update ----
    public Booking updateBookingStatus(Integer id, Booking.Status status) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);

        // If completed or cancelled, mark motorbike as available again
        if (status == Booking.Status.COMPLETED || status == Booking.Status.CANCELLED) {
            Motorbike motorbike = booking.getMotorbike();
            motorbike.setStatus(Motorbike.Status.AVAILABLE);
            motorbikeRepository.save(motorbike);
        }
        return bookingRepository.save(booking);
    }

    // ---- CRUD: Delete ----
    public void deleteBooking(Integer id) {
        bookingRepository.deleteById(id);
    }

    public long countAll() { return bookingRepository.count(); }
    public long countPending() { return bookingRepository.countByStatus(Booking.Status.PENDING); }
    public long countConfirmed() { return bookingRepository.countByStatus(Booking.Status.CONFIRMED); }
}