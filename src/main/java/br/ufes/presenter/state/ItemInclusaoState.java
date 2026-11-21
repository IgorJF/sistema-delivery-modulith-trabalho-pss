package br.ufes.presenter.state;

import br.ufes.presenter.ItemPresenter;
import br.ufes.view.ItemView;
import javax.swing.JOptionPane;

public class ItemInclusaoState extends ItemPresenterState {

    public ItemInclusaoState(ItemPresenter presenter) {
        super(presenter);
        ItemView view = presenter.getView();

        view.getLblModo().setText("Modo: Inclusão");
        presenter.limparCampos();
        presenter.habilitarCampos();

        view.getBtnNovo().setEnabled(false);
        view.getBtnSalvar().setEnabled(true);
        view.getBtnCancelar().setEnabled(true);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getTblItens().clearSelection();
        view.getTblItens().setEnabled(false);
    }

    @Override
    public void salvar() {
        ItemView view = presenter.getView();
        String descricao = view.getTxtDescricao().getText();
        double preco = Double.parseDouble(view.getTxtPreco().getText());
        int quantidade = Integer.parseInt(view.getTxtQuantidade().getText());

        presenter.getItemService().cadastrar(descricao, preco, quantidade);
        presenter.atualizarTabela();
        JOptionPane.showMessageDialog(view, "Item cadastrado com sucesso!");
        presenter.setEstado(new ItemVisualizacaoState(presenter));
    }

    @Override
    public void cancelar() {
        presenter.setEstado(new ItemVisualizacaoState(presenter));
    }
}