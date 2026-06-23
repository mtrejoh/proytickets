package com.mx.gce360.services.interfaces;

import java.util.List;

import com.mx.gce360.bean.BeanAcceso;
import com.mx.gce360.bean.BeanAtender;
import com.mx.gce360.bean.BeanUsuario;

public interface UsuarioService {

    /**
     * Valida las credenciales de acceso y devuelve los datos del usuario si son correctas.
     *
     * @param acceso datos de acceso del usuario
     * @return BeanUsuario con información de la sesión o null si las credenciales son inválidas
     */
	public BeanUsuario login(BeanAcceso acceso);
    
    
    public List<BeanAtender> getAtender();
    
    public int setAtender(Long ticket, Long usuario);
    
    
    /**
	 * Metodo para crear un usuario
	 * 
	 * @param beanUsuario
	 * @return
	 */
    int crearUsuario(BeanUsuario beanUsuario);
    
    
    /**
	 * Metodo para cambiar el rol del Usuario
	 * 
	 * @param idUsuario
	 * @param cveRol
	 * @return
	 */
    int changeRol(BeanUsuario beanUsuario);
    
    
    /**
	 * Metodo para cambiar el estatus del Usuario
	 * 
	 * @param idUsuario
	 * @return
	 */
    int changePass(BeanUsuario beanUsuario);
}
