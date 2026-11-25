package br.ufes.pagamento;

public class PagamentoRecusadoEvento {
    private final long pedidoId;
    private String motivoRecusa;

    public PagamentoRecusadoEvento(long pedidoId, String motivoRecusa){
        this.pedidoId = pedidoId;
        this.motivoRecusa = motivoRecusa;
    }

    public long getPedidoId(){ return pedidoId; }

    public String getMotivoRecusa(){ return motivoRecusa; }

}
