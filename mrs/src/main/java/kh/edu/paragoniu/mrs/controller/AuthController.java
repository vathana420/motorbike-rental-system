package kh.edu.paragoniu.mrs.controller;

import jakarta.servlet.http.HttpSession;
import kh.edu.paragoniu.mrs.entity.Admin;
import kh.edu.paragoniu.mrs.entity.Customer;
import kh.edu.paragoniu.mrs.service.AdminService;
import kh.edu.paragoniu.mrs.service.CustomerService;
import kh.edu.paragoniu.mrs.service.MotorbikeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    private final CustomerService customerService;
    private final AdminService adminService;
    private final MotorbikeService motorbikeService;

    public AuthController(CustomerService customerService,
                          AdminService adminService,
                          MotorbikeService motorbikeService) {
        this.customerService = customerService;
        this.adminService = adminService;
        this.motorbikeService = motorbikeService;
    }

    // ---- Landing Page ----
    @GetMapping("/")
    public String landing(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        if ("ADMIN".equals(role)) return "redirect:/admin/dashboard";
        if ("CUSTOMER".equals(role)) return "redirect:/customer/home";

        // Pass available motorbikes to show on landing page
        model.addAttribute("motorbikes", motorbikeService.getAvailableMotorbikes());
        return "landing";
    }

    // ---- Login ----
    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model) {
        if (session.getAttribute("role") != null) return "redirect:/";
        model.addAttribute("error", null);
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam String loginAs,
                        HttpSession session,
                        Model model) {

        if ("ADMIN".equals(loginAs)) {
            Optional<Admin> admin = adminService.login(email, password);
            if (admin.isPresent()) {
                session.setAttribute("userId", admin.get().getId());
                session.setAttribute("userName", admin.get().getFullName());
                session.setAttribute("role", "ADMIN");
                return "redirect:/admin/dashboard";
            }
        } else {
            Optional<Customer> customer = customerService.login(email, password);
            if (customer.isPresent()) {
                session.setAttribute("userId", customer.get().getId());
                session.setAttribute("userName", customer.get().getFullName());
                session.setAttribute("role", "CUSTOMER");
                return "redirect:/customer/home";
            }
        }

        model.addAttribute("error", "Invalid email or password. Please try again.");
        return "auth/login";
    }

    // ---- Register ----
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Customer customer, Model model) {
        if (customerService.existsByEmail(customer.getEmail())) {
            model.addAttribute("customer", customer);
            model.addAttribute("error", "Email already registered. Please use a different email.");
            return "auth/register";
        }
        customerService.register(customer);
        return "redirect:/login?registered=true";
    }

    // ---- Logout ----
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}