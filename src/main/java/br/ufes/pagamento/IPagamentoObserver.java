package br.ufes.pagamento;

public interface IPagamentoObserver {
    void notificarAprovacao(PagamentoAprovadoEvento eventoAprovado);
    void notificarRecusa(PagamentoRecusadoEvento eventoRecusado);
}
