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

public class PedidoFinalizadoState extends PedidoPresenterState {

    public PedidoFinalizadoState(PedidoPresenter presenter) {
        super(presenter);
        configurarBotoesECampos();
    }

    private void configurarBotoesECampos() {
        presenter.getView().getCmbCliente().setEnabled(false);
        presenter.getView().getCmbItem().setEnabled(false);
        presenter.getView().getTxtQuantidadeItem().setEnabled(false);
        presenter.getView().getBtnAdicionar().setEnabled(false);
        presenter.getView().getBtnRemover().setEnabled(false);
        presenter.getView().getBtnCriar().setEnabled(false);
        presenter.getView().getCmbFormaPagamento().setEnabled(false);
        presenter.getView().getBtnRealizarPagamento().setEnabled(false);
        presenter.getView().getBtnCancelar().setEnabled(false);

        presenter.getView().getBtnLimpar().setEnabled(true);
    }

    @Override
    public void limpar() {
        presenter.limparTela();
        presenter.setEstado(new PedidoNovoState(presenter));
    }
}
