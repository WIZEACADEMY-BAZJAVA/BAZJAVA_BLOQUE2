package com.superapp.springboot.learningjava.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.superapp.springboot.learningjava.model.EmpleadoBean;
import com.superapp.springboot.learningjava.repository.impl.EmpleadoRepository;

@RestController
@RequestMapping(value = "/empleado")
public class EmpleadoBankController {

    @Autowired
    private EmpleadoRepository repository;
    
    @GetMapping("")
    public List<EmpleadoBean> selectAll(){
        List<EmpleadoBean> customerList = repository.findAll();
        return customerList;
    }
    
    @GetMapping("/{id}")
    public Optional<EmpleadoBean> getSpecificCustomer(@PathVariable String id){
        return repository.findById(id);
    }
    
    @GetMapping("/buscar/nombre/{nombre}")
    public List<EmpleadoBean> searchByLastName(@PathVariable String nombre){
        return repository.findByNombre(nombre);
    }

    @GetMapping("/buscar/puesto/{puesto}")
    public List<EmpleadoBean> searchByFirstName(@PathVariable String puesto){
        return repository.findByPuesto(puesto);
    }
    
    @PostMapping("")
    public void insert(@RequestBody EmpleadoBean emplado) {
        repository.save(emplado);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@RequestParam String id) {
    	Optional<EmpleadoBean> deleteCustomer = repository.findById(id);
        repository.deleteById(id);
    }
}
