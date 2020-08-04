package com.jrg.ricoh.demo.test.unit.exception;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jrg.ricoh.demo.exception.ArticuloNotFoundException;

@SpringBootTest
public class ArticuloNotFoundExceptionUnitTest {

	private final static String ID = "1";
	
	@Test
	public void basicTest() {
		try {
			throw new ArticuloNotFoundException(ID);
		} catch (ArticuloNotFoundException e) {
			assertTrue(e.getClass().equals(ArticuloNotFoundException.class));
			assertTrue(e.getId().equals(ID));
		}
	}
}
