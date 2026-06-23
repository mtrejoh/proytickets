/**
* GCE-360 - Grupo Consultor Empresarial 360
* Todos los derechos Reservados
* 
* Clase: OpCadena.java
* 
* Control de Versiones
* 
* Version Date/Hour                BY        Company   Description
* ------- ------------------------ --------- --------- --------------
* 1.01.0    <16 jun 2026 13:18:49>  GCE-360 GCE-360 Creacion de Clase Primera fase.
* 
*/
package com.mx.gce360.utils;


/**
 * Clase generada para Class OpCadena.
 *
 * @author <mtrejoh>
 * @modifico <mtrejoh>
 */
public class OpCadena {
	
	
	/**
	 * Obtener sub cad.
	 *
	 * @param cadena the cadena
	 * @return el int
	 */
	public static Long obtenerSubCad(String cadena) {
		Long response = 0L;
		if( cadena == null && "".equals(cadena)) {
			return response;
		}
		
		String cad = cadena.substring(4);
		try {
			response = Long.valueOf(cad);
		} catch(NumberFormatException e) {
			response = 0L;
		}
		return response;
	}
}
