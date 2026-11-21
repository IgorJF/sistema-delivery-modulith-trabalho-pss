package br.ufes.presenter.state;

import br.ufes.item.ItemDTO;
import br.ufes.presenter.ItemPresenter;
import br.ufes.view.ItemView;
import javax.swing.JOptionPane;

public class ItemEdicaoState extends ItemPresenterState {
    private final ItemDTO item;

    public ItemEdicaoState(ItemPresenter presenter, ItemDTO item) {
        super(presenter);
        this.item = item;
        ItemView view = presenter.getView();

        view.getLblModo().setText("Modo: Edição");
        presenter.habilitarCampos();

        view.getBtnNovo().setEnabled(false);
        view.getBtnSalvar().setEnabled(true);
        view.getBtnCancelar().setEnabled(true);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getTblItens().setEnabled(false);
    }

    @Override
    public void salvar() {
        ItemView view = presenter.getView();
        String descricao = view.getTxtDescricao().getText();
        double preco = Double.parseDouble(view.getTxtPreco().getText());
        int quantidade = Integer.parseInt(view.getTxtQuantidade().getText());

        presenter.getItemService().atualizar(item.getId(), descricao, preco, quantidade);
        presenter.atualizarTabela();
        JOptionPane.showMessageDialog(view, "Item atualizado com sucesso!");
        presenter.setEstado(new ItemVisualizacaoState(presenter));
    }

    @Override
    public void cancelar() {
        presenter.setEstado(new ItemVisualizacaoState(presenter));
    }
}