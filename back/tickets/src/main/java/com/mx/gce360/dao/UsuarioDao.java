package com.mx.gce360.dao;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mx.gce360.bean.BeanAtender;
import com.mx.gce360.bean.BeanUsuario;
import com.mx.gce360.utils.OpCadena;

@Repository
public class UsuarioDao {

	private static final Logger LOG = LoggerFactory.getLogger(UsuarioDao.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public BeanUsuario findByUsuarioAndPassword(String usuario, String password) {
		String sql = "SELECT CONCAT('GCE-', LPAD(t.idusuario, 6, '0')) AS cveusuario,  "
				+ "CONCAT_WS(' ', tbp.app, tbp.apm, tbp.nombre) AS nombre, "
				+ "t.usuario, t.estatus, t2.cverol, t2.nombre AS nomrol " + "FROM tbusuarios t "
				+ "INNER JOIN tbroles t2 ON t.idrol = t2.idrol "
				+ "INNER JOIN tbpersonal tbp ON t.idpersonal = tbp.idpersonal "
				+ "WHERE t.estatus NOT IN ('INACTIVO', 'BAJA') " + "AND t2.estatus = 1 " + "AND tbp.estatus = 1 "
				+ "and t.usuario = ?  " + "AND t.password = ?  " + "LIMIT 1;";

		LOG.info("Buscamos las credenciales del usuario");

		var results = jdbcTemplate.query(sql, (rs, rowNum) -> {
			BeanUsuario usuarioBean = new BeanUsuario();
			usuarioBean.setCvePersonal(rs.getString("cveusuario"));
			usuarioBean.setCveRol(rs.getString("cverol"));
			usuarioBean.setUsuario(rs.getString("usuario"));
			usuarioBean.setEstatus(rs.getString("estatus"));
			usuarioBean.setNombre(rs.getString("nombre"));
			return usuarioBean;
		}, usuario, password);

		return results.isEmpty() ? null : results.get(0);
	}

	public List<BeanAtender> getAtender() {
		String sql = "SELECT t.idusuario, t.usuario, "
			+ "concat(t1.app, ' ', t1.apm, ' ', t1.nombre) as  nombre "
			+ "FROM tbusuarios t " 
			+ "INNER JOIN tbpersonal t1 on t1.idpersonal  = t.idpersonal "
			+ "where t.estatus in ('ACTIVO', 'CHANGE_PASS');";

		LOG.info("Buscamos quien atendera el ticket....");

		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			BeanAtender ticket = new BeanAtender();
			ticket.setIdUsuario(rs.getString("idusuario"));
			ticket.setUsuario(rs.getString("usuario"));
			ticket.setNombre(rs.getString("nombre"));

			return ticket;
		});
	}

	public int setAtender(Long ticket, Long usuario) {
		String sql = "INSERT INTO tbpersonalasig (idusuario, idticket) " + "VALUES(?, ?);";

		LOG.info("Agregamos el personal asignado..");
		return jdbcTemplate.update(sql, usuario, ticket);
	}

	
	/**
	 * Metodo para crear un usuario
	 * 
	 * @param beanUsuario
	 * @return
	 */
	public int crearUsuario(BeanUsuario beanUsuario) {
		Long idRol = OpCadena.obtenerSubCad( beanUsuario.getCveRol() );
		Long idPersonal = OpCadena.obtenerSubCad( beanUsuario.getCvePersonal() );
		
		// Validamos que contenga los datos necesarios
		if( idRol ==0 || idPersonal ==0 || "".equals(beanUsuario.getUsuario()) || "".equals(beanUsuario.getPassword())) {
			LOG.error("Existen datos vacios.");
			return 0;
		}
    	String sql = "INSERT INTO tbusuarios "
    		+ "(idpersonal, idrol, usuario, password) "
        	+ "VALUES(?, ?, ?, ?);";
        	
        LOG.info("Agregamos el usuario asignado..");
         int response = 0;
         try {
        	 response = jdbcTemplate.update(sql,
    			idPersonal,
	        	idRol,
	        	beanUsuario.getUsuario(),
	        	beanUsuario.getPassword()
	        );
         } catch(Exception e) {
        	 response = 0;
         }
         return response;
    }
	
	
	/**
	 * Metodo para cambiar el rol del Usuario
	 * 
	 * @param idUsuario
	 * @param cveRol
	 * @return
	 */
	public int changeRol(BeanUsuario beanUsuario) {
		Long idRol = OpCadena.obtenerSubCad( beanUsuario.getCveRol() );
		Long idUsuario = OpCadena.obtenerSubCad( beanUsuario.getCveUsuario() );

    	String sql = "UPDATE tbusuarios set idrol=? "
        	+ "WHERE idUsuario=?;";
        	
        LOG.info("Actualizamos el nuevo ROl del usuario...");
        return jdbcTemplate.update(sql, idRol, idUsuario );
    }
	
	
	/**
	 * Metodo para cambiar el estatus del Usuario
	 * 
	 * @param idUsuario
	 * @return
	 */
	public int changePass(BeanUsuario beanUsuario) {
		Long idUsuario = OpCadena.obtenerSubCad( beanUsuario.getCveUsuario() );
    	String sql = "UPDATE tbusuarios set estatus='CHANGE_PASS' "
        	+ "WHERE idUsuario=?;";
        	
        LOG.info("Actualizamos el nuevo status del usuario a CHANGE_PASS...");
        int response = 0;
        try {
        	response = jdbcTemplate.update(sql, idUsuario );
        } catch(Exception e) {
        	LOG.error("Ocurrio un error en la actualizacion del ROL");
        }
        return response;
    }
}
