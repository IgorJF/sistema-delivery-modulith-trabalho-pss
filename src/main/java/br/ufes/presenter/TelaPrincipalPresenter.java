package br.ufes.presenter;

import br.ufes.cliente.ClienteService;
import br.ufes.item.ItemService;
import br.ufes.pedido.PedidoService;
import br.ufes.view.TelaPrincipalView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.springframework.stereotype.Component;

@Component
public class TelaPrincipalPresenter {
    private final TelaPrincipalView view;
    private final ItemService itemService;
    private final ClienteService clienteService;
    private final PedidoService pedidoService;
    private ItemPresenter itemPresenter;
    private ClientePresenter clientePresenter;
    private PedidoPresenter pedidoPresenter;
    

    public TelaPrincipalPresenter(ItemService itemService, ClienteService clienteService, PedidoService pedidoService) {
        this.view = new TelaPrincipalView();
        this.itemService = itemService;
        this.clienteService = clienteService;
        this.pedidoService = pedidoService;
        configuraView();
    }

    private void configuraView() {
        view.setVisible(false);

        view.getBtnItem().addActionListener(e -> {
            itemPresenter = new ItemPresenter(itemService);
            abrirTelaFilha(itemPresenter.getView());
        });

        view.getBtnClientes().addActionListener(e -> {
            clientePresenter = new ClientePresenter(clienteService);
            abrirTelaFilha(clientePresenter.getView());
        });

        view.getBtnPedidos().addActionListener(e -> {
            pedidoPresenter = new PedidoPresenter(pedidoService, clienteService, itemService);
            abrirTelaFilha(pedidoPresenter.getView());
        });

        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    private void abrirTelaFilha(javax.swing.JFrame telaFilha) {
        view.setVisible(false);
        telaFilha.setLocationRelativeTo(null);
        telaFilha.toFront();
        telaFilha.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                view.setVisible(true);
                view.toFront();
            }
        });
    }

}
