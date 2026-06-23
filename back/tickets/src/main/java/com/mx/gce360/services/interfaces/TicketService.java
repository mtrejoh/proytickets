package com.mx.gce360.services.interfaces;

import java.util.List;
import java.util.Map;

import com.mx.gce360.bean.BeanSeguimiento;
import com.mx.gce360.bean.TicketBean;



public interface TicketService {
	Map<String, String> getDataDash();
	
	public List<TicketBean> obtenerTickets(Long inicio, Long cantidad);

	public void crearTicket(TicketBean ticket);
	
	public int cancelTicket(Long idTicket, String cveUsuario, String descripcion);
	
	public int closeTicket(Long idTicket, String cveUsuario, String descripcion, String estatus);
	
	public int openTicket(Long idTicket, String cveUsuario);
	
	public List<BeanSeguimiento> getSeguimiento(Long idTicket);
	
	
	public int saveSeguimiento(BeanSeguimiento infoTicket);
}
