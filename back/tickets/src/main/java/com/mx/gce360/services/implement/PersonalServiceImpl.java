/**
* GCE-360 - Grupo Consultor Empresarial 360
* Todos los derechos Reservados
* 
* Clase: PersonalServiceImpl.java
* 
* Control de Versiones
* 
* Version Date/Hour                BY        Company   Description
* ------- ------------------------ --------- --------- --------------
* 1.01.0    <22 jun 2026 09:53:56>  GCE-360 GCE-360 Creacion de Clase Primera fase.
* 
*/
package com.mx.gce360.services.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.gce360.bean.BeanPersonal;
import com.mx.gce360.bean.BeanUsuario;
import com.mx.gce360.dao.PersonalDao;
import com.mx.gce360.services.interfaces.PersonalService;

/**
 * Clase generada para Class PersonalServiceImpl.
 *
 * @author <mtrejoh>
 * @modifico <mtrejoh>
 */
@Service
public class PersonalServiceImpl implements PersonalService {

	/** Declaracion de variable personal. */
	@Autowired
	private PersonalDao personal;

	/**
	 * Obtiene el personal.
	 *
	 * @return el personal
	 */
	public List<BeanPersonal> getPersonal() {
		return personal.getPersonal();
	}

	/**
	 * Obtiene el personal activo.
	 *
	 * @return el personal activo
	 */
	public List<BeanUsuario> getPersonalActivo() {
		return personal.getPersonalActivo();
	}
	
	
	public int guardaPersonal(BeanPersonal beanPersonal) {
		return personal.guardaPersonal(beanPersonal);
	}
	
	public int actualizaPersonal(BeanPersonal beanPersonal) {
		return personal.actualizaPersonal(beanPersonal);
	}
	
	public int actualizaStPersonal(BeanPersonal beanPersonal) {
		return personal.actualizaStPersonal(beanPersonal);
	}
}
