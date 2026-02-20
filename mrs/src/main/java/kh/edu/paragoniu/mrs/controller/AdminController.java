package kh.edu.paragoniu.mrs.controller;

import jakarta.servlet.http.HttpSession;
import kh.edu.paragoniu.mrs.entity.Booking;
import kh.edu.paragoniu.mrs.entity.Motorbike;
import kh.edu.paragoniu.mrs.service.BookingService;
import kh.edu.paragoniu.mrs.service.CustomerService;
import kh.edu.paragoniu.mrs.service.MotorbikeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final MotorbikeService motorbikeService;
    private final BookingService bookingService;
    private final CustomerService customerService;

    public AdminController(MotorbikeService motorbikeService,
                           BookingService bookingService,
                           CustomerService customerService) {
        this.motorbikeService = motorbikeService;
        this.bookingService = bookingService;
        this.customerService = customerService;
    }

    // Guard: check admin session
    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("role"));
    }

    // =============================================
    //  DASHBOARD
    // =============================================
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";

        model.addAttribute("totalMotorbikes", motorbikeService.countAll());
        model.addAttribute("availableMotorbikes", motorbikeService.countAvailable());
        model.addAttribute("totalCustomers", customerService.countCustomers());
        model.addAttribute("totalBookings", bookingService.countAll());
        model.addAttribute("pendingBookings", bookingService.countPending());
        model.addAttribute("confirmedBookings", bookingService.countConfirmed());
        model.addAttribute("recentBookings", bookingService.getAllBookings());
        model.addAttribute("userName", session.getAttribute("userName"));
        return "admin/dashboard";
    }

    // =============================================
    //  MOTORBIKE MANAGEMENT (CRUD)
    // =============================================
    @GetMapping("/motorbikes")
    public String listMotorbikes(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("motorbikes", motorbikeService.getAllMotorbikes());
        model.addAttribute("userName", session.getAttribute("userName"));
        return "admin/motorbikes";
    }

    @GetMapping("/motorbikes/add")
    public String addMotorbikeForm(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("motorbike", new Motorbike());
        model.addAttribute("statuses", Motorbike.Status.values());
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("isEdit", false);
        return "admin/motorbike-form";
    }

    @PostMapping("/motorbikes/add")
    public String addMotorbike(@ModelAttribute Motorbike motorbike, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        motorbikeService.addMotorbike(motorbike);
        return "redirect:/admin/motorbikes";
    }

    @GetMapping("/motorbikes/edit/{id}")
    public String editMotorbikeForm(@PathVariable Integer id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        motorbikeService.getMotorbikeById(id).ifPresent(m -> model.addAttribute("motorbike", m));
        model.addAttribute("statuses", Motorbike.Status.values());
        model.addAttribute("userName", session.getAttribute("userName"));
        model.addAttribute("isEdit", true);
        return "admin/motorbike-form";
    }

    @PostMapping("/motorbikes/edit/{id}")
    public String editMotorbike(@PathVariable Integer id,
                                @ModelAttribute Motorbike motorbike,
                                HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        motorbike.setId(id);
        motorbikeService.updateMotorbike(motorbike);
        return "redirect:/admin/motorbikes";
    }

    @GetMapping("/motorbikes/delete/{id}")
    public String deleteMotorbike(@PathVariable Integer id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        motorbikeService.deleteMotorbike(id);
        return "redirect:/admin/motorbikes";
    }

    // =============================================
    //  BOOKING MANAGEMENT
    // =============================================
    @GetMapping("/bookings")
    public String listBookings(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("bookings", bookingService.getAllBookings());
        model.addAttribute("userName", session.getAttribute("userName"));
        return "admin/bookings";
    }

    @PostMapping("/bookings/status/{id}")
    public String updateBookingStatus(@PathVariable Integer id,
                                      @RequestParam String status,
                                      HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        bookingService.updateBookingStatus(id, Booking.Status.valueOf(status));
        return "redirect:/admin/bookings";
    }

    @GetMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable Integer id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        bookingService.deleteBooking(id);
        return "redirect:/admin/bookings";
    }

    // =============================================
    //  CUSTOMER MANAGEMENT
    // =============================================
    @GetMapping("/customers")
    public String listCustomers(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("userName", session.getAttribute("userName"));
        return "admin/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id, HttpSession session) {
        if (!isAdmin(session)) return "redirect:/login";
        customerService.deleteCustomer(id);
        return "redirect:/admin/customers";
    }
}