package net.tickets.springboot.service;

import java.util.List;

import org.springframework.data.domain.Page;

import net.tickets.springboot.model.Ticket;

public interface TicketService {
	List<Ticket> getAllTickets();
	void saveTicket(Ticket ticket);
	Ticket getTicketById(long id);
	void deleteTicketById(long id);
	Page<Ticket> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
