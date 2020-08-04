package com.jrg.ricoh.demo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustomPrincipal implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String email;
	
	public CustomPrincipal(String username, String email) {
		this.username = username;
		this.email = email;	
	}
}