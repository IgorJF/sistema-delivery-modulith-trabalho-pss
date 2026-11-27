package br.ufes.cliente;

public class ClienteDTO {

    private final Long id;
    private final String nome;
    private final String endereco;

    public ClienteDTO(Long id, String nome, String endereco) {
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


    @Override
    public String toString(){

        return "Cliente: #" +getId()+ " - " +getNome()+ " - Endereço: "+getEndereco();

    }

}

