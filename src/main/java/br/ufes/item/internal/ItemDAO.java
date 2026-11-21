package br.ufes.item.internal;

import java.util.List;
import java.util.Optional;

public interface ItemDAO {
    void inserir(Item item);
    Optional<Item> buscarPorId(long id);
    List<Item> listar();
    void atualizar(Item item);
    void deletar(long id);
}
