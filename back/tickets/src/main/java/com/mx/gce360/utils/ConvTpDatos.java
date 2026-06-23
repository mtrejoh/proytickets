package com.mx.gce360.utils;

public class ConvTpDatos {
	
	public static String getCadena(Object obj) {
		return getObjeto(obj);
	}
	
	public static String getObjeto(Object obj) {
		if( obj == null) {
			return "";
		}
		return obj.toString();
	}
}
