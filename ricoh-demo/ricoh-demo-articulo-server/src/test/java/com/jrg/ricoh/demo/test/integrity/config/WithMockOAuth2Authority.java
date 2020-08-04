package com.jrg.ricoh.demo.test.integrity.config;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockOAuth2AuthoritySecurityContextFactory.class)
public @interface WithMockOAuth2Authority {

    String authority() default "";
    
}