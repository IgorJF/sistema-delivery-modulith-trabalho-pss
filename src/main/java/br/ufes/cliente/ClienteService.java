package br.ufes.cliente;

import java.util.List;

public interface ClienteService {

    ClienteDTO cadastrar(String nome, String endereco);
    ClienteDTO atualizar(long id, String nome, String endereco);
    void excluir(long id);
    ClienteDTO buscarPorId(long id);
    List<ClienteDTO> listar();

}


