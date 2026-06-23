/**
* GCE-360 - Grupo Consultor Empresarial 360
* Todos los derechos Reservados
* 
* Clase: SistemaServiceImpl.java
* 
* Control de Versiones
* 
* Version Date/Hour                BY        Company   Description
* ------- ------------------------ --------- --------- --------------
* 1.01.0    <16 jun 2026 12:20:10>  GCE-360 GCE-360 Creacion de Clase Primera fase.
* 
*/
package com.mx.gce360.services.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.gce360.bean.BeanRol;
import com.mx.gce360.dao.SistemaDao;
import com.mx.gce360.services.interfaces.SistemaService;

/**
 * Clase generada para Class SistemaServiceImpl.
 *
 * @author <mtrejoh>
 * @modifico <mtrejoh>
 */
@Service
public class SistemaServiceImpl implements SistemaService {

	/** Declaracion de variable sistema. */
	@Autowired
	private SistemaDao sistema;
	
	
	/**
	 * Obtiene el roles.
	 *
	 * @return el roles
	 */
	@Override
	public List<BeanRol> getRoles() {
		return sistema.getRoles();
	}

}
