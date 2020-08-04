package com.jrg.ricoh.demo.test.unit.dto;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.jrg.ricoh.demo.dto.PedidoDto;

@SpringBootTest
public class PedidoDtoUnitTest {

	private final static Integer ID = 1;
	private final static String CLIENTE = "cliente";
	private final static List<Integer> ARTICULOS = Arrays.asList(ID);
		
	@Test
	public void constructorTest() {
		PedidoDto pedidoDto = new PedidoDto();
		assertNotNull(pedidoDto);
	}
	
	@Test
	public void setterAndGetterTest() {
		PedidoDto pedidoDto = new PedidoDto();
		pedidoDto.setId(ID);
		pedidoDto.setCliente(CLIENTE);
		pedidoDto.setArticulos(ARTICULOS);
		assertNotNull(pedidoDto);
		assertNotNull(pedidoDto.getId());
		assertNotNull(pedidoDto.getCliente());
		assertNotNull(pedidoDto.getArticulos());
	}
}
