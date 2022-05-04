package net.tickets.springboot.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import net.tickets.springboot.model.Ticket;
import net.tickets.springboot.repository.TicketsRepository;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketsRepository ticketsRepository;

	@Override
	public List<Ticket> getAllTickets() {
		return ticketsRepository.findAll();
	}

	@Override
	public void saveTicket(Ticket ticket) {
		
		this.ticketsRepository.save(ticket);
	}

	@Override
	public Ticket getTicketById(long id) {
		Optional<Ticket> optional = ticketsRepository.findById(id);
		Ticket ticket = null;
		if (optional.isPresent()) {
			ticket = optional.get();
		} else {
			throw new RuntimeException(" ticket not found for id :: " + id);
		}
		return ticket;
	}

	@Override
	public void deleteTicketById(long id) {
		this.ticketsRepository.deleteById(id);
	}

	@Override
	public Page<Ticket> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.ticketsRepository.findAll(pageable);
	}
}
