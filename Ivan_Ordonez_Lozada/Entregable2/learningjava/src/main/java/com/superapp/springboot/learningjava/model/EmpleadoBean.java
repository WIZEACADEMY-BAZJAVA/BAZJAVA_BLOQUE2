package com.superapp.springboot.learningjava.model;

import org.springframework.data.annotation.Id;

import com.superapp.springboot.learningjava.enums.Puesto;

public class EmpleadoBean {

	@Id
	private String id;
	private long matricula;
	private String nombre;
	private Puesto puesto;
	protected boolean estatus;
	private CentroCostos centro_costos;
	
	public EmpleadoBean() {
		super();
	}

	//Sobrecarga - constructor
	public EmpleadoBean(long matricula, String nombre, Puesto puesto) {
		super();
		this.matricula = matricula;
		this.nombre = nombre;
		this.puesto = puesto;
		this.estatus = false;
	}

	public long getMatricula() {
		return matricula;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMatricula(long matricula) {
		this.matricula = matricula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}
	
	public CentroCostos getCentro_costos() {
		return centro_costos;
	}

	public void setCentro_costos(CentroCostos centro_costos) {
		this.centro_costos = centro_costos;
	}

	//Sobrecarga - método
	public void cambiarEstatus(boolean estatus) {
		if(estatus == true)
			this.estatus = false;
		else
			this.estatus = true;
	}
	
	class CentroCostos {
		private int id;
		private String descripcion;
		
		public int getId() {
			return id;
		}
		
		public void setId(int id) {
			this.id = id;
		}
		
		public String getDescripcion() {
			return descripcion;
		}
		
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		
	}
}
