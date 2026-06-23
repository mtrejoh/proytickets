package com.mx.gce360.services.implement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.gce360.bean.BeanSeguimiento;
import com.mx.gce360.bean.TicketBean;
import com.mx.gce360.dao.TicketDao;
import com.mx.gce360.services.interfaces.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
	
	@Autowired
	private TicketDao ticketDAO;

	public Map<String, String> getDataDash() {
		return ticketDAO.getDataDash();
	}
	
	public List<TicketBean> obtenerTickets(Long inicio, Long cantidad) {
		return ticketDAO.listar(inicio, cantidad);
	}

	
	public void crearTicket(TicketBean ticket) {
		ticketDAO.guardar(ticket);
	}


	public int cancelTicket(Long idTicket, String cveUsuario, String descripcion) {
		return ticketDAO.cancelar(idTicket, cveUsuario, descripcion);
	}
	
	public int closeTicket(Long idTicket, String cveUsuario, String descripcion, String estatus) {
		return ticketDAO.closeTicket(idTicket, cveUsuario, descripcion, estatus);
	}
	
	public int openTicket(Long idTicket, String cveUsuario) {
		return ticketDAO.openTicket(idTicket, cveUsuario);
	}

	
	public int saveSeguimiento(BeanSeguimiento infoTicket) {
		return ticketDAO.saveSeguimiento(infoTicket);
	}
	
	
	public List<BeanSeguimiento> getSeguimiento(Long idTicket) {
		return ticketDAO.getSeguimiento(idTicket);
	}
}
