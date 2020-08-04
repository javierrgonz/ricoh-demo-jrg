package com.jrg.ricoh.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrg.ricoh.demo.entity.Articulo;

public interface ArticuloRepository extends JpaRepository<Articulo, Integer> {}
