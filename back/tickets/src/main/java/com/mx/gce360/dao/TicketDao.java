package com.mx.gce360.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mx.gce360.bean.BeanSeguimiento;
import com.mx.gce360.bean.TicketBean;
import com.mx.gce360.utils.ConvTpDatos;
import com.mx.gce360.utils.OpCadena;

@Repository
public class TicketDao {

	private static final Logger LOG = LoggerFactory.getLogger(TicketDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	/**
	 * Metodo para obtener los datos del Tablero del Dashboard
	 * 
	 * @return Map
	 */
	public Map<String, String> getDataDash() {
		String sql = "SELECT  "
			+ "CASE estado "
			+ "WHEN 1 THEN 'ABIERTO' "
			+ "WHEN 2 THEN 'DESARROLLO' "
			+ "WHEN 3 THEN 'RESUELTO' "
			+ "WHEN 4 THEN 'CERRADO' "
			+ "WHEN 5 THEN 'CANCELADO' "
			+ "ELSE 'VACIOS' "
			+ "END AS estatus, "
			+ "COUNT(*) AS total "
			+ "FROM dbtickets.tbtickets "
			+ "GROUP BY estado;";
		
		LOG.info("Se inicia consulta de datos para el Dashboard...");

		// Para devolver un mapa en el Lambda
		return jdbcTemplate.query(sql, rs -> {
		    Map<String, String> response = new HashMap<>();
		    while (rs.next()) {
		        response.put(
		            rs.getString("estatus"),
		            rs.getString("total")
		        );
		    }
		    return response;
		});
	}
	
	/**
	 * Listado de los elementos del Ticket
	 * 
	 * @param inicio
	 * @param cantidad
	 * @return
	 */
	public List<TicketBean> listar(Long inicio, Long cantidad) {
		Long fin = 0L;
		if( inicio == null ) {
			inicio = 0L;
			fin = 50L;
		} else {
			fin = inicio + cantidad;
		}
		
		String sql = "SELECT "
				+ "t.idticket AS cveticket, "
				+ "uc.usuario AS usuario_creador, "
				+ "CONCAT(pc.nombre, ' ', pc.app, ' ', pc.apm) AS creador, "
				+ "ua.usuario AS usuario_asignado, "
				+ "CONCAT(pa.nombre, ' ', pa.app, ' ', pa.apm) AS asignado, "
				+ "t.titulo, "
				+ "t.descripcion, t.modulo,"
				+ "t.priority, "
				+ "t.estado, "
				+ "t.fcreacion, t.finicio, t.fentrega, t.hinvertidas, t.priority, t.estado "
				+ "FROM tbtickets t "
				+ "INNER JOIN tbusuarios uc "
				+ "ON uc.idusuario = t.idusuario "
				+ "INNER JOIN tbpersonal pc "
				+ "ON pc.idpersonal = uc.idpersonal "
				+ "LEFT JOIN ( "
				+ "SELECT "
				+ "idticket, "
				+ "MAX(idpersonalasig) AS ultima_asignacion "
				+ "FROM tbpersonalasig "
				+ "GROUP BY idticket "
				+ ") ult "
				+ "ON ult.idticket = t.idticket "
				+ "LEFT JOIN tbpersonalasig ta "
				+ "ON ta.idpersonalasig = ult.ultima_asignacion "
				+ "LEFT JOIN tbusuarios ua "
				+ "ON ua.idusuario = ta.idusuario "
				+ "LEFT JOIN tbpersonal pa "
				+ "ON pa.idpersonal = ua.idpersonal "
				+ "LIMIT "+ inicio+", "+ fin;
		
		LOG.info("Se inicia consulta de listado de Ticket...");
		
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			TicketBean ticket = new TicketBean();
			ticket.setIdTicket(rs.getString("cveticket"));
			ticket.setSolicitado(rs.getString("usuario_creador"));
			ticket.setAsignado(rs.getString("usuario_asignado"));
			ticket.setNomSolicitado(rs.getString("creador"));
			ticket.setNomAsignado(rs.getString("asignado"));
			ticket.setCreado(rs.getString("fcreacion"));
			ticket.setDescripcion(rs.getString("descripcion"));
			ticket.setPrioridad(rs.getString("priority"));
			ticket.setEstatus(rs.getString("estado"));
			ticket.setTitulo(rs.getString("titulo"));
			ticket.setModulo(rs.getString("modulo"));
			
			ticket.setFinicio(rs.getString("finicio"));
			ticket.setFentrega(rs.getString("fentrega"));
			ticket.setHorasInvertidas(rs.getString("hinvertidas"));
			return ticket;
		});
	}
	
/*
	private int obtenerSubCad(String ticketCvUser) {
		String cad = ticketCvUser.substring(4);
		return Integer.valueOf(cad);
	}
	*/
	
	public int guardar(TicketBean ticket) {
		Long solicita = OpCadena.obtenerSubCad( ticket.getSolicitado() );;
		/*if( ticket.getSolicitado() != null || !"".equals(ticket.getSolicitado())) {
			solicita = obtenerSubCad( ticket.getSolicitado() );
			solicita = OpCadena.obtenerSubCad( ticket.getSolicitado() );
		}*/
		String sql = "INSERT INTO dbtickets.tbtickets "
			+ "(idusuario, titulo, descripcion, modulo, finicio, fentrega, hinvertidas, priority) "
			+ "VALUES (?, ?, ?, ?, null, null, null, ?)";
		LOG.info(sql);
		return jdbcTemplate.update(sql,
				solicita,
				ticket.getTitulo(), 
				ticket.getDescripcion(), 
				ticket.getModulo(), 
				ticket.getPrioridad()
			);
	}

	
	/**
	 * Cancelacion de un ticket
	 * 
	 * @param idTicket
	 * @param cveUsuario
	 * @param descripcion
	 * @return
	 */
	public int cancelar(Long idTicket, String cveUsuario, String descripcion) {		
		return cerradoTicket(idTicket, cveUsuario, descripcion, "");
	}
	
	public int closeTicket(Long idTicket, String cveUsuario, String descripcion, String estatus) {
		return cerradoTicket(idTicket, cveUsuario, descripcion, estatus);
	}
	
	
	public int openTicket(Long idTicket, String cveUsuario) {
		return cerradoTicket(idTicket, cveUsuario, "Se inicia atencion", "DESARROLLO");
	}
	
	
	
	public int cerradoTicket(Long idTicket, String cveUsuario, String descripcion, final String estatus) {
		BeanSeguimiento infoTicket = new BeanSeguimiento();
		infoTicket.setId( idTicket.toString() );
		infoTicket.setUsuario(cveUsuario);
		infoTicket.setComentario(descripcion);
		
		int opresponse = saveSeguimiento(infoTicket);
		LOG.info("Resultado: "+ opresponse);
		String newEstado = "CANCELADO";
		if( !"".equals(estatus) ) {
			 newEstado = estatus;
		}
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE tbtickets SET estado = ?, ");
		if("DESARROLLO".equals(estatus)) {
			sql.append("finicio=now() ");
		} else {
			sql.append("fentrega=now() ");
		}
		sql.append( "WHERE idticket = ?");
		
		LOG.info(sql +" - "+ newEstado +" - "+ idTicket);
		
		return jdbcTemplate.update(sql.toString(), newEstado, idTicket);
	}
	
	
	
	public int saveSeguimiento(BeanSeguimiento infoTicket) {
		String cad = "";
		if( infoTicket.getUsuario() != null) {
			cad = infoTicket.getUsuario().substring(4);
		}
		if( "".equals(cad)) {
			LOG.info("No viene informado el dato del usuario.....");
			return 0;
		}
		int idusuario = Integer.valueOf(cad);
		String sql = "INSERT INTO tbticketseg (idticket, idusuario, comentario) "
			+ "VALUES(?, ?, ?)";
		
		LOG.info("Guardamos el seguimiento...");
		
		return jdbcTemplate.update(sql,
				infoTicket.getId(), 
				idusuario,
				infoTicket.getComentario()
			);
	}

	
	/**
	 * Listado de los comentarios de seguimiento
	 * 
	 * @param idTicket
	 * @return
	 */
	public List<BeanSeguimiento> getSeguimiento(Long idTicket) {
		String sql = "select tseg.idseguimiento, t.usuario, concat(t2.app,' ',t2.apm ,' ', t2.nombre) as nombre, "
			+ "tseg.comentario, tseg.fcomentario  from tbticketseg tseg "
			+ "inner join tbusuarios t on t.idusuario = tseg.idusuario "
			+ "inner join tbpersonal t2 on t2.idpersonal = t.idpersonal "
			+ "where tseg.idticket = ? "
			+ "order by idseguimiento DESC; ";
		
		LOG.info("Seguimiento de Tickets.......");
		
		return jdbcTemplate.query(
		        sql,
		        (rs, rowNum) -> {
		            BeanSeguimiento dto = new BeanSeguimiento();
		            dto.setId( ConvTpDatos.getCadena(rs.getString("idseguimiento")) );
		            dto.setUsuario(ConvTpDatos.getCadena(rs.getString("usuario")));
		            dto.setNombre(ConvTpDatos.getCadena(rs.getString("nombre")));
		            dto.setComentario(ConvTpDatos.getCadena(rs.getString("comentario")));
		            dto.setFcomentario(ConvTpDatos.getCadena(rs.getString("fcomentario")));
		            return dto;
		        },
		        idTicket
		    );
	}
}
