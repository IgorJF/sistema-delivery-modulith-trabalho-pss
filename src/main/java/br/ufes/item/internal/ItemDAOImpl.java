package br.ufes.item.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class ItemDAOImpl implements ItemDAO {
    private final List<Item> itens;
    private long proximoId;

    public ItemDAOImpl() {
        this.itens = new ArrayList<>();
        this.proximoId = 1;
    }

    @Override
    public void inserir(Item item) {
        item.setId(proximoId++);
        itens.add(item);
    }

    @Override
    public Optional<Item> buscarPorId(long id) {
        for (Item item : itens) {
            if (item.getId() == id) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Item> listar() {
        return new ArrayList<>(itens);
    }

    @Override
    public void atualizar(Item item) {
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getId() == item.getId()) {
                itens.set(i, item);
                return;
            }
        }
    }

    @Override
    public void deletar(long id) {
        Iterator<Item> it = itens.iterator();
        while (it.hasNext()) {
            Item item = it.next();
            if (item.getId() == id) {
                it.remove();
                return;
            }
        }
    }
}
