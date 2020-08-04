package com.jrg.ricoh.demo.test.integrity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.jrg.ricoh.demo.repository")
@PropertySource("application.yml")
@EnableTransactionManagement
public class H2JpaConfig {
 
}