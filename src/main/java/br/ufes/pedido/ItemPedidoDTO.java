package br.ufes.pedido;

public class ItemPedidoDTO {

    private long id;
    private String descricaoItem;
    private int quantidade;
    private double precoUnitario;
    private double subTotal;

    public ItemPedidoDTO(long id, String descricaoItem, int quantidade, double precoUnitario, double subTotal) {
        this.id = id;
        this.descricaoItem = descricaoItem;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subTotal = subTotal;
    }

    public long getId() {
        return id;
    }

    public String getDescricaoItem() {
        return descricaoItem;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public double getSubTotal() {
        return subTotal;
    }

    @Override
    public String toString(){

        return "#"+getId()+" - "+getDescricaoItem()+" - "+getPrecoUnitario()+" x "+getQuantidade()+" : "+getSubTotal();

    }

}
