/**
* GCE-360 - Grupo Consultor Empresarial 360
* Todos los derechos Reservados
* 
* Clase: SistemaDao.java
* 
* Control de Versiones
* 
* Version Date/Hour                BY        Company   Description
* ------- ------------------------ --------- --------- --------------
* 1.01.0    <16 jun 2026 12:19:05>  GCE-360 GCE-360 Creacion de Clase Primera fase.
* 
*/
package com.mx.gce360.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mx.gce360.bean.BeanRol;


/**
 * Clase generada para Class SistemaDao.
 *
 * @author <mtrejoh>
 * @modifico <mtrejoh>
 */
@Repository
public class SistemaDao {
	
	/** Declaracion de la Constante LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SistemaDao.class);
	
	/** Declaracion de variable jdbc template. */
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * Listado de los comentarios de seguimiento.
	 *
	 * @param idTicket the id ticket
	 * @return el roles
	 */
	public List<BeanRol> getRoles() {
		String sql = "select CONCAT('GCE-', lpad(t.idrol, 6, 0)) as id, cverol, nombre, "
			+ "case when estatus=1 then 'ACTIVO' else 'INACTIVO' end as estatus "
			+ "from tbroles t ; ";
		
		LOG.info("Seguimiento de Roles.......");
		
		return jdbcTemplate.query(
	        sql,
	        (rs, rowNum) -> {
	        	BeanRol dto = new BeanRol();
	            dto.setIdRol( rs.getString("id") );
	            dto.setCveRol( rs.getString("cverol") );
	            dto.setNombre( rs.getString("nombre") );
	            dto.setEstatus( rs.getString("estatus") );
	            return dto;
	        }
	    );
	}
}
