package br.ufes.pagamento;

public class PagamentoDTO {
    private long pagamentoId;
    private long pedidoId;
    private double valorCobrado;
    private String tipoPagamento;
    private String status;

    public PagamentoDTO(long pagamentoId, long pedidoId, double valorCobrado, String tipoPagamento, String status) {
        this.pagamentoId = pagamentoId;
        this.pedidoId = pedidoId;
        this.valorCobrado = valorCobrado;
        this.tipoPagamento = tipoPagamento;
        this.status = status;
    }

    //getters necessários
    public long getPagamentoId() { return pagamentoId; }

    public long getPedidoId(){ return pedidoId; }

    public double getValorCobrado() { return valorCobrado; }

    public String getTipoPagamento(){ return tipoPagamento; }

    public String getStatus(){ return status; }
}
