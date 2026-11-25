package br.ufes.pagamento.internal.model;

public class Pagamento {
    private long pagamentoId;
    private long pedidoId;
    private double valorCobrado;
    private String tipoPagamento;
    private String status;

    public Pagamento(long pedidoId, double valorCobrado, String tipoPagamento, String status) {
        this.pedidoId = pedidoId;
        this.valorCobrado = valorCobrado;
        this.tipoPagamento = tipoPagamento;
        this.status = status;
    }

    public long getPagamentoId() { return pagamentoId; }

    public void setPagamentoId(long pagamentoId) { this.pagamentoId = pagamentoId; }

    public long getPedidoId() { return pedidoId; }

    public double getValorCobrado() { return valorCobrado; }

    public String getTipoPagamento() { return tipoPagamento; }

    public String getStatus() { return status; }
}
