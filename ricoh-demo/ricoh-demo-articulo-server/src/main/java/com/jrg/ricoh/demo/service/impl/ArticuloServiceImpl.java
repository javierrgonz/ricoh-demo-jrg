package com.jrg.ricoh.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.jrg.ricoh.demo.entity.Articulo;
import com.jrg.ricoh.demo.repository.ArticuloRepository;
import com.jrg.ricoh.demo.service.ArticuloService;

@Service
public class ArticuloServiceImpl implements ArticuloService {

	private final ArticuloRepository articuloRepository;

	public ArticuloServiceImpl(final ArticuloRepository articuloRepository) {
		super();
		this.articuloRepository = articuloRepository;
	}

	@Override
	public Articulo findById(int id) {
		return this.articuloRepository.findById(id)
				.orElseThrow(NullPointerException::new);
	}

	@Override
	public List<Articulo> findAll() {
		List<Articulo> articulos = this.articuloRepository.findAll();
		if (articulos == null || CollectionUtils.isEmpty(articulos))
			throw new NullPointerException();
		return articulos;
	}

}
