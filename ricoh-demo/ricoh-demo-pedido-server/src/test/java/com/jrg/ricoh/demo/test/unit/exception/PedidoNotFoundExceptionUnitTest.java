package com.jrg.ricoh.demo.test.unit.exception;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jrg.ricoh.demo.exception.PedidoNotFoundException;

@SpringBootTest
public class PedidoNotFoundExceptionUnitTest {

	private final static String ID = "1";
	
	@Test
	public void basicTest() {
		try {
			throw new PedidoNotFoundException(ID);
		} catch (PedidoNotFoundException e) {
			assertTrue(e.getClass().equals(PedidoNotFoundException.class));
			assertTrue(e.getId().equals(ID));
		}
	}
}
