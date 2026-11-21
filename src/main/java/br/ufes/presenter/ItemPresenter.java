package br.ufes.presenter;

import br.ufes.presenter.state.ItemPresenterState;
import br.ufes.presenter.state.ItemVisualizacaoState;
import br.ufes.view.ItemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


public class ItemPresenter {
    private final ItemView view;
    private ItemPresenterState estado;

    public ItemPresenter() {
        this.view = new ItemView();
        configuraView();
    }

    private void configuraView() {
        view.setVisible(false);
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
        view.setDefaultCloseOperation(ItemView.DISPOSE_ON_CLOSE);
    }

    private void novo() {
        estado.novo();
    }

    private void salvar() {
        estado.salvar();
    }

    private void editar() {
        estado.editar();
    }

    private void excluir() {
        estado.excluir();
    }

    //Metodos para auxiliar o State
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
}