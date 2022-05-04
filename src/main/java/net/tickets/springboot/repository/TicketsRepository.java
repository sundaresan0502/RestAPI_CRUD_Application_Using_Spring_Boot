package net.tickets.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.tickets.springboot.model.Ticket;

@Repository
public interface TicketsRepository extends JpaRepository<Ticket, Long>{

}
