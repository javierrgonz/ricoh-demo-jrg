package com.jrg.ricoh.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ArticuloNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 482273000601630872L;

	private String id;

	public ArticuloNotFoundException(String id) {
		super(String.format("Articulo '%s' not found", id));
		this.id = id;
	}

	public String getId() {
		return this.id;
	}
}
