package br.ufes.cliente.internal.model;

public class Cliente {

    private long id;
    private String nome;
    private String endereco;

    public Cliente(long id, String nome, String endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString(){

        return "Cliente: #" +getId()+ " - " +getNome()+ " - Endereço: "+getEndereco();

    }

}

