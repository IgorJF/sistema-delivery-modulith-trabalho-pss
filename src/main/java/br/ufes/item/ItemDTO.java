package br.ufes.item;

public class ItemDTO {
    private long id;
    private String descricao;
    private double preco;
    private int quantidade;

    public ItemDTO(long id, String descricao, double preco, int quantidade) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public long getId() {
        return id;
    }
}
