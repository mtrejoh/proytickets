/**
* GCE-360 - Grupo Consultor Empresarial 360
* Todos los derechos Reservados
* 
* Clase: SistemaService.java
* 
* Control de Versiones
* 
* Version Date/Hour                BY        Company   Description
* ------- ------------------------ --------- --------- --------------
* 1.01.0    <16 jun 2026 12:37:48>  GCE-360 GCE-360 Creacion de Clase Primera fase.
* 
*/
package com.mx.gce360.services.interfaces;

import java.util.List;

import com.mx.gce360.bean.BeanRol;

/**
 * Clase generada para Interface SistemaService.
 *
 * @author <mtrejoh>
 * @modifico <mtrejoh>
 */
public interface SistemaService {
	
	/**
	 * Obtiene el roles.
	 *
	 * @return el roles
	 */
	List<BeanRol> getRoles();
}
