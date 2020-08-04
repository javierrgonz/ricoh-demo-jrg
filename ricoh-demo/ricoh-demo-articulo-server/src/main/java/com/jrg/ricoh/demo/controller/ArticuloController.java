package com.jrg.ricoh.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jrg.ricoh.demo.entity.Articulo;
import com.jrg.ricoh.demo.service.ArticuloService;

/**
 * The ArticuloController
 * @author Javier
 *
 */
@RestController
@RequestMapping("/api/articulo")
public class ArticuloController {

	private final ArticuloService articuloService;

	public ArticuloController(ArticuloService articuloService) {
		super();
		this.articuloService = articuloService;
	}

	/**
	 * Get the articles
	 * @return <b>Http OK code and a list of articles</b> if there are articles <b>Http NOT FOUND code</b> otherwise
	 */
	@GetMapping("/")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@ResponseBody
	public ResponseEntity<List<Articulo>> getArticulos() {
		List<Articulo> articulos = null;
		try {
			articulos = this.articuloService.findAll();
		} catch (NullPointerException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(articulos);
	}
	
	/**
	 * Get an article by his id
	 * @param id the id of the article to get
	 * @return <b>Http OK code and a the article</b> if exists <b>Http NOT FOUND code</b> otherwise
	 */
	@GetMapping(path="/{id}")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	@ResponseBody
	public ResponseEntity<Articulo> getArticuloById(@PathVariable(required = true) int id) {
		Articulo articulo = null;
		try {
			articulo =  this.articuloService.findById(id);
		} catch (NullPointerException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(articulo);
	}
}
