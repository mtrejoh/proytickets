/**
* GCE-360 - Grupo Consultor Empresarial 360
* Todos los derechos Reservados
* 
* Clase: SistemaController.java
* 
* Control de Versiones
* 
* Version Date/Hour                BY        Company   Description
* ------- ------------------------ --------- --------- --------------
* 1.01.0    <16 jun 2026 12:38:16>  GCE-360 GCE-360 Creacion de Clase Primera fase.
* 
*/
package com.mx.gce360.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.gce360.bean.BeanRol;
import com.mx.gce360.services.interfaces.SistemaService;


/**
 * Clase generada para Class SistemaController.
 *
 * @author <mtrejoh>
 * @modifico <mtrejoh>
 */
@RestController
@RequestMapping("/sistema/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SistemaController {

	/** Declaracion de la Constante LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SistemaController.class);
	
	/** Declaracion de variable sistema. */
	@Autowired
	private SistemaService sistema;
	
	/**
	 * Obtiene el roles.
	 *
	 * @return el roles
	 */
	@GetMapping("roles")
	public List<BeanRol> getRoles() {
		return sistema.getRoles();
	}
}
