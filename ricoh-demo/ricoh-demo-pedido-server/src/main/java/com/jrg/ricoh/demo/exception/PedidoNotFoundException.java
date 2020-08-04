package com.jrg.ricoh.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PedidoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 482273000601630872L;

	private String id;

	public PedidoNotFoundException(String id) {
		super(String.format("Pedido '%s' not found", id));
		this.id = id;
	}

	public String getId() {
		return this.id;
	}
}
