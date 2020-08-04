package com.jrg.ricoh.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jrg.ricoh.demo.dto.PedidoDto;
import com.jrg.ricoh.demo.entity.Pedido;
import com.jrg.ricoh.demo.service.PedidoService;

/**
 * The PedidoController
 * @author Javier
 *
 */
@RestController
@RequestMapping("/api/pedido")
public class PedidoController {

	private final PedidoService pedidoService;

	public PedidoController(PedidoService pedidoService) {
		super();
		this.pedidoService = pedidoService;
	}

	/**
	 * Creates a pedido
	 * @param pedidoDto (required) a dto with the info
	 * @return <b>Http created code</b> if created
	 */
	@PostMapping("/")
	@PreAuthorize("hasAnyAuthority('role_admin')")
	public ResponseEntity<Object> createPedido(
			@RequestBody PedidoDto pedidoDto) {
		Pedido savedPedido = this.pedidoService.createPedido(pedidoDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedPedido.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * Updates a pedido 
	 * @param pedidoDto (required) a dto with the info
	 * @return <b>Http noContent code</b> if updated <b>Http not found</b> otherwise
	 */
	@PutMapping("/")
	@PreAuthorize("hasAnyAuthority('role_admin')")
	public ResponseEntity<Object> updatePedido(@RequestBody PedidoDto pedidoDto) {
		try {
			this.pedidoService.updatePedido(pedidoDto);
		} catch (NullPointerException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.noContent().build();
	}

	/**
	 * Delete pedido by id
	 * @param id the id to delete
	 * @return <b>Http accepted code</b> if created <b>Http badRequest code</b> otherwise
	 */
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('role_admin')")
	public ResponseEntity<Object> deletePedidoById(@PathVariable int id) {
		return (this.pedidoService.deleteById(id))
				? ResponseEntity.accepted().build()
				: ResponseEntity.badRequest().build();
	}

	/**
	 * Get pedido by id
	 * @param id (required) the id
	 * @return <b>Http ok code and the pedido</b> if found <b>Http not found code</b> otherwise
	 */
	@GetMapping(path = "/{id}")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	public ResponseEntity<Pedido> getPedidoById(@PathVariable(name = "id", required = true) final int id) {
		Pedido pedido = null;
		try {
			pedido = this.pedidoService.findById(id);
		} catch (NullPointerException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(pedido);
	}

	/**
	 * Get all pedidos
	 * @return <b>Http ok code and a list of pedidos</b> if created <b>Http not found code</b> otherwise
	 */
	@GetMapping("/")
	@PreAuthorize("hasAnyAuthority('role_admin','role_user')")
	public ResponseEntity<List<Pedido>> getPedidos() {
		List<Pedido> pedidos = null;
		try {
			pedidos = this.pedidoService.findAll();
		} catch (NullPointerException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(pedidos);
	}

}
