package br.ufes.pedido.internal.model;

import br.ufes.pedido.StatusPedido;

import java.util.List;

public class Pedido {

    private long id;
    private long clienteId;
    private String nomeCliente;
    private String enderecoCliente;
    private List<ItemPedido> itens;
    private double valorTotal;
    private StatusPedido status;
    private String tipoPagamento;

    public Pedido(long id, long clienteId, String nomeCliente, String enderecoCliente, List<ItemPedido> itens, double valorTotal,
                  StatusPedido status, String tipoPagamento) {
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

    public List<ItemPedido> getItens() {
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

    public void setId(long id) {
        this.id = id;
    }

    public void setClienteId(long clienteId) {
        this.clienteId = clienteId;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setEnderecoCliente(String enderecoCliente) {
        this.enderecoCliente = enderecoCliente;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }


    public double calcularValorTotal(){

        double valorTotal = 0;
        for(ItemPedido itemPedido : itens){

            valorTotal += itemPedido.getSubTotal();

        }

        return valorTotal;

    }

    @Override
    public String toString(){
        //(cliente, lista de itens, total, status atual e forma de pagamento)
        return "\n-------------------------------------------------------------\n"+"Pedido: #"+getId()+"\n"+"Cliente: #"+getClienteId()+" - "
                +getNomeCliente()+"\n"+"Endereço: "+getEnderecoCliente()+"\n"+"Itens: "
                +getItens()+"\n"+"Valor total: R$ "+getValorTotal()+"\n"+"Pagamento: "+getTipoPagamento()
                +"\n-------------------------------------------------------------\n";

    }
}

