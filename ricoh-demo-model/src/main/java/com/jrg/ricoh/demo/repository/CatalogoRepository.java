package com.jrg.ricoh.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.jrg.ricoh.demo.entity.Catalogo;

public interface CatalogoRepository extends CrudRepository<Catalogo, Integer> {
	
	Catalogo findByDescripcion(String descripcion);
	
}
