package br.ufes.presenter;

import br.ufes.cliente.ClienteService;
import br.ufes.item.ItemService;
import br.ufes.view.TelaPrincipalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.stereotype.Component;

@Component
public class TelaPrincipalPresenter {
    private final TelaPrincipalView view;
    private final ItemService itemService;
    private final ClienteService clienteService;
    private ItemPresenter itemPresenter;
    private ClientePresenter clientePresenter;

    public TelaPrincipalPresenter(ItemService itemService, ClienteService clienteService) {
        this.view = new TelaPrincipalView();
        this.itemService = itemService;
        this.clienteService = clienteService;
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
        view.getBtnClientes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientePresenter = new ClientePresenter(clienteService);
            }
        });
        view.setVisible(true);
    }

}
