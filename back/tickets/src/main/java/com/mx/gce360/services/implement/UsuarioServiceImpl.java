package com.mx.gce360.services.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.gce360.bean.BeanAcceso;
import com.mx.gce360.bean.BeanAtender;
import com.mx.gce360.bean.BeanUsuario;
import com.mx.gce360.dao.UsuarioDao;
import com.mx.gce360.services.interfaces.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    
    public BeanUsuario login(BeanAcceso acceso) {
        return usuarioDao.findByUsuarioAndPassword(acceso.getUsuario(), acceso.getPassword());
    }
    
    
    public List<BeanAtender> getAtender() {
    	return usuarioDao.getAtender();
    }
    
    
    public int setAtender(Long ticket, Long usuario) {
    	return usuarioDao.setAtender(ticket, usuario);
    }
    
    
    /**
	 * Metodo para crear un usuario
	 * 
	 * @param beanUsuario
	 * @return
	 */
    public int crearUsuario(BeanUsuario beanUsuario) {
    	return usuarioDao.crearUsuario(beanUsuario);
    }
    
    
    /**
	 * Metodo para cambiar el rol del Usuario
	 * 
	 * @param idUsuario
	 * @param cveRol
	 * @return
	 */
    public int changeRol(BeanUsuario beanUsuario) {
    	return usuarioDao.changeRol(beanUsuario);
    }
    
    
    /**
	 * Metodo para cambiar el estatus del Usuario
	 * 
	 * @param idUsuario
	 * @return
	 */
    public int changePass(BeanUsuario beanUsuario) {
    	return usuarioDao.changePass(beanUsuario);
    }
}
