package com.ticketing.ticketmanagement.controller;

import com.ticketing.ticketmanagement.entity.Ticket;
import com.ticketing.ticketmanagement.entity.TicketStatus;
import com.ticketing.ticketmanagement.repository.TicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/supervisor")
public class SupervisorController {

    private final TicketRepository ticketRepository;

    public SupervisorController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/dashboard")
    public String supervisorDashboard(Model model) {
        model.addAttribute("tickets", ticketRepository.findAll());
        return "supervisor-dashboard";
    }



    @PostMapping("/assign")
    public String assignTicket(@RequestParam Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        if (ticket != null) {
            ticket.setStatus(TicketStatus.IN_PROGRESS);
            ticketRepository.save(ticket);
        }

        return "redirect:/supervisor/dashboard";
    }
}
