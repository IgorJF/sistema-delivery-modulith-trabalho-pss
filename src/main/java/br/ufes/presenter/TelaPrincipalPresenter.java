package br.ufes.presenter;

import br.ufes.item.ItemService;
import br.ufes.view.TelaPrincipalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.stereotype.Component;

@Component
public class TelaPrincipalPresenter {
    private final TelaPrincipalView view;
    private final ItemService itemService;
    private ItemPresenter itemPresenter;

    public TelaPrincipalPresenter(ItemService itemService) {
        this.view = new TelaPrincipalView();
        this.itemService = itemService;
        configuraView();
    }

    private void configuraView() {
        view.setVisible(false);
        view.getBtnItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemPresenter = new ItemPresenter(itemService);
            }
        });
        view.setVisible(true);
    }

}