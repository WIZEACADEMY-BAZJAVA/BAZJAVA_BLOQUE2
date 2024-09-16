package com.superapp.springboot.learningjava.model;

import com.superapp.springboot.learningjava.enums.Puesto;

public class CajeroBean extends EmpleadoBean {

	public CajeroBean(long empleado_id, String nombre, Puesto puesto) {
		super(empleado_id, nombre, puesto);
	}

	public void cambiarEstatus(String estatus) {
		if("Activo".equals(estatus)) 
			super.cambiarEstatus(true);
		else
			super.cambiarEstatus(false);
	}
	
	

}
