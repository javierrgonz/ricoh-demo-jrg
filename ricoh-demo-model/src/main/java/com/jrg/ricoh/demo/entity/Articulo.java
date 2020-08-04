package com.jrg.ricoh.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Data
@RequiredArgsConstructor
@ToString
public class Articulo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int precio;
    private String descripcion;
    @OneToOne
    @JoinColumn(name = "id_catalogo", referencedColumnName = "id")
    private Catalogo catalogo;

    public Articulo(int precio, String descripcion, Catalogo catalogo) {
		super();
		this.precio = precio;
		this.descripcion = descripcion;
		this.catalogo = catalogo;
	}
}