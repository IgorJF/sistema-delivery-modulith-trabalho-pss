package br.ufes.item.internal;

import br.ufes.item.ItemDTO;
import br.ufes.item.ItemService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemDAO itemDAO;

    public ItemServiceImpl() {
        this.itemDAO = new ItemDAOImpl();
    }

    @Override
    public ItemDTO cadastrar(String descricao, double preco, int quantidade) {
        validarDados(descricao, preco, quantidade);

        Item item = new Item(0, descricao, preco, quantidade);
        itemDAO.inserir(item);

        return converterParaDTO(item);
    }

    @Override
    public ItemDTO atualizar(long id, String descricao, double preco, int quantidade) {
        validarDados(descricao, preco, quantidade);

        Item item = buscarEntidadePorId(id);
        item.setDescricao(descricao);
        item.setPreco(preco);
        item.setQuantidade(quantidade);

        itemDAO.atualizar(item);
        return converterParaDTO(item);
    }

    @Override
    public void excluir(long id) {
        buscarEntidadePorId(id);
        itemDAO.deletar(id);
    }

    @Override
    public ItemDTO buscarPorId(long id) {
        Item item = buscarEntidadePorId(id);
        return converterParaDTO(item);
    }

    @Override
    public List<ItemDTO> listar() {
        List<ItemDTO> dtos = new ArrayList<>();
        for (Item item : itemDAO.listar()) {
            dtos.add(converterParaDTO(item));
        }
        return dtos;
    }

    private Item buscarEntidadePorId(long id) {
        Optional<Item> item = itemDAO.buscarPorId(id);
        if (item.isEmpty()) {
            throw new RuntimeException("Item nao encontrado com ID: " + id);
        }
        return item.get();
    }

    private void validarDados(String descricao, double preco, int quantidade) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new RuntimeException("A descricao eh obrigatoria.");
        }
        if (preco <= 0) {
            throw new RuntimeException("O preco deve ser maior que zero");
        }
        if (quantidade < 0) {
            throw new RuntimeException("A quantidade nao pode ser negativa");
        }
    }

    private ItemDTO converterParaDTO(Item item) {
        return new ItemDTO(item.getId(), item.getDescricao(), item.getPreco(), item.getQuantidade());
    }
}