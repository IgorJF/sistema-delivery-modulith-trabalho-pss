package br.ufes.presenter.state;

import br.ufes.presenter.ItemPresenter;

public abstract class ItemPresenterState {
    protected ItemPresenter presenter;

    public ItemPresenterState(ItemPresenter presenter) {
        this.presenter = presenter;
    }

    public void novo() {
        throw new RuntimeException("Novo nao permitida nesse estado");
    }

    public void salvar() {
        throw new RuntimeException("Salvar nao permitida nesse estado");
    }

    public void editar() {
        throw new RuntimeException("Editar nao permitida nesse estado");
    }

    public void excluir() {
        throw new RuntimeException("Excluir nao permitida nesse estado");
    }
    
    public void cancelar(){
        throw new RuntimeException("Cancelar nao permitida nesse estado");
    }
}



