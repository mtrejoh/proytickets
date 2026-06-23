package com.mx.gce360.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.gce360.bean.BeanSeguimiento;
import com.mx.gce360.bean.TicketBean;
import com.mx.gce360.services.interfaces.TicketService;

@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TicketsController {

	private static final Logger LOG = LoggerFactory.getLogger(TicketsController.class);
	
	@Autowired
	private TicketService ticketService;
	
	@GetMapping("dashboard")
	public Map<String, String> getDashboard() {
		Map<String, String> response = ticketService.getDataDash();
		return response;
	}


	@GetMapping("listar")
	public List<TicketBean> listar(@RequestParam int pagina,  @RequestParam int registros) {
		if( pagina>0) {
			pagina = pagina - 1;
		}
		 Long inicio = Long.valueOf(pagina);
		 Long cantidad = Long.valueOf(registros);
		return ticketService.obtenerTickets(inicio, cantidad);
	}

	
	@PostMapping("registrar")
	public ResponseEntity<TicketBean> crear(@RequestBody TicketBean ticket) {
		LOG.info("Inicia proceso de guardado.....");
		ticketService.crearTicket(ticket);
		return ResponseEntity.ok(ticket);
	}
	
	
	@DeleteMapping("cancelar/{id}")
	public ResponseEntity<String> cancelTicket(@PathVariable (name = "id") int idTicket,
			 @RequestBody Map<String, Object> request) {
		String descripcion = (String) request.get("descripcion");
	    String cveUsuario = (String) request.get("cveUsuario");
	    LOG.info("Inicia proceso de cancelado....."+ cveUsuario +" - "+ descripcion);
		
		Long inicio = Long.valueOf(idTicket);
		int response = ticketService.cancelTicket(inicio, cveUsuario, descripcion);
		return ResponseEntity.ok( String.valueOf(response) );
	}
	
	
	@GetMapping("seguimiento/{id}")
	public List<BeanSeguimiento> getSeguimiento(@PathVariable int id) {
		LOG.info("Agregamos el seguimiento....");
		Long idTicket = Long.valueOf(id);
		return ticketService.getSeguimiento(idTicket);
	}
	
	
	@PostMapping("seguimiento")
	public ResponseEntity<String> crear(@RequestBody BeanSeguimiento ticket) {
		LOG.info("Inicia proceso de guardado de seguimiento.....");
		int response = ticketService.saveSeguimiento(ticket);
		return ResponseEntity.ok( String.valueOf(response) );
	}
	
	
	@PostMapping("close/{id}")
	public ResponseEntity<String> closeTicket(@PathVariable (name = "id") int idTicket,
			 @RequestBody Map<String, Object> request) {
		String descripcion = (String) request.get("descripcion");
	    String cveUsuario = (String) request.get("cveUsuario");
	    String estatus = (String) request.get("estatus");
	    LOG.info("Inicia proceso de Cerrado de Ticket....."+ cveUsuario +" - "+ descripcion +" - "+ estatus);
		
		Long inicio = Long.valueOf(idTicket);
		int response = ticketService.closeTicket(inicio, cveUsuario, descripcion, estatus);
		return ResponseEntity.ok( String.valueOf(response) );
	}
	
	
	
	@PostMapping("tomarticket/{id}/{cveUsuario}")
	public ResponseEntity<String> abrirTicket(@PathVariable (name = "id") int idTicket,
			@PathVariable String cveUsuario) {
		LOG.info("Inicia proceso de cancelado....."+ cveUsuario);
		
		Long inicio = Long.valueOf(idTicket);
		int response = ticketService.openTicket(inicio, cveUsuario);
		return ResponseEntity.ok( String.valueOf(response) );
	}
}
