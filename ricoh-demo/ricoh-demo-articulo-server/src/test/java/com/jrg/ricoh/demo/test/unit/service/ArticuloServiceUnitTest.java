package com.jrg.ricoh.demo.test.unit.service;

import static org.junit.Assert.assertEquals;
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

import com.jrg.ricoh.demo.entity.Articulo;
import com.jrg.ricoh.demo.repository.ArticuloRepository;
import com.jrg.ricoh.demo.service.ArticuloService;
import com.jrg.ricoh.demo.service.impl.ArticuloServiceImpl;

@SpringBootTest
public class ArticuloServiceUnitTest {

	private final Articulo articuloAux = new Articulo();
	private final List<Articulo> articulosAux = Arrays.asList(this.articuloAux);
	private final int ID_AUX = 1;
	Optional<Articulo> optionalArticuloAux = Optional.of(this.articuloAux);

	@Mock
	private ArticuloRepository articuloRepository;

	private ArticuloService articuloService;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		this.articuloService = new ArticuloServiceImpl(this.articuloRepository);
	}

	@Test
	public void findByIdOKTest() {

		when(this.articuloRepository.findById(Mockito.anyInt()))
				.thenReturn(this.optionalArticuloAux);

		Articulo result = this.articuloService.findById(this.ID_AUX);
		assertEquals(this.articuloAux, result);
	}

	@Test(expected = NullPointerException.class)
	public void findByIdKOTest() {

		when(this.articuloRepository.findById(Mockito.anyInt()))
				.thenReturn(null);

		this.articuloService.findById(this.ID_AUX);
	}

	@Test
	public void findAllOKTest() {

		when(this.articuloRepository.findAll()).thenReturn(this.articulosAux);

		List<Articulo> articulos = this.articuloService.findAll();
		assertEquals(this.articulosAux, articulos);
	}

	@Test(expected = NullPointerException.class)
	public void findAllKOTest() {

		when(this.articuloRepository.findAll()).thenReturn(null);

		this.articuloService.findAll();
	}

}
