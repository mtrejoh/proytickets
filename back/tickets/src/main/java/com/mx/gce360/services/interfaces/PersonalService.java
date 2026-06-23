/**
* GCE-360 - Grupo Consultor Empresarial 360
* Todos los derechos Reservados
* 
* Clase: PersonalService.java
* 
* Control de Versiones
* 
* Version Date/Hour                BY        Company   Description
* ------- ------------------------ --------- --------- --------------
* 1.01.0    <22 jun 2026 09:53:26>  GCE-360 GCE-360 Creacion de Clase Primera fase.
* 
*/
package com.mx.gce360.services.interfaces;

import java.util.List;

import com.mx.gce360.bean.BeanPersonal;
import com.mx.gce360.bean.BeanUsuario;

/**
 * Clase generada para Interface PersonalService.
 *
 * @author <mtrejoh>
 * @modifico <mtrejoh>
 */
public interface PersonalService {
	
	/**
	 * Obtiene el listado de personal.
	 *
	 * @return el personal
	 */
	List<BeanPersonal> getPersonal();
	

	/**
	 * Listado de Personal Activo.
	 *
	 * @return el personal activo
	 */
	List<BeanUsuario> getPersonalActivo() ;
	
	int guardaPersonal(BeanPersonal beanPersonal);
	
	int actualizaPersonal(BeanPersonal beanPersonal);
	
	int actualizaStPersonal(BeanPersonal beanPersonal);
}
