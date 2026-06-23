package com.mx.gce360.bean;

import java.io.Serializable;


/**
 * Clase Bean para la respuesta del acceso a Usuarios
 * 
 * @author: <mtrejoh>
 */
public class BeanUsuario implements Serializable {

    /** Variable para el Serial */
    private static final long serialVersionUID = 1L;

    /** Variabe para la clave del personal */
    private String cvePersonal;

    /** Variable para la clave del usuario */
    private String cveUsuario;
    
    /** Variable para la clave del rol */
    private String cveRol;
    
    /** Variable que contiene la clave del Rol */
    private String nombreRol;

    /** Variable que contiene el usuario */
    private String usuario;

    /** Variable que contiene el password del usuario */
    private String password;
    
    /** Variable que contiene el estatus del usuario */
    private String estatUsr;
    
    /** Variable que contiene el estatus de la cuenta */
    private String estatus;

    /** Variable que contiene el nombre de la persona */
    private String nombre;


    public String getCvePersonal() {
        return cvePersonal;
    }
    public void setCvePersonal(String cvePersonal) {
        this.cvePersonal = cvePersonal;
    }
    
    public String getCveUsuario() {
		return cveUsuario;
	}
	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}
	
	public String getCveRol() {
        return cveRol;
    }
    public void setCveRol(String cveRol) {
        this.cveRol = cveRol;
    }
    
    public String getNombreRol() {
		return nombreRol;
	}
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	
	public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEstatUsr() {
		return estatUsr;
	}
	public void setEstatUsr(String estatUsr) {
		this.estatUsr = estatUsr;
	}
	
	public String getEstatus() {
        return estatus;
    }
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
	@Override
	public String toString() {
		return "response [cvePersonal=" + cvePersonal + ", cveUsuario=" + cveUsuario + ", cveRol=" + cveRol
				+ ", nombreRol=" + nombreRol + ", usuario=" + usuario + ", estatUsr=" + estatUsr + ", estatus="
				+ estatus + ", nombre=" + nombre + "]";
	}

}
