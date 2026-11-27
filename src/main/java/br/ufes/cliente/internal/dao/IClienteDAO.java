package br.ufes.cliente.internal.dao;

import br.ufes.cliente.internal.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface IClienteDAO {

    void inserir(Cliente cliente);
    Optional<Cliente> buscarPorId(Long id);
    List<Cliente> listar();
    void atualizar(Cliente cliente);
    void deletar(long id);

}

