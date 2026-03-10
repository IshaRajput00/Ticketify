package com.ticketing.ticketmanagement.repository;

import com.ticketing.ticketmanagement.entity.CaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseRepository extends JpaRepository<CaseEntity, Long> {
}
