package com.jrg.ricoh.demo.test.integrity.config;

import org.springframework.security.core.GrantedAuthority;

public interface RoleAdminAuthority extends GrantedAuthority {

	@Override
	public default String getAuthority() {
		return "ROLE_ADMIN";
	}

}
