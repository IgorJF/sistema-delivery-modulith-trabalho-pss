package br.ufes.item;

import java.util.List;

public interface ItemService {
    ItemDTO cadastrar(String descricao, double preco, int quantidade);
    ItemDTO atualizar(long id, String descricao, double preco, int quantidade);
    void excluir(long id);
    ItemDTO buscarPorId(long id);
    List<ItemDTO> listar();
}
