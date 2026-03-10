package com.ticketing.ticketmanagement.repository;

import com.ticketing.ticketmanagement.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
