/**
* GCE-360 - Grupo Consultor Empresarial 360
* Todos los derechos Reservados
* 
* Clase: PersonalController.java
* 
* Control de Versiones
* 
* Version Date/Hour                BY        Company   Description
* ------- ------------------------ --------- --------- --------------
* 1.01.0    <16 jun 2026 12:05:28>  GCE-360 GCE-360 Creacion de Clase Primera fase.
* 
*/
package com.mx.gce360.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.gce360.bean.BeanPersonal;
import com.mx.gce360.bean.BeanUsuario;
import com.mx.gce360.services.interfaces.PersonalService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/personal")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PersonalController {
	
	private static final Logger LOG = LoggerFactory.getLogger(PersonalController.class);
	
	/** Declaracion de variable personal. */
	@Autowired
	private PersonalService personal;
	
	
	@GetMapping("listar")
	public List<BeanPersonal> getPersonal() {
		return personal.getPersonal();
	}
	
	 
	/**
	 * Personal activo del Sistema
	 * @return
	 */
	@GetMapping("activosys")
	public List<BeanUsuario> getPersonalActivo() {
		return personal.getPersonalActivo();
	}
	
	
	
	@PostMapping("creapersonal")
	public ResponseEntity<String> guardaPersonal(@RequestBody BeanPersonal beanPersonal) {
		LOG.info("Inicia proceso de guardado de Personal.....");
		int response = personal.guardaPersonal(beanPersonal);
		return ResponseEntity.ok( String.valueOf(response) );
	}
	
	
	@PostMapping("actualizar")
	public ResponseEntity<String> actualizaPersonal(@RequestBody BeanPersonal beanPersonal) {
		LOG.info("Inicia proceso de guardado de Personal.....");
		int response = personal.actualizaPersonal(beanPersonal);
		return ResponseEntity.ok( String.valueOf(response) );
	}
	
	
	@PostMapping("actualizar/status")
	public ResponseEntity<String> actualizaStPersonal(@RequestBody BeanPersonal beanPersonal) {
		LOG.info("Inicia proceso de guardado de Personal.....");
		int response = personal.actualizaStPersonal(beanPersonal);
		return ResponseEntity.ok( String.valueOf(response) );
	}
}
