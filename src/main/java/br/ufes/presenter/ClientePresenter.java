/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.presenter;

import br.ufes.cliente.ClienteDTO;
import br.ufes.cliente.ClienteService;
import br.ufes.presenter.state.ClientePresenterState;
import br.ufes.presenter.state.ClienteVisualizacaoState;
import br.ufes.view.ClienteView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Paulo Vitor
 */

public class ClientePresenter {
    private final ClienteView view;
    private final ClienteService clienteService;
    private ClientePresenterState estado;
    private ClienteDTO clienteSelecionado;

    public ClientePresenter(ClienteService clienteService) {
        this.clienteService = clienteService;
        this.view = new ClienteView();
        configuraView();
    }

    private void configuraView() {
        view.setVisible(false);
        atualizarTabela();
        setEstado(new ClienteVisualizacaoState(this));

        view.getBtnNovo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    novo();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    salvar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cancelar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getBtnExcluir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    excluir();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
                }
            }
        });

        view.getTblClientes().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int linha = view.getTblClientes().getSelectedRow();
                    boolean selecionado = linha != -1;

                    view.getBtnEditar().setEnabled(selecionado);
                    view.getBtnExcluir().setEnabled(selecionado);

                    if (selecionado) {
                        long id = (Long) view.getTblClientes().getModel().getValueAt(linha, 0);
                        clienteSelecionado = clienteService.buscarPorId(id);
                        carregarDadosCliente();
                    } else {
                        clienteSelecionado = null;
                    }
                }
            }
        });

        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    private void novo() {
        estado.novo();
    }

    private void salvar() {
        estado.salvar();
    }

    private void cancelar() {
        estado.cancelar();
    }

    private void editar() {
        estado.editar();
    }

    private void excluir() {
        estado.excluir();
    }

    private void carregarDadosCliente() {
        view.getTxtNome().setText(clienteSelecionado.getNome());
        view.getTxtEndereco().setText(clienteSelecionado.getEndereco());
    }

    public void atualizarTabela() {
        DefaultTableModel model = (DefaultTableModel) view.getTblClientes().getModel();
        model.setRowCount(0);
        for (ClienteDTO cliente : clienteService.listar()) {
            model.addRow(new Object[]{
                cliente.getId(),
                cliente.getNome(),
                cliente.getEndereco()
            });
        }
    }

    public void limparCampos() {
        view.getTxtNome().setText("");
        view.getTxtEndereco().setText("");
    }

    public void habilitarCampos() {
        view.getTxtNome().setEditable(true);
        view.getTxtEndereco().setEditable(true);
    }

    public void desabilitarCampos() {
        view.getTxtNome().setEditable(false);
        view.getTxtEndereco().setEditable(false);
    }

    public void setEstado(ClientePresenterState novoEstado) {
        this.estado = novoEstado;
    }

    public ClienteView getView() {
        return view;
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public ClienteDTO getClienteSelecionado() {
        return clienteSelecionado;
    }
}