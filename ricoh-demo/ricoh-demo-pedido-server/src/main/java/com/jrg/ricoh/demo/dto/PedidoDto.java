package com.jrg.ricoh.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class PedidoDto {

	private int id;
	private String cliente;
	private List<Integer> articulos;

}
