package com.jrg.ricoh.demo.service;

import java.util.List;

import com.jrg.ricoh.demo.dto.PedidoDto;
import com.jrg.ricoh.demo.entity.Pedido;

public interface PedidoService {

    public Pedido findById(int theId);

    public List<Pedido> findAll();

    public boolean deleteById(int theId);

    public Pedido createPedido(PedidoDto pedidoDto);

    public void updatePedido(PedidoDto pedidoDto);
}
