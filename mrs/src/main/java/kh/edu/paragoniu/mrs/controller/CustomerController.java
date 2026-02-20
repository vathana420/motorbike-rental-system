package kh.edu.paragoniu.mrs.controller;

import jakarta.servlet.http.HttpSession;
import kh.edu.paragoniu.mrs.entity.Booking;
import kh.edu.paragoniu.mrs.entity.Customer;
import kh.edu.paragoniu.mrs.entity.Motorbike;
import kh.edu.paragoniu.mrs.service.BookingService;
import kh.edu.paragoniu.mrs.service.CustomerService;
import kh.edu.paragoniu.mrs.service.MotorbikeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final MotorbikeService motorbikeService;
    private final BookingService bookingService;
    private final CustomerService customerService;

    public CustomerController(MotorbikeService motorbikeService,
                              BookingService bookingService,
                              CustomerService customerService) {
        this.motorbikeService = motorbikeService;
        this.bookingService = bookingService;
        this.customerService = customerService;
    }

    // Guard: check customer session
    private boolean isCustomer(HttpSession session) {
        return "CUSTOMER".equals(session.getAttribute("role"));
    }

    // =============================================
    //  HOME
    // =============================================
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        if (!isCustomer(session)) return "redirect:/login";
        model.addAttribute("availableMotorbikes", motorbikeService.getAvailableMotorbikes());
        model.addAttribute("userName", session.getAttribute("userName"));
        return "customer/home";
    }

    // =============================================
    //  BROWSE MOTORBIKES
    // =============================================
    @GetMapping("/browse")
    public String browse(HttpSession session, Model model) {
        if (!isCustomer(session)) return "redirect:/login";
        model.addAttribute("motorbikes", motorbikeService.getAvailableMotorbikes());
        model.addAttribute("userName", session.getAttribute("userName"));
        return "customer/browse";
    }

    // =============================================
    //  BOOK A MOTORBIKE
    // =============================================
    @GetMapping("/book/{motorbikeId}")
    public String bookForm(@PathVariable Integer motorbikeId, HttpSession session, Model model) {
        if (!isCustomer(session)) return "redirect:/login";
        Optional<Motorbike> motorbike = motorbikeService.getMotorbikeById(motorbikeId);
        if (motorbike.isEmpty()) return "redirect:/customer/browse";

        model.addAttribute("motorbike", motorbike.get());
        model.addAttribute("today", LocalDate.now().toString());
        model.addAttribute("userName", session.getAttribute("userName"));
        return "customer/book";
    }

    @PostMapping("/book/{motorbikeId}")
    public String book(@PathVariable Integer motorbikeId,
                       @RequestParam String startDate,
                       @RequestParam String endDate,
                       HttpSession session) {
        if (!isCustomer(session)) return "redirect:/login";

        Integer customerId = (Integer) session.getAttribute("userId");
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        Optional<Motorbike> motorbike = motorbikeService.getMotorbikeById(motorbikeId);

        if (customer.isPresent() && motorbike.isPresent()) {
            Booking booking = new Booking(
                    customer.get(),
                    motorbike.get(),
                    LocalDate.parse(startDate),
                    LocalDate.parse(endDate)
            );
            bookingService.createBooking(booking);
        }
        return "redirect:/customer/my-bookings";
    }

    // =============================================
    //  MY BOOKINGS
    // =============================================
    @GetMapping("/my-bookings")
    public String myBookings(HttpSession session, Model model) {
        if (!isCustomer(session)) return "redirect:/login";

        Integer customerId = (Integer) session.getAttribute("userId");
        customerService.getCustomerById(customerId).ifPresent(customer -> {
            model.addAttribute("bookings", bookingService.getBookingsByCustomer(customer));
        });
        model.addAttribute("userName", session.getAttribute("userName"));
        return "customer/my-bookings";
    }

    // =============================================
    //  CANCEL BOOKING
    // =============================================
    @GetMapping("/bookings/cancel/{id}")
    public String cancelBooking(@PathVariable Integer id, HttpSession session) {
        if (!isCustomer(session)) return "redirect:/login";
        bookingService.updateBookingStatus(id, Booking.Status.CANCELLED);
        return "redirect:/customer/my-bookings";
    }
}