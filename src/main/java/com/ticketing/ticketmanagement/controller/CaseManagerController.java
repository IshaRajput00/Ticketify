package com.ticketing.ticketmanagement.controller;

import com.ticketing.ticketmanagement.entity.CaseEntity;
import com.ticketing.ticketmanagement.entity.Ticket;
import com.ticketing.ticketmanagement.entity.TicketCase;
import com.ticketing.ticketmanagement.entity.TicketStatus;
import com.ticketing.ticketmanagement.repository.CaseRepository;
import com.ticketing.ticketmanagement.repository.TicketCaseRepository;
import com.ticketing.ticketmanagement.repository.TicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ticketing.ticketmanagement.entity.CaseStatus;


@Controller
@RequestMapping("/case")
public class CaseManagerController {

    private final TicketRepository ticketRepository;
    private final CaseRepository caseRepository;
    private final TicketCaseRepository ticketCaseRepository;

    public CaseManagerController(TicketRepository ticketRepository,
                                 CaseRepository caseRepository,
                                 TicketCaseRepository ticketCaseRepository) {
        this.ticketRepository = ticketRepository;
        this.caseRepository = caseRepository;
        this.ticketCaseRepository = ticketCaseRepository;
    }

    @GetMapping("/dashboard")
    public String caseDashboard(Model model) {

        model.addAttribute("tickets",
                ticketRepository.findAll()
                        .stream()
                        .filter(t -> t.getStatus() == TicketStatus.RESOLVED)
                        .toList());

        return "case-dashboard";
    }


    @PostMapping("/convert")
    public String convertToCase(@RequestParam Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);

        if (ticket != null) {

            CaseEntity caseEntity = new CaseEntity();
            caseEntity.setStatus(CaseStatus.OPEN);

            caseRepository.save(caseEntity);

            TicketCase tc = new TicketCase();
            tc.setTicket(ticket);
            tc.setCaseEntity(caseEntity);
            ticketCaseRepository.save(tc);
        }

        return "redirect:/case/dashboard";
    }
}
