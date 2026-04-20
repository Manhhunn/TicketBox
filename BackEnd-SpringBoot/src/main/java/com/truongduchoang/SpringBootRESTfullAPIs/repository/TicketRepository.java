package com.truongduchoang.SpringBootRESTfullAPIs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.truongduchoang.SpringBootRESTfullAPIs.models.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByTicketCode(String ticketCode);

    boolean existsByTicketCode(String ticketCode);

    boolean existsByEventEventId(Long eventId);
}
