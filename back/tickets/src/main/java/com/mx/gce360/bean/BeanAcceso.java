package com.mx.gce360.bean;

public class BeanAcceso {
    private String usuario;
    private String password;

    public BeanAcceso() {
    }

    public BeanAcceso(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
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
}
