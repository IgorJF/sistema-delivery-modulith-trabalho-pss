package br.ufes.presenter;

import br.ufes.item.ItemDTO;
import br.ufes.item.ItemService;
import br.ufes.presenter.state.ItemPresenterState;
import br.ufes.presenter.state.ItemVisualizacaoState;
import br.ufes.view.ItemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class ItemPresenter {
    private final ItemView view;
    private final ItemService itemService;
    private ItemPresenterState estado;
    private ItemDTO itemSelecionado;

    public ItemPresenter(ItemService itemService) {
        this.itemService = itemService;
        this.view = new ItemView();
        configuraView();
    }

    private void configuraView() {
        view.setVisible(false);
        atualizarTabela();
        setEstado(new ItemVisualizacaoState(this));

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
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Falha: Preço ou quantidade inválidos.");
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

        view.getTblItens().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int linha = view.getTblItens().getSelectedRow();
                    boolean selecionado = linha != -1;

                    view.getBtnEditar().setEnabled(selecionado);
                    view.getBtnExcluir().setEnabled(selecionado);

                    if (selecionado) {
                        long id = (Long) view.getTblItens().getModel().getValueAt(linha, 0);
                        itemSelecionado = itemService.buscarPorId(id);
                        carregarDadosItem();
                    } else {
                        itemSelecionado = null;
                    }
                }
            }
        });
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

    private void carregarDadosItem() {
        view.getTxtDescricao().setText(itemSelecionado.getDescricao());
        view.getTxtPreco().setText(String.valueOf(itemSelecionado.getPreco()));
        view.getTxtQuantidade().setText(String.valueOf(itemSelecionado.getQuantidade()));
    }

    public void atualizarTabela() {
        DefaultTableModel model = (DefaultTableModel) view.getTblItens().getModel();
        model.setRowCount(0);
        for (ItemDTO item : itemService.listar()) {
            model.addRow(new Object[]{
                item.getId(),
                item.getDescricao(),
                item.getPreco(),
                item.getQuantidade()
            });
        }
    }

    public void limparCampos() {
        view.getTxtDescricao().setText("");
        view.getTxtPreco().setText("");
        view.getTxtQuantidade().setText("");
    }

    public void habilitarCampos() {
        view.getTxtDescricao().setEditable(true);
        view.getTxtPreco().setEditable(true);
        view.getTxtQuantidade().setEditable(true);
    }

    public void desabilitarCampos() {
        view.getTxtDescricao().setEditable(false);
        view.getTxtPreco().setEditable(false);
        view.getTxtQuantidade().setEditable(false);
    }

    public void setEstado(ItemPresenterState novoEstado) {
        this.estado = novoEstado;
    }

    public ItemView getView() {
        return view;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public ItemDTO getItemSelecionado() {
        return itemSelecionado;
    }
}