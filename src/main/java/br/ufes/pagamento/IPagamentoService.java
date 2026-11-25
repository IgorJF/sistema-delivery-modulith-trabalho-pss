package br.ufes.pagamento;

import java.util.List;

public interface IPagamentoService {
    PagamentoDTO processarPagamento(long pedidoId, double valorCobrado, String tipoPagamento);
    PagamentoDTO buscarPorPedidoId(long pedidoId);
    List<PagamentoDTO> listarPagamentos();
    void adicionarObservador(IPagamentoObserver observador);
    void removerObservador(IPagamentoObserver observador);
}
