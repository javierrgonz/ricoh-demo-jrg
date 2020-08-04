package com.jrg.ricoh.demo.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Data
@RequiredArgsConstructor
@ToString
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String cliente;
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "pedido_articulo", joinColumns = @JoinColumn(name = "id_pedido"), inverseJoinColumns = @JoinColumn(name = "id_articulo"))
	List<Articulo> articulos;
	
	public Pedido(String cliente, List<Articulo> articulos) {
		super();
		this.cliente = cliente;
		this.articulos = articulos;
	}
}