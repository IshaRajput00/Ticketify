package com.ticketing.ticketmanagement.controller;

import com.ticketing.ticketmanagement.entity.Feedback;
import com.ticketing.ticketmanagement.entity.Ticket;
import com.ticketing.ticketmanagement.entity.TicketPriority;
import com.ticketing.ticketmanagement.entity.TicketStatus;
import com.ticketing.ticketmanagement.repository.FeedbackRepository;
import com.ticketing.ticketmanagement.repository.TicketRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    private final TicketRepository ticketRepository;
    private final FeedbackRepository feedbackRepository;

    public CustomerController(TicketRepository ticketRepository,
                              FeedbackRepository feedbackRepository) {
        this.ticketRepository = ticketRepository;
        this.feedbackRepository = feedbackRepository;
    }

    // ---------------- DASHBOARD ----------------

    @GetMapping("/dashboard")
    public String customerDashboard() {
        return "customer-dashboard";
    }

    // ---------------- VIEW MY TICKETS ----------------

    @GetMapping("/tickets")
    public String viewMyTickets(Model model) {
        model.addAttribute("tickets", ticketRepository.findAll());
        return "customer-tickets";
    }

    // ---------------- VIEW TICKET DETAILS ----------------

    @GetMapping("/ticket/view")
    public String viewTicketDetails(
            @RequestParam Long id,
            Model model) {

        Ticket ticket = ticketRepository.findById(id).orElse(null);

        if (ticket == null) {
            return "redirect:/customer/tickets";
        }

        model.addAttribute("ticket", ticket);
        return "customer-ticket-view";
    }

    // ---------------- CREATE TICKET ----------------

    @GetMapping("/ticket/create")
    public String showCreateTicketForm() {
        return "ticket-create";
    }

    @PostMapping("/ticket/create")
    public String createTicket(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam String category,
            @RequestParam TicketPriority priority,
            Authentication authentication) {

        Ticket ticket = new Ticket();
        ticket.setTitle(title);
        ticket.setDescription(description);
        ticket.setCategory(category);
        ticket.setPriority(priority);
        ticket.setStatus(TicketStatus.OPEN);

        // in-memory login → customer mapping future scope
        ticket.setCustomer(null);

        ticketRepository.save(ticket);

        return "redirect:/customer/dashboard";
    }

    // ---------------- FEEDBACK ----------------

    // Open feedback form (ONLY if RESOLVED)
    @GetMapping("/ticket/feedback")
    public String showFeedbackForm(
            @RequestParam Long id,
            Model model) {

        Ticket ticket = ticketRepository.findById(id).orElse(null);

        if (ticket == null || ticket.getStatus() != TicketStatus.RESOLVED) {
            return "redirect:/customer/tickets";
        }

        model.addAttribute("ticketId", id);
        return "customer-feedback";
    }

    // Save feedback
    @PostMapping("/ticket/feedback")
    public String saveFeedback(
            @RequestParam Long ticketId,
            @RequestParam int rating,
            @RequestParam String comment) {

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        if (ticket != null && ticket.getStatus() == TicketStatus.RESOLVED) {

            Feedback feedback = new Feedback();
            feedback.setRating(rating);
            feedback.setComment(comment);
            feedback.setTicket(ticket);

            feedbackRepository.save(feedback);
        }

        return "redirect:/customer/tickets";
    }
}
