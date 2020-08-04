package com.jrg.ricoh.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrg.ricoh.demo.entity.Pedido;

public interface PedidoCrudRepository extends JpaRepository<Pedido, Integer> {}
