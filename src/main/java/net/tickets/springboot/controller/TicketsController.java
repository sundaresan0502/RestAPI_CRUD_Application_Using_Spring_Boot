package net.tickets.springboot.controller;

import java.util.List;
import org.jboss.logging.Logger;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.tickets.springboot.model.Ticket;
import net.tickets.springboot.service.TicketService;

@Controller
public class TicketsController {
	
	private final Logger log = LoggerFactory.logger(getClass());
	
	@Autowired
	private TicketService ticketsService;
	
	// display list of tickers
	@GetMapping("/")
	public String viewHomePage(Model model) {
		log.info("{} - View Home Page - starts");
		log.info("{} - View Home Page - ends");
		return findPaginated(1, "title", "asc", model);		
	}
	
	@GetMapping("/showNewTicketForm")
	public String showNewTicketForm(Model model) {
		// create model attribute to bind form data
		log.info("{} - Create new ticket - starts");
		Ticket ticket = new Ticket();
		model.addAttribute("ticket", ticket);
		log.info("{} - Create new ticket - ends");
		return "new_ticket";
	}
	
	@PostMapping("/saveTicket")
	public String saveTicket(@ModelAttribute("Ticket") Ticket ticket) {
		// save ticket to database
		ticketsService.saveTicket(ticket);
		log.info("{} - Save new ticket - starts");
		log.info("{} - Save new ticket - ends");
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		log.infof("{} - update ticket - starts", id);
		// get employee from the service
		Ticket currentTicket = ticketsService.getTicketById(id);
		
		// set ticket as a model attribute to pre-populate the form
		model.addAttribute("Ticket", currentTicket);
		log.infof("{} - update ticket - end", id);
		return "update_Ticket";
	}
	
	@GetMapping("/deleteTicket/{id}")
	public String deleteTicket(@PathVariable (value = "id") long id) {
		log.infof("{} - delete ticket - starts", id);
		// call delete ticket method 
		this.ticketsService.deleteTicketById(id);
		log.infof("{} - delete ticket - end", id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Ticket> page = ticketsService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Ticket> listTickets = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listTickets", listTickets);
		return "index";
	}
}
