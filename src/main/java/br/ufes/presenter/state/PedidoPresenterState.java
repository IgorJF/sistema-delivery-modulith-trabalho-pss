/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.presenter.state;

import br.ufes.presenter.PedidoPresenter;

/**
 *
 * @author Paulo Vitor
 */

public class PedidoPresenterState {

    protected PedidoPresenter presenter;

    public PedidoPresenterState(PedidoPresenter presenter) {
        this.presenter = presenter;
    }

    public void adicionarItem() {
        throw new RuntimeException("Adicionar item não é permitido neste estado.");
    }

    public void removerItem() {
        throw new RuntimeException("Remover item não é permitido neste estado.");
    }

    public void criarPedido() {
        throw new RuntimeException("Criar pedido não é permitido neste estado.");
    }

    public void realizarPagamento() {
        throw new RuntimeException("Realizar pagamento não é permitido neste estado.");
    }

    public void cancelarPedido() {
        throw new RuntimeException("Cancelar pedido não é permitido neste estado.");
    }

    public void limpar() {
        throw new RuntimeException("Limpar não é permitido neste estado.");
    }
}
