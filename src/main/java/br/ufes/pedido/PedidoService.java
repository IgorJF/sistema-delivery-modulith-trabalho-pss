package br.ufes.pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoService {

    PedidoDTO criarPedido(long clienteId, List<ItemPedidoDTO> itens);
    PedidoDTO realizarPagamento(long pedidoId, String tipoPagamento);
    Optional<PedidoDTO> buscarPorId(long id);
    List<PedidoDTO> listar();
    void cancelar(long id);

}
