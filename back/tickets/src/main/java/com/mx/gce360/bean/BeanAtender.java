package com.mx.gce360.bean;

import java.io.Serializable;

public class BeanAtender implements Serializable {
	
	private static final long serialVersionUID = -5879694815129993720L;
	private String idUsuario;
	private String usuario;
	private String nombre;
	
	
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
