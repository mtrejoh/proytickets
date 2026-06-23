/**
* GCE-360 - Grupo Consultor Empresarial 360
* Todos los derechos Reservados
* 
* Clase: PersonalDao.java
* 
* Control de Versiones
* 
* Version Date/Hour                BY        Company   Description
* ------- ------------------------ --------- --------- --------------
* 1.01.0    <16 jun 2026 12:17:25>  GCE-360 GCE-360 Creacion de Clase Primera fase.
* 
*/
package com.mx.gce360.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mx.gce360.bean.BeanPersonal;
import com.mx.gce360.bean.BeanUsuario;
import com.mx.gce360.utils.OpCadena;

/**
 * Clase generada para Class PersonalDao.
 *
 * @author <mtrejoh>
 * @modifico <mtrejoh>
 */
@Repository
public class PersonalDao {

	/** Declaracion de la Constante LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(PersonalDao.class);

	/** Declaracion de variable jdbc template. */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<BeanPersonal> getPersonal() {
		String sql = "SELECT " + "CONCAT('GCE-', lpad(p.idpersonal, 6, 0)) as cvepersonal, "
				+ "p.nombre, p.app, p.apm, " + "p.estatus " + "FROM tbpersonal p " + "ORDER BY p.app;";

		LOG.info("Obtenemos los datos del Personal");
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			BeanPersonal beanPersonal = new BeanPersonal();
			beanPersonal.setCvePersonal(rs.getString("cvepersonal"));
			beanPersonal.setNombre(rs.getString("nombre"));
			beanPersonal.setApp(rs.getString("app"));
			beanPersonal.setApm(rs.getString("apm"));
			beanPersonal.setEstatus(rs.getString("estatus"));

			return beanPersonal;
		});
	}

	/**
	 * Obtiene el personal activo.
	 *
	 * @return el personal activo
	 */
	public List<BeanUsuario> getPersonalActivo() {
		String sql = "SELECT " + "CONCAT('GCE-', lpad(p.idpersonal, 6, 0)) as cvepersonal, "
				+ "CONCAT('GCE-', lpad(u.idusuario, 6, 0)) as cveusuario, "
				+ "CONCAT('GCE-', lpad(rol.idrol, 6, 0)) as cverol, "
				+ "CONCAT(p.nombre, ' ', p.app, ' ', p.apm) AS nombreCompleto, " + "u.usuario, "
				+ "rol.nombre as nomrol , " + "u.estatus as estatususer, " + "p.estatus " + "FROM tbpersonal p "
				+ "LEFT JOIN tbusuarios u " + "    ON p.idpersonal = u.idpersonal "
				+ "LEFT join tbroles rol on rol.idrol = u.idrol " + "ORDER BY nombreCompleto;";
		System.out.println(sql);
		LOG.info("Obtenemos los datos del Personal");
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			BeanUsuario beanPersonal = new BeanUsuario();
			beanPersonal.setCvePersonal(rs.getString("cvepersonal"));
			beanPersonal.setCveUsuario(rs.getString("cveusuario"));
			beanPersonal.setCveRol(rs.getString("cverol"));
			beanPersonal.setNombre(rs.getString("nombreCompleto"));
			beanPersonal.setUsuario(rs.getString("usuario"));
			beanPersonal.setNombreRol(rs.getString("nomrol"));
			beanPersonal.setEstatUsr(rs.getString("estatususer"));
			beanPersonal.setEstatus(rs.getString("estatus"));

			return beanPersonal;
		});
	}

	public int guardaPersonal(BeanPersonal beanPersonal) {
		LOG.info("Agregamos el personal ...");
		int response = 0;
		String sql = "INSERT INTO tbpersonal (nombre, app, apm) " + "VALUES(?, ?, ?)";
		try {
			response = jdbcTemplate.update(sql, beanPersonal.getNombre(), beanPersonal.getApp(), beanPersonal.getApm());
		} catch (Exception e) {
			response = 0;
		}
		return response;
	}

	public int actualizaPersonal(BeanPersonal beanPersonal) {
		Long idPersonal = OpCadena.obtenerSubCad(beanPersonal.getCvePersonal());
		LOG.info("Actualizamos el personal ...");
		int response = 0;
		String sql = "UPDATE tbpersonal SET nombre=?, app=?, apm=?, estatus=?" + "WHERE idpersonal=?";

		try {
			response = jdbcTemplate.update(sql, beanPersonal.getNombre(), beanPersonal.getApp(), beanPersonal.getApm(),
					beanPersonal.getEstatus(), idPersonal);
		} catch (Exception e) {
			response = 0;
		}
		return response;
	}

	public int actualizaStPersonal(BeanPersonal beanPersonal) {
		Long idPersonal = OpCadena.obtenerSubCad(beanPersonal.getCvePersonal());
		LOG.info("Actualizamos el status del personal ...");
		int response = 0;
		String sql = "UPDATE tbpersonal SET estatus=0 " 
			+ "WHERE idpersonal=?";
		LOG.info(sql);
		try {
			response = jdbcTemplate.update(sql, idPersonal);
		} catch (Exception e) {
			response = 0;
		}
		return response;
	}
}
