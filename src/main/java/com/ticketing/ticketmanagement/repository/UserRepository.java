package com.ticketing.ticketmanagement.repository;

import com.ticketing.ticketmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
