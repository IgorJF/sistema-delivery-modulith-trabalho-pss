package br.ufes.cliente.internal.service;


import br.ufes.cliente.ClienteDTO;
import br.ufes.cliente.ClienteService;
import br.ufes.cliente.internal.dao.ClienteDAOImpl;
import br.ufes.cliente.internal.dao.IClienteDAO;
import br.ufes.cliente.internal.model.Cliente;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final IClienteDAO clienteDAO;

    public ClienteServiceImpl() {
        this.clienteDAO = new ClienteDAOImpl();
    }

    @Override
    public ClienteDTO cadastrar(String nome, String endereco) {

        validarDados(nome, endereco);

        Cliente cliente = new Cliente(0, nome, endereco);

        clienteDAO.inserir(cliente);

        return converterParaDTO(cliente);

    }

    @Override
    public ClienteDTO atualizar(long id, String nome, String endereco) {

        validarDados(nome, endereco);

        Cliente cliente = buscarEntidadePorId(id);
        cliente.setNome(nome);
        cliente.setEndereco(endereco);

        clienteDAO.atualizar(cliente);

        return converterParaDTO(cliente);

    }

    @Override
    public void excluir(long id) {

        buscarEntidadePorId(id);
        clienteDAO.deletar(id);

    }

    @Override
    public ClienteDTO buscarPorId(long id) {

        Cliente cliente = buscarEntidadePorId(id);
        return converterParaDTO(cliente);

    }

    @Override
    public List<ClienteDTO> listar() {

        List<ClienteDTO> dtos = new ArrayList<>();
        for (Cliente cliente : clienteDAO.listar()){
            dtos.add(converterParaDTO(cliente));
        }

        return dtos;
    }

    private Cliente buscarEntidadePorId(long id) {
        Optional<Cliente> cliente = clienteDAO.buscarPorId(id);
        if (cliente.isEmpty()) {
            throw new RuntimeException("Cliente nao encontrado com ID: " + id);
        }
        return cliente.get();
    }


    private void validarDados(String nome, String endereco) {

        if (nome == null || nome.trim().isEmpty()) {
            throw new RuntimeException("O nome é obrigatório.");
        }

        if (endereco == null || endereco.trim().isEmpty()) {
            throw new RuntimeException("O endereço é obrigatório.");
        }
    }

    private ClienteDTO converterParaDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getId(), cliente.getNome(), cliente.getEndereco());
    }

}
