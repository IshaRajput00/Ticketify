package com.ticketing.ticketmanagement.repository;

import com.ticketing.ticketmanagement.entity.TicketMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketMessageRepository
        extends JpaRepository<TicketMessage, Long> {

    List<TicketMessage> findByTicketIdOrderBySentAtAsc(Long ticketId);
}
