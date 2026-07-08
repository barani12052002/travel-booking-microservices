package com.barani.travel.controller;

import com.barani.travel.client.AdminClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    private final AdminClient adminClient;

    public AdminWebController(AdminClient adminClient) {
        this.adminClient = adminClient;
    }

    @GetMapping("/users")
    public String users(HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("ROLE"))) {
            return "redirect:/dashboard";
        }
        String token = "Bearer " + session.getAttribute("TOKEN");

        model.addAttribute("users",
                adminClient.getUsers(token));

        return "admin-dashboard";
    }
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        String token = "Bearer " + session.getAttribute("TOKEN");

        System.out.println("ROLE = " + session.getAttribute("ROLE"));
        System.out.println("TOKEN = " + token);

        try {
            var response = adminClient.dashboard(token);

            System.out.println(response);

            model.addAttribute("dashboard", response);

            return "admin-dashboard";

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
    @GetMapping("/bookings")
    public String bookings(HttpSession session,
                           Model model,
                           @RequestParam(defaultValue = "0") int page) {

        if (!"ADMIN".equals(session.getAttribute("ROLE"))) {
            return "redirect:/dashboard";
        }

        String token = "Bearer " + session.getAttribute("TOKEN");

        var bookings = adminClient.getBookings(token, page, 10);

        model.addAttribute("bookings", bookings.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookings.getTotalPages());

        return "admin-bookings";
    }

    @PostMapping("/bookings/cancel/{bookingReference}")
    public String cancelBooking(@PathVariable String bookingReference,
                                HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("ROLE"))) {
            return "redirect:/dashboard";
        }

        String token = "Bearer " + session.getAttribute("TOKEN");

        adminClient.cancelBooking(token, bookingReference);

        return "redirect:/admin/bookings";
    }
}