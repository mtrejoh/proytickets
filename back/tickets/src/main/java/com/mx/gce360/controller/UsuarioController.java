package com.mx.gce360.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.gce360.bean.BeanAcceso;
import com.mx.gce360.bean.BeanAtender;
import com.mx.gce360.bean.BeanUsuario;
import com.mx.gce360.services.interfaces.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;



/**
 * Clase  para el contro de los procesos de Usuarios
 * 
 * @author: <mtrejoh>
 */
@RestController
@RequestMapping("/usuarios/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

	private static final Logger LOG = LoggerFactory.getLogger(UsuarioController.class);
	
    @Autowired
    private UsuarioService usuarioService;

    private static final String VALID_CHARS_REGEX = "^[a-zA-Z0-9#$_\\-]+$";
    
    /**
     * Metodo para el acceso al sistema
     * 
     * @param beanAcceso DAtos de acceso del usuario
     * @return BeanUsuario Datos del usuario que accede al sistema
     */
    @PostMapping("acceso")
    public ResponseEntity<?> accesoSystem(@RequestBody BeanAcceso beanAcceso) {
    	LOG.info("Validando acceso al sistema....");
        if (beanAcceso == null
                || beanAcceso.getUsuario() == null
                || beanAcceso.getPassword() == null
                || beanAcceso.getUsuario().isBlank()
                || beanAcceso.getPassword().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuario y contraseña son requeridos y no pueden estar vacíos.");
        }

        if (!beanAcceso.getUsuario().matches(VALID_CHARS_REGEX)
                || !beanAcceso.getPassword().matches(VALID_CHARS_REGEX)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Usuario y contraseña solo pueden contener letras, números y los caracteres: # $ _ -");
        }

        BeanUsuario response = usuarioService.login(beanAcceso);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuario o contraseña incorrectos.");
        }
        return ResponseEntity.ok(response);
    }
    
    
    
    @GetMapping("atender")
    public List<BeanAtender> getAtencione() {
    	return usuarioService.getAtender();
    }
    
    
    @PostMapping("asignar")
    public int asignarUsr(@RequestBody Map<String, String> datos) {
    	Long ticket = Long.valueOf( datos.get("ticket") );
    	Long usuario = Long.valueOf( datos.get("usuario") );
    	
    	LOG.info("Asignando ticket...");
    	return usuarioService.setAtender(ticket,  usuario);
    }
    
    
    @PutMapping("crearusr")
    public ResponseEntity<?> crearUsuario(@RequestBody BeanUsuario beanUsuario) {
    	return ResponseEntity.ok( usuarioService.crearUsuario(beanUsuario) );
    }
    
    
    /**
   	 * Metodo para cambiar el rol del Usuario
   	 * 
   	 * @param idUsuario
   	 * @param cveRol
   	 * @return
   	 */
    @PostMapping("changerol")
    public int changeRol(@RequestBody BeanUsuario beanUsuario) {
    	beanUsuario.setCveUsuario( beanUsuario.getUsuario());
    	return usuarioService.changeRol(beanUsuario);
    }
       
       
    /**
   	 * Metodo para cambiar el estatus del Usuario
   	 * 
   	 * @param idUsuario
   	 * @return
   	 */
    @PostMapping("changestatus")
     public int changePass(@RequestBody BeanUsuario beanUsuario) {
    	beanUsuario.setCveUsuario( beanUsuario.getUsuario());
    	 return usuarioService.changePass(beanUsuario);
     }
    
}

