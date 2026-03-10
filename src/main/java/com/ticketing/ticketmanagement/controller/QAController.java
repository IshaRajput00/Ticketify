package com.ticketing.ticketmanagement.controller;

import com.ticketing.ticketmanagement.entity.TicketStatus;
import com.ticketing.ticketmanagement.repository.FeedbackRepository;
import com.ticketing.ticketmanagement.repository.TicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/qa")
public class QAController {

    private final TicketRepository ticketRepository;
    private final FeedbackRepository feedbackRepository;

    public QAController(TicketRepository ticketRepository,
                        FeedbackRepository feedbackRepository) {
        this.ticketRepository = ticketRepository;
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping("/dashboard")
    public String qaDashboard(Model model) {

        model.addAttribute("tickets",
                ticketRepository.findAll()
                        .stream()
                        .filter(t -> t.getStatus() == TicketStatus.RESOLVED)
                        .toList());

        model.addAttribute("feedbacks", feedbackRepository.findAll());

        return "qa-dashboard";
    }
}
