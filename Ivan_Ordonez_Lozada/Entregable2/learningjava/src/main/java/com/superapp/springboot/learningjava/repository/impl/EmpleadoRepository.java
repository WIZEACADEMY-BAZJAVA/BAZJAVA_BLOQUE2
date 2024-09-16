package com.superapp.springboot.learningjava.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.superapp.springboot.learningjava.model.EmpleadoBean;

public interface EmpleadoRepository extends MongoRepository<EmpleadoBean, String>{

	public Optional<EmpleadoBean> findById(String id);
	public List<EmpleadoBean> findByPuesto(String puesto);
	public List<EmpleadoBean> findByNombre (String nombre);
}
