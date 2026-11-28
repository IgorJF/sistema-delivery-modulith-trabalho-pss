package br.ufes.pedido.internal.model;

public class ItemPedido {

    private long id;
    private String descricaoItem;
    private int quantidade;
    private double precoUnitario;;

    public ItemPedido(long id, String descricaoItem, int quantidade, double precoUnitario) {
        this.id = id;
        this.descricaoItem = descricaoItem;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
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
        return precoUnitario * quantidade;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescricaoItem(String descricaoItem) {
        this.descricaoItem = descricaoItem;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public String toString(){

        return "#"+getId()+" - "+getDescricaoItem()+" - "+getPrecoUnitario()+" x "+getQuantidade()+" : "+getSubTotal();

    }

}
