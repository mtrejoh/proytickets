/**
* GCE-360 - Grupo Consultor Empresarial 360
* Todos los derechos Reservados
* 
* Clase: BeanRol.java
* 
* Control de Versiones
* 
* Version Date/Hour                BY        Company   Description
* ------- ------------------------ --------- --------- --------------
* 1.01.0    <16 jun 2026 12:05:03>  GCE-360 GCE-360 Creacion de Clase Primera fase.
* 
*/


package com.mx.gce360.bean;

import java.io.Serializable;

/**
 * Clase generada para Class BeanRol.
 *
 * @author <mtrejoh>
 * @modifico <mtrejoh>
 */
public class BeanRol implements Serializable {
	
	/** Declaracion de la Constante serialVersionUID. */
	private static final long serialVersionUID = -2478453004944931642L;
	
	/** Declaracion de variable id rol. */
	private String idRol;
	
	/** Declaracion de variable cve rol. */
	private String cveRol;
	
	/** Declaracion de variable nombre. */
	private String nombre;
	
	/** Declaracion de variable estatus. */
	private String estatus;
	
	
	/**
	 * Obtiene el id rol.
	 *
	 * @return el id rol
	 */
	public String getIdRol() {
		return idRol;
	}
	
	/**
	 * Establece el id rol.
	 *
	 * @param idRol el nuevo id rol
	 */
	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}
	
	/**
	 * Obtiene el cve rol.
	 *
	 * @return el cve rol
	 */
	public String getCveRol() {
		return cveRol;
	}
	
	/**
	 * Establece el cve rol.
	 *
	 * @param cveRol el nuevo cve rol
	 */
	public void setCveRol(String cveRol) {
		this.cveRol = cveRol;
	}
	
	/**
	 * Obtiene el nombre.
	 *
	 * @return el nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * Establece el nombre.
	 *
	 * @param nombre el nuevo nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * Obtiene el estatus.
	 *
	 * @return el estatus
	 */
	public String getEstatus() {
		return estatus;
	}
	
	/**
	 * Establece el estatus.
	 *
	 * @param estatus el nuevo estatus
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
}
