package br.ufes.cliente.internal.dao;

import br.ufes.cliente.internal.model.Cliente;

import java.util.*;

public class ClienteDAOImpl implements IClienteDAO {

    private final List<Cliente> clientes;
    private long proximoId;

    public ClienteDAOImpl() {
        this.clientes = new ArrayList<>();
        this.proximoId = 1;
    }

    @Override
    public void inserir(Cliente cliente) {

        cliente.setId(proximoId++);
        clientes.add(cliente);

    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {

        for (Cliente cliente : clientes) {
            if (Objects.equals(cliente.getId(), id)) {
                return Optional.of(cliente);
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Cliente> listar() {

        return new ArrayList<>(clientes);
    }

    @Override
    public void atualizar(Cliente cliente) {

        for (int i = 0; i < clientes.size(); i++) {
            if (Objects.equals(clientes.get(i).getId(), cliente.getId())) {
                clientes.set(i, cliente);
                return;
            }
        }

    }

    @Override
    public void deletar(long id) {

        Iterator<Cliente> it = clientes.iterator();
        while (it.hasNext()){

            Cliente cliente = it.next();
            if(cliente.getId() == id){

                it.remove();
                return;

            }

        }

    }

}
