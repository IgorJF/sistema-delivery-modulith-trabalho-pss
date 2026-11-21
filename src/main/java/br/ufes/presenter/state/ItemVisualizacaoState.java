package br.ufes.presenter.state;

import br.ufes.presenter.ItemPresenter;
import br.ufes.view.ItemView;
import javax.swing.JOptionPane;

public class ItemVisualizacaoState extends ItemPresenterState {

    public ItemVisualizacaoState(ItemPresenter presenter) {
        super(presenter);
        ItemView view = presenter.getView();

        view.getLblModo().setText("Modo: Visualização");
        presenter.limparCampos();
        presenter.desabilitarCampos();

        view.getBtnNovo().setEnabled(true);
        view.getBtnSalvar().setEnabled(false);
        view.getBtnCancelar().setEnabled(false);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getTblItens().clearSelection();
        view.getTblItens().setEnabled(true);
    }

    @Override
    public void novo() {
        presenter.setEstado(new ItemInclusaoState(presenter));
    }

    @Override
    public void editar() {
        if (presenter.getItemSelecionado() == null) {
            throw new RuntimeException("Selecione um item na tabela.");
        }
        presenter.setEstado(new ItemEdicaoState(presenter, presenter.getItemSelecionado()));
    }

    @Override
    public void excluir() {
        if (presenter.getItemSelecionado() == null) {
            throw new RuntimeException("Selecione um item na tabela.");
        }
        long id = presenter.getItemSelecionado().getId();
        presenter.getItemService().excluir(id);
        presenter.atualizarTabela();
        JOptionPane.showMessageDialog(presenter.getView(), "Item excluido com sucesso");
        presenter.setEstado(new ItemVisualizacaoState(presenter));
    }
}
