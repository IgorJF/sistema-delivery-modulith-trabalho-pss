package br.ufes.pedido.internal.dao;

import br.ufes.pedido.internal.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface IPedidoDAO {


    void salvar(Pedido pedido);
    void atualizar(Pedido pedido);
    Optional<Pedido> buscarPorId(long id);
    List<Pedido> listar();


}
