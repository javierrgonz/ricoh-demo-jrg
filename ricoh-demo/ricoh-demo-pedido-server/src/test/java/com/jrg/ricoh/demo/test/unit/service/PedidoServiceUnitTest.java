package com.jrg.ricoh.demo.test.unit.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.jrg.ricoh.demo.dto.PedidoDto;
import com.jrg.ricoh.demo.entity.Articulo;
import com.jrg.ricoh.demo.entity.Pedido;
import com.jrg.ricoh.demo.repository.ArticuloRepository;
import com.jrg.ricoh.demo.repository.PedidoCrudRepository;
import com.jrg.ricoh.demo.service.PedidoService;
import com.jrg.ricoh.demo.service.impl.PedidoServiceImpl;

@SpringBootTest
public class PedidoServiceUnitTest {

	private Pedido pedidoAux = new Pedido();
	private final List<Pedido> pedidosAux = Arrays.asList(this.pedidoAux);
	private final Articulo articuloAux = new Articulo();
	private final List<Articulo> articulosAux = Arrays.asList(this.articuloAux);
	private final int ID_AUX = 1;
	private final List<Integer> idArticulosAux = Arrays.asList(this.ID_AUX);
	private final PedidoDto pedidoDto = new PedidoDto();
	Optional<Pedido> optional = Optional.of(this.pedidoAux);
	Optional<Pedido> emptyOptional = Optional.ofNullable(null);

	@Mock
	private ArticuloRepository articuloRepository;

	@Mock
	private PedidoCrudRepository pedidoCrudRepository;

	private PedidoService pedidoService;

	@Before
	public void before() {

		this.pedidoDto.setId(this.ID_AUX);
		this.pedidoDto.setArticulos(this.idArticulosAux);
		
		// Mocks
		MockitoAnnotations.initMocks(this);
		this.pedidoService = new PedidoServiceImpl(this.pedidoCrudRepository,
				this.articuloRepository);
	}

	@Test
	public void findByIdTest() {

		when(this.pedidoCrudRepository.findById(Mockito.anyInt()))
				.thenReturn(this.optional);

		Pedido result = this.pedidoService.findById(this.ID_AUX);
		assertEquals(this.pedidoAux, result);
	}

	@Test
	public void findAllOKTest() {

		when(this.pedidoCrudRepository.findAll()).thenReturn(this.pedidosAux);

		List<Pedido> pedidos = this.pedidoService.findAll();
		assertEquals(this.pedidosAux, pedidos);
	}

	@Test(expected = NullPointerException.class)
	public void findAllKOTest() {

		when(this.pedidoCrudRepository.findAll()).thenReturn(null);
		List<Pedido> pedidos = this.pedidoService.findAll();
	}

	@Test
	public void deleteByIdOKTest() {

		when(this.pedidoCrudRepository.findById(Mockito.any()))
			.thenReturn(this.optional)
			.thenReturn(this.emptyOptional);
		doNothing().when(this.pedidoCrudRepository).deleteById(Mockito.any());

		boolean deleted = this.pedidoService.deleteById(this.ID_AUX);
		assertTrue(deleted);
	}
	
	@Test
	public void deleteByIdKOTest() {

		when(this.pedidoCrudRepository.findById(Mockito.any()))
			.thenReturn(this.emptyOptional);
		boolean deleted = this.pedidoService.deleteById(this.ID_AUX);
		assertFalse(deleted);
	}

	@Test
	public void saveTest() {

		when(this.pedidoCrudRepository.save(Mockito.any()))
				.thenReturn(this.pedidoAux);
		when(this.articuloRepository.findAllById(Mockito.any()))
				.thenReturn(this.articulosAux);

		Pedido pedido = this.pedidoService.createPedido(this.pedidoDto);
		assertEquals(this.pedidoAux, pedido);
	}

	@Test
	public void updatePedidoOKTest() {

		when(this.pedidoCrudRepository.findById(Mockito.any()))
				.thenReturn(this.optional);
		when(this.articuloRepository.findAllById(Mockito.any()))
				.thenReturn(this.articulosAux);

		this.pedidoService.updatePedido(this.pedidoDto);
		verify(this.pedidoCrudRepository).findById(this.pedidoDto.getId());
		verify(this.articuloRepository).findAllById(this.idArticulosAux);
	}

	@Test(expected = NullPointerException.class)
	public void updatePedidoKOTest() {

		when(this.pedidoCrudRepository.findById(Mockito.any()))
				.thenReturn(null);
		this.pedidoService.updatePedido(this.pedidoDto);
	}
}
