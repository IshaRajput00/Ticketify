package com.ticketing.ticketmanagement.repository;

import com.ticketing.ticketmanagement.entity.TicketCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCaseRepository extends JpaRepository<TicketCase, Long> {
}
