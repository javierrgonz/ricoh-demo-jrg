package com.jrg.ricoh.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.jrg.ricoh.demo.dto.PedidoDto;
import com.jrg.ricoh.demo.entity.Pedido;
import com.jrg.ricoh.demo.repository.ArticuloRepository;
import com.jrg.ricoh.demo.repository.PedidoCrudRepository;
import com.jrg.ricoh.demo.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

	private final PedidoCrudRepository pedidoCrudRepository;
	private final ArticuloRepository articuloRepository;
	
	public PedidoServiceImpl(PedidoCrudRepository pedidoCrudRepository, ArticuloRepository articuloRepository) {
		super();
		this.pedidoCrudRepository = pedidoCrudRepository;
		this.articuloRepository = articuloRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Pedido findById(int id) {
		return this.pedidoCrudRepository.findById(id)
				.orElseThrow(NullPointerException::new);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pedido> findAll() {
		List<Pedido> pedidos = this.pedidoCrudRepository.findAll();
		if (pedidos == null || CollectionUtils.isEmpty(pedidos))
			throw new NullPointerException();
		return pedidos;
	}

	@Override
	public boolean deleteById(int id) {
		if (!this.pedidoCrudRepository.findById(id).isPresent()) {
			return false;
		} 
		this.pedidoCrudRepository.deleteById(id);
		return (!this.pedidoCrudRepository.findById(id).isPresent());
	}

	@Override
	public Pedido createPedido(PedidoDto pedidoDto) {
		return this.pedidoCrudRepository.save(new Pedido(pedidoDto.getCliente(),
				this.articuloRepository.findAllById(pedidoDto.getArticulos())));
	}

	@Override
	@Transactional
	public void updatePedido(PedidoDto pedidoDto) {
		Pedido updatablePedido = this.pedidoCrudRepository
				.findById(pedidoDto.getId())
				.orElseThrow(NullPointerException::new);
		updatablePedido.setCliente(pedidoDto.getCliente());
		updatablePedido.setArticulos(
				this.articuloRepository.findAllById(pedidoDto.getArticulos()));
		// No necesita de manera explicita el repository.save
	}
	
}
