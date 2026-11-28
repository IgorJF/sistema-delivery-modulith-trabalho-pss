package br.ufes.pedido;

import java.util.List;

public class PedidoDTO {

    private long id;
    private long clienteId;
    private String nomeCliente;
    private String enderecoCliente;
    private List<ItemPedidoDTO> itens;
    private double valorTotal;
    private StatusPedido status;
    private String tipoPagamento;

    public PedidoDTO(long id, long clienteId, String nomeCliente, String enderecoCliente, List<ItemPedidoDTO> itens,
                     double valorTotal, StatusPedido status, String tipoPagamento) {
        this.id = id;
        this.clienteId = clienteId;
        this.nomeCliente = nomeCliente;
        this.enderecoCliente = enderecoCliente;
        this.itens = itens;
        this.valorTotal = valorTotal;
        this.status = status;
        this.tipoPagamento = tipoPagamento;
    }

    public long getId() {
        return id;
    }

    public long getClienteId() {
        return clienteId;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getEnderecoCliente() {
        return enderecoCliente;
    }

    public List<ItemPedidoDTO> getItens() {
        return itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    @Override
    public String toString(){

        return "\n-------------------------------------------------------------\n"+"Pedido: #"+getId()+"\n"+"Cliente: #"+getClienteId()+" - "
                +getNomeCliente()+"\n"+"Endereço: "+getEnderecoCliente()+"\n"+"Itens: "
                +getItens()+"\n"+"Valor total: R$ "+getValorTotal()+"\n"+"Pagamento: "+getTipoPagamento()
                +"\n-------------------------------------------------------------\n";

    }
}
