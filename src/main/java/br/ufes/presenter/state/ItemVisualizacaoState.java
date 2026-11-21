package br.ufes.presenter.state;

import br.ufes.presenter.ItemPresenter;
import br.ufes.view.ItemView;

public class ItemVisualizacaoState extends ItemPresenterState{
    public ItemVisualizacaoState(ItemPresenter presenter) {
        super(presenter);
        ItemView view = presenter.getView();

        view.getLblModo().setText("Modo: Visualização");
        presenter.limparCampos();
        presenter.habilitarCampos();

        view.getBtnNovo().setEnabled(true);
        view.getBtnSalvar().setEnabled(false);
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getTblItens().setEnabled(true);
    }
    
    

}
