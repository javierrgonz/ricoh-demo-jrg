package com.jrg.ricoh.demo.service;

import java.util.List;

import com.jrg.ricoh.demo.entity.Articulo;

public interface ArticuloService {

    public Articulo findById(int theId);

	public List<Articulo> findAll();

}
