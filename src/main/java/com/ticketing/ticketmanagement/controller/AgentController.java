package com.ticketing.ticketmanagement.controller;

import com.ticketing.ticketmanagement.entity.Comment;
import com.ticketing.ticketmanagement.entity.Ticket;
import com.ticketing.ticketmanagement.entity.TicketStatus;
import com.ticketing.ticketmanagement.repository.CommentRepository;
import com.ticketing.ticketmanagement.repository.TicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/agent")
public class AgentController {

    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;

    public AgentController(TicketRepository ticketRepository,
                           CommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
    }

    // ---------------- DASHBOARD ----------------

    @GetMapping("/dashboard")
    public String agentDashboard() {
        return "agent-dashboard";
    }

    // ---------------- VIEW TICKETS ----------------

    @GetMapping("/tickets")
    public String viewTickets(Model model) {
        model.addAttribute("tickets", ticketRepository.findAll());
        return "agent-tickets";
    }

    // ---------------- ADD INTERNAL NOTE ----------------

    @GetMapping("/ticket/note")
    public String showAddNotePage(@RequestParam Long id, Model model) {
        model.addAttribute("ticketId", id);
        return "agent-add-note";
    }

    @PostMapping("/ticket/note")
    public String saveInternalNote(
            @RequestParam Long ticketId,
            @RequestParam String message) {

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        if (ticket != null) {
            Comment comment = new Comment();
            comment.setMessage(message);
            comment.setInternalNote(true);
            comment.setTicket(ticket);

            commentRepository.save(comment);
        }

        return "redirect:/agent/ticket/update?id=" + ticketId;
    }

    // ---------------- UPDATE STATUS + VIEW NOTES ----------------

    @GetMapping("/ticket/update")
    public String showUpdateStatusPage(
            @RequestParam Long id,
            Model model) {

        Ticket ticket = ticketRepository.findById(id).orElse(null);

        model.addAttribute("ticket", ticket);

        // 🔥 INTERNAL NOTES DIKHANE KE LIYE
        model.addAttribute(
                "notes",
                commentRepository.findByTicketIdAndInternalNoteTrue(id)
        );

        return "agent-update-ticket";
    }

    @PostMapping("/ticket/update")
    public String updateTicketStatus(
            @RequestParam Long id,
            @RequestParam TicketStatus status) {

        Ticket ticket = ticketRepository.findById(id).orElse(null);

        if (ticket != null) {
            ticket.setStatus(status);
            ticketRepository.save(ticket);
        }

        return "redirect:/agent/tickets";
    }
}
