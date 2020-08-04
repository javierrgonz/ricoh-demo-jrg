package com.jrg.ricoh.demo.test.unit.controller;

import static org.junit.Assert.assertEquals;
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

import com.jrg.ricoh.demo.controller.ArticuloController;
import com.jrg.ricoh.demo.entity.Articulo;
import com.jrg.ricoh.demo.service.ArticuloService;

@SpringBootTest
public class ArticuloControllerUnitTest {

    private final Articulo articuloAux = new Articulo();
    private final List<Articulo> articulosAux = Arrays.asList(this.articuloAux);
    private final ResponseEntity<Articulo> articuloResponseEntityAux = ResponseEntity.ok().body(articuloAux);
    private final ResponseEntity<List<Articulo>> articulosResponseEntityAux = ResponseEntity.ok().body(articulosAux);
    private final ResponseEntity<Articulo> notFoundArticuloResponseEntityAux = ResponseEntity.notFound().build();
    private final ResponseEntity<List<Articulo>> notFoundArticulosResponseEntityAux = ResponseEntity.notFound().build();
    private final int ID_AUX = 1;

    @Mock
    private ArticuloService articuloService;

    private ArticuloController articuloController;

    @Before
    public void before() {
	MockitoAnnotations.initMocks(this);
	this.articuloController = new ArticuloController(this.articuloService);
    }

    @Test
    public void getArticulosOKTest() {
	when(this.articuloService.findAll()).thenReturn(this.articulosAux);
	assertEquals(this.articulosResponseEntityAux, this.articuloController.getArticulos());
    }

    @Test
    public void getArticulosKOTest() {
	when(this.articuloService.findAll()).thenThrow(new NullPointerException());
	assertEquals(this.notFoundArticulosResponseEntityAux, this.articuloController.getArticulos());
    }

    @Test
    public void getArticuloByIdOKTest() {
	when(this.articuloService.findById(Mockito.anyInt())).thenReturn(this.articuloAux);
	assertEquals(this.articuloResponseEntityAux, this.articuloController.getArticuloById(this.ID_AUX));
    }

    @Test
    public void getArticuloByIdKOTest() {
	when(this.articuloService.findById(Mockito.anyInt())).thenThrow(new NullPointerException());
	assertEquals(this.notFoundArticuloResponseEntityAux, this.articuloController.getArticuloById(this.ID_AUX));
    }
}