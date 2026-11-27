/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.presenter.state;

import br.ufes.cliente.ClienteDTO;
import br.ufes.presenter.ClientePresenter;
import br.ufes.view.ClienteView;
import javax.swing.JOptionPane;

/**
 *
 * @author Paulo Vitor
 */
public class ClienteEdicaoState extends ClientePresenterState{
    
    private final ClienteDTO cliente;

    public ClienteEdicaoState(ClientePresenter presenter, ClienteDTO cliente) {
        super(presenter);
        this.cliente = cliente;
        ClienteView view = presenter.getView();

        view.getLblModo().setText("Modo: Edição");
        presenter.habilitarCampos();

        view.getBtnNovo().setEnabled(false);
        view.getBtnSalvar().setEnabled(true);
        view.getBtnCancelar().setEnabled(true);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getTblClientes().setEnabled(false);
    }
    
    @Override
    public void salvar() {
        ClienteView view = presenter.getView();
        String nome = view.getTxtNome().getText();
        String endereco = view.getTxtEndereco().getText();

        presenter.getClienteService().atualizar(cliente.getId(), nome, endereco);
        presenter.atualizarTabela();
        JOptionPane.showMessageDialog(view, "Cliente atualizado com sucesso!");
        presenter.setEstado(new ClienteVisualizacaoState(presenter));
    }

    @Override
    public void cancelar() {
        presenter.setEstado(new ClienteVisualizacaoState(presenter));
    }
    
}
