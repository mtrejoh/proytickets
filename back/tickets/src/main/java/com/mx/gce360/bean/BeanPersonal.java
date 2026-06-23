/**
* GCE-360 - Grupo Consultor Empresarial 360
* Todos los derechos Reservados
* 
* Clase: BeanPersonal.java
* 
* Control de Versiones
* 
* Version Date/Hour                BY        Company   Description
* ------- ------------------------ --------- --------- --------------
* 1.01.0    <22 jun 2026 10:39:17>  GCE-360 GCE-360 Creacion de Clase Primera fase.
* 
*/
package com.mx.gce360.bean;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Clase generada para Class BeanPersonal.
 *
 * @author <mtrejoh>
 * @modifico <mtrejoh>
 */
public class BeanPersonal implements Serializable {
	
	/** Declaracion de la Constante serialVersionUID. */
	private static final long serialVersionUID = -3998599967278002037L;
	
	/** Declaracion de variable id personal. */
	private String idPersonal;
	
	/** Declaracion de variable cve personal. */
	private String cvePersonal;
	
	/** Declaracion de variable nombre. */
	private String nombre;
	
	/** Declaracion de variable app. */
	private String app;
	
	/** Declaracion de variable apm. */
	private String apm;
	
	/** Declaracion de variable estatus. */
	private String estatus;
	
	
	/**
	 * Obtiene el id personal.
	 *
	 * @return el id personal
	 */
	public String getIdPersonal() {
		return idPersonal;
	}
	
	/**
	 * Establece el id personal.
	 *
	 * @param idPersonal el nuevo id personal
	 */
	public void setIdPersonal(String idPersonal) {
		this.idPersonal = idPersonal;
	}
	
	
	/**
	 * Obtiene el cve personal.
	 *
	 * @return el cve personal
	 */
	public String getCvePersonal() {
		return cvePersonal;
	}
	
	/**
	 * Establece el cve personal.
	 *
	 * @param cvePersonal el nuevo cve personal
	 */
	public void setCvePersonal(String cvePersonal) {
		this.cvePersonal = cvePersonal;
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
	 * Obtiene el app.
	 *
	 * @return el app
	 */
	public String getApp() {
		return app;
	}
	
	/**
	 * Establece el app.
	 *
	 * @param app el nuevo app
	 */
	public void setApp(String app) {
		this.app = app;
	}
	
	/**
	 * Obtiene el apm.
	 *
	 * @return el apm
	 */
	public String getApm() {
		return apm;
	}
	
	/**
	 * Establece el apm.
	 *
	 * @param apm el nuevo apm
	 */
	public void setApm(String apm) {
		this.apm = apm;
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
	
	/**
	 * To string.
	 *
	 * @return el string
	 */
	@Override
	public String toString() {
		return "datos {id=" + idPersonal + ", nombre=" + nombre + ", app=" + app + ", apm=" + apm + ", estatus="
				+ estatus + "}";
	}
}
