package com.jrg.ricoh.demo.test.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jrg.ricoh.demo.controller.PedidoController;
import com.jrg.ricoh.demo.dto.PedidoDto;
import com.jrg.ricoh.demo.entity.Pedido;
import com.jrg.ricoh.demo.service.PedidoService;

@SpringBootTest
public class PedidoControllerUnitTest {

	private Pedido pedidoAux = new Pedido();
	private List<Pedido> pedidosAux = Arrays.asList(this.pedidoAux);
	private int ID_AUX = 1;
	private final ResponseEntity<Pedido> pedidoResponseEntityAux = ResponseEntity.ok().body(pedidoAux);
	private final ResponseEntity<List<Pedido>> pedidosResponseEntityAux = ResponseEntity.ok().body(pedidosAux);
	private final ResponseEntity<Pedido> notFoundPedidoResponseEntityAux = ResponseEntity.notFound().build(); 
	private final ResponseEntity<List<Pedido>> notFoundPedidosResponseEntityAux = ResponseEntity.notFound().build();
	private PedidoDto pedidoDto = new PedidoDto();
	

	@Mock
	private PedidoService pedidoService;

	private PedidoController pedidoController;
	
	@Before
	public void before() {
		this.pedidoAux.setId(this.ID_AUX);
		MockitoAnnotations.initMocks(this);
		this.pedidoController = new PedidoController(this.pedidoService);
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	@Test
	public void createPedidoTest() {
		when(this.pedidoService.createPedido(Mockito.any())).thenReturn(this.pedidoAux);
		ResponseEntity<Object> responseEntity = this.pedidoController.createPedido(this.pedidoDto);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
		assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
	}

	@Test
	public void updatePedidoTestOK() {
		doNothing().when(this.pedidoService).updatePedido(Mockito.any());
		ResponseEntity<Object> responseEntity = this.pedidoController.updatePedido(this.pedidoDto);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(204);
	}

	@Test
	public void updatePedidoTestKO() {
		doThrow(new NullPointerException()).when(this.pedidoService).updatePedido(Mockito.any());
		ResponseEntity<Object> responseEntity = this.pedidoController.updatePedido(this.pedidoDto);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
	}

	@Test
	public void getPedidosOKTest() {
		when(this.pedidoService.findAll()).thenReturn(this.pedidosAux);
		assertEquals(this.pedidosResponseEntityAux, this.pedidoController.getPedidos());
	}
	
	@Test
	public void getPedidosKOTest() {
		when(this.pedidoService.findAll()).thenThrow(new NullPointerException());
		assertEquals(this.notFoundPedidosResponseEntityAux, this.pedidoController.getPedidos());
	}
	
	@Test
	public void getPedidoByIdOKTest() {
		when(this.pedidoService.findById(Mockito.anyInt())).thenReturn(this.pedidoAux);
		assertEquals(this.pedidoResponseEntityAux, this.pedidoController.getPedidoById(this.ID_AUX));
	}
	
	@Test
	public void getPedidoByIdKOTest() {
		when(this.pedidoService.findById(Mockito.anyInt())).thenThrow(new NullPointerException());
		assertEquals(this.notFoundPedidoResponseEntityAux, this.pedidoController.getPedidoById(this.ID_AUX));
	}

//	@Test
//	public void deletePedidoByIdKOTest() throws Exception {
//		// TODO
//		//	when(this.pedidoService.deleteById(Mockito.any())).thenReturn(false);
//		//	ResponseEntity<Object> response = this.pedidoController.deletePedidoById(this.ID_AUX);
//		//	assertThat(response.getStatusCodeValue()).isEqualTo(400);
//	}
//	
//	@Test
//	public void deletePedidoByIdKOTest() throws Exception {
//		 TODO
//			when(this.pedidoService.deleteById(Mockito.any())).thenReturn(false);
//			ResponseEntity<Object> response = this.pedidoController.deletePedidoById(this.ID_AUX);
//			assertThat(response.getStatusCodeValue()).isEqualTo(400);
//	}
}