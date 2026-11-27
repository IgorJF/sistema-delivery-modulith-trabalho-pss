/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.presenter.state;

import br.ufes.presenter.ClientePresenter;
import br.ufes.view.ClienteView;
import javax.swing.JOptionPane;

/**
 *
 * @author Paulo Vitor
 */
public class ClienteVisualizacaoState extends ClientePresenterState {

    public ClienteVisualizacaoState(ClientePresenter presenter) {
        super(presenter);
        ClienteView view = presenter.getView();

        view.getLblModo().setText("Modo: Visualização");
        presenter.limparCampos();
        presenter.desabilitarCampos();

        view.getBtnNovo().setEnabled(true);
        view.getBtnSalvar().setEnabled(false);
        view.getBtnCancelar().setEnabled(false);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getTblClientes().clearSelection();
        view.getTblClientes().setEnabled(true);
    }

    @Override
    public void novo() {
        presenter.setEstado(new ClienteInclusaoState(presenter));
    }

    @Override
    public void editar() {
        if (presenter.getClienteSelecionado() == null) {
            throw new RuntimeException("Selecione um cliente na tabela.");
        }
        presenter.setEstado(new ClienteEdicaoState(presenter, presenter.getClienteSelecionado()));
    }

    @Override
    public void excluir() {
        if (presenter.getClienteSelecionado() == null) {
            throw new RuntimeException("Selecione um cliente na tabela.");
        }
        long id = presenter.getClienteSelecionado().getId();
        presenter.getClienteService().excluir(id);
        presenter.atualizarTabela();
        JOptionPane.showMessageDialog(presenter.getView(), "Cliente excluído com sucesso!");
        presenter.setEstado(new ClienteVisualizacaoState(presenter));
    }

}
