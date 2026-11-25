package br.ufes.pagamento;

public class PagamentoAprovadoEvento {
    private final long pedidoId;

    public PagamentoAprovadoEvento(long pedidoId){
        this.pedidoId = pedidoId;
    }

    public long getPedidoId(){ return pedidoId; }
}
