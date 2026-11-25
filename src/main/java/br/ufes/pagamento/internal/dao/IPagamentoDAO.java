package br.ufes.pagamento.internal.dao;

import br.ufes.pagamento.internal.model.Pagamento;

import java.util.List;

public interface IPagamentoDAO {
    void inserirPagamento(Pagamento pagamento);
    Pagamento buscarPorPedidoId(long pedidoId);
    List<Pagamento> listarPagamentos();
}
