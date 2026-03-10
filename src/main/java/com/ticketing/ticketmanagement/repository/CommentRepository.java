package com.ticketing.ticketmanagement.repository;

import com.ticketing.ticketmanagement.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTicketIdAndInternalNoteTrue(Long ticketId);
}
