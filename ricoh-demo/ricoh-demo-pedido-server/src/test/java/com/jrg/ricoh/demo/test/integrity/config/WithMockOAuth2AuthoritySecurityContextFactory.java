package com.jrg.ricoh.demo.test.integrity.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.jrg.ricoh.demo.test.integrity.config.WithMockOAuth2Authority;

public class WithMockOAuth2AuthoritySecurityContextFactory implements WithSecurityContextFactory<WithMockOAuth2Authority> {

	@Override
	public SecurityContext createSecurityContext(WithMockOAuth2Authority mockOAuth2Scope) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		List<GrantedAuthority> authorities = new ArrayList<>();
		Set<String> rolesWithPrefix = new HashSet<>();
		if (mockOAuth2Scope.authority() != null) {
			rolesWithPrefix.add(mockOAuth2Scope.authority());	
		}
		authorities = AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));
		OAuth2Request request = new OAuth2Request(null, null, authorities, true, null, null, null, null, null);
		Authentication auth = new OAuth2Authentication(request, null);
		context.setAuthentication(auth);
		return context;
	}
}