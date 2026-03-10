package com.ticketing.ticketmanagement.controller;

import com.ticketing.ticketmanagement.entity.Ticket;
import com.ticketing.ticketmanagement.entity.TicketMessage;
import com.ticketing.ticketmanagement.repository.TicketMessageRepository;
import com.ticketing.ticketmanagement.repository.TicketRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class TicketChatController {

    private final TicketRepository ticketRepository;
    private final TicketMessageRepository messageRepository;

    public TicketChatController(TicketRepository ticketRepository,
                                TicketMessageRepository messageRepository) {
        this.ticketRepository = ticketRepository;
        this.messageRepository = messageRepository;
    }

    // ---------------- OPEN CHAT PAGE ----------------

    @GetMapping("/ticket/chat")
    public String openChat(
            @RequestParam Long ticketId,
            Authentication authentication,
            Model model) {

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        if (ticket == null) {
            return "redirect:/redirect";
        }

        String role = "";

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            role = authority.getAuthority().replace("ROLE_", "");
            break;
        }

        model.addAttribute("ticketId", ticketId);
        model.addAttribute("role", role);
        model.addAttribute(
                "messages",
                messageRepository.findByTicketIdOrderBySentAtAsc(ticketId)
        );

        return "ticket-chat";
    }

    // ---------------- SAVE MESSAGE ----------------

    @PostMapping("/ticket/chat")
    public String saveMessage(
            @RequestParam Long ticketId,
            @RequestParam String message,
            @RequestParam String role) {

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        if (ticket != null && message != null && !message.trim().isEmpty()) {

            TicketMessage tm = new TicketMessage();
            tm.setTicket(ticket);
            tm.setMessage(message);
            tm.setSenderRole(role);
            tm.setSentAt(LocalDateTime.now());

            messageRepository.save(tm);
        }

        return "redirect:/ticket/chat?ticketId=" + ticketId;
    }
}
