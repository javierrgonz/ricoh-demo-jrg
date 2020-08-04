package com.jrg.ricoh.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RicohDemoArticuloServer {

    public static Logger LOGGER = LoggerFactory.getLogger(RicohDemoArticuloServer.class);

    public static void main(String[] args) {
	try {
	    SpringApplication.run(RicohDemoArticuloServer.class, args);
	} catch (Throwable e) {
	    // Avoid SilentExitException due to
	    // https://github.com/spring-projects/spring-boot/issues/3100
	    if (e.getClass().getName().contains("SilentExitException")) {
		LOGGER.info("Spring is restarting the main thread - See spring-boot-devtools");
	    } else {
		LOGGER.error("Application crashed!", e);
	    }
	}
    }
}
