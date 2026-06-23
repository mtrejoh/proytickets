package com.mx.gce360.bean;

public class BeanSeguimiento {
	private String id;
	private String usuario;
    private String nombre;
    private String comentario;
    private String fcomentario;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getFcomentario() {
		return fcomentario;
	}
	public void setFcomentario(String fcomentario) {
		this.fcomentario = fcomentario;
	}
}
