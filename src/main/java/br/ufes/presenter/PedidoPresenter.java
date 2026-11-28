/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.presenter;

import br.ufes.cliente.ClienteDTO;
import br.ufes.cliente.ClienteService;
import br.ufes.item.ItemDTO;
import br.ufes.item.ItemService;
import br.ufes.pedido.ItemPedidoDTO;
import br.ufes.pedido.PedidoDTO;
import br.ufes.pedido.PedidoService;
import br.ufes.pedido.StatusPedido;
import br.ufes.presenter.state.PedidoAguardandoPagamentoState;
import br.ufes.presenter.state.PedidoFinalizadoState;
import br.ufes.presenter.state.PedidoNovoState;
import br.ufes.presenter.state.PedidoPresenterState;
import br.ufes.view.PedidoView;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Paulo Vitor
 */




public class PedidoPresenter {

    private final PedidoView view;
    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final ItemService itemService;

    private PedidoPresenterState estado;
    private final List<ItemPedidoDTO> itensCarrinho;
    private PedidoDTO pedidoSelecionado;

    public PedidoPresenter(PedidoService pedidoService, ClienteService clienteService, ItemService itemService) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.itemService = itemService;
        this.itensCarrinho = new ArrayList<>();
        this.view = new PedidoView();
        configuraView();
    }

    private void configuraView() {
        view.setVisible(false);
        carregarCombos();
        atualizarTabelaPedidos();
        setEstado(new PedidoNovoState(this));

        view.getBtnAdicionar().addActionListener(e -> {
            try {
                estado.adicionarItem();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });

        view.getBtnRemover().addActionListener(e -> {
            try {
                estado.removerItem();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });

        view.getBtnCriar().addActionListener(e -> {
            try {
                estado.criarPedido();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });

        view.getBtnRealizarPagamento().addActionListener(e -> {
            try {
                estado.realizarPagamento();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });

        view.getBtnCancelar().addActionListener(e -> {
            try {
                estado.cancelarPedido();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });

        view.getBtnLimpar().addActionListener(e -> {
            try {
                estado.limpar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Falha: " + ex.getMessage());
            }
        });

        view.getTblPedidos().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = view.getTblPedidos().getSelectedRow();
                if (linha != -1) {
                    long pedidoId = (Long) view.getTblPedidos().getModel().getValueAt(linha, 0);
                    Optional<PedidoDTO> optPedido = pedidoService.buscarPorId(pedidoId);
                    if (optPedido.isPresent()) {
                        pedidoSelecionado = optPedido.get();
                        carregarDadosPedidoSelecionado(pedidoSelecionado);

                        if (pedidoSelecionado.getStatus() == StatusPedido.AGUARDANDO_PAGAMENTO) {
                            setEstado(new PedidoAguardandoPagamentoState(this));
                        } else {
                            setEstado(new PedidoFinalizadoState(this));
                        }
                    }
                }
            }
        });

        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    public void carregarCombos() {
        DefaultComboBoxModel<Object> clienteModel = new DefaultComboBoxModel<>();
        for (ClienteDTO cliente : clienteService.listar()) {
            clienteModel.addElement(cliente);
        }
        view.getCmbCliente().setModel((DefaultComboBoxModel) clienteModel);

        DefaultComboBoxModel<Object> itemModel = new DefaultComboBoxModel<>();
        for (ItemDTO item : itemService.listar()) {
            itemModel.addElement(item);
        }
        view.getCmbItem().setModel((DefaultComboBoxModel) itemModel);
    }

    public void atualizarTabelaCarrinho() {
        DefaultTableModel model = (DefaultTableModel) view.getTblItens1().getModel();
        model.setRowCount(0);
        double total = 0;

        for (ItemPedidoDTO item : itensCarrinho) {
            model.addRow(new Object[]{
                    item.getId(),
                    item.getDescricaoItem(),
                    item.getQuantidade(),
                    item.getPrecoUnitario(),
                    item.getSubTotal()
            });
            total += item.getSubTotal();
        }
        view.getTxtValorTotal().setText(String.format("%.2f", total));
    }

    public void atualizarTabelaPedidos() {
        DefaultTableModel model = (DefaultTableModel) view.getTblPedidos().getModel();
        model.setRowCount(0);

        for (PedidoDTO pedido : pedidoService.listar()) {
            model.addRow(new Object[]{
                    pedido.getId(),
                    pedido.getClienteId(),
                    String.format("R$ %.2f", pedido.getValorTotal()),
                    pedido.getTipoPagamento() != null ? pedido.getTipoPagamento() : "-",
                    pedido.getStatus()
            });
        }
    }

    public void carregarDadosPedidoSelecionado(PedidoDTO pedido) {
        itensCarrinho.clear();
        itensCarrinho.addAll(pedido.getItens());
        atualizarTabelaCarrinho();

        for (int i = 0; i < view.getCmbCliente().getItemCount(); i++) {
            Object obj = view.getCmbCliente().getItemAt(i);
            if (obj instanceof ClienteDTO cliente && cliente.getId().equals(pedido.getClienteId())) {
                view.getCmbCliente().setSelectedIndex(i);
                break;
            }
        }
    }

    public void limparTela() {
        itensCarrinho.clear();
        pedidoSelecionado = null;
        view.getTxtQuantidadeItem().setText("");
        view.getTxtValorTotal().setText("0,00");
        view.getTblPedidos().clearSelection();
        view.getTblItens1().clearSelection();
        atualizarTabelaCarrinho();
        carregarCombos();
    }

    public void setEstado(PedidoPresenterState novoEstado) {
        this.estado = novoEstado;
    }

    public PedidoView getView() {
        return view;
    }

    public PedidoService getPedidoService() {
        return pedidoService;
    }

    public ClienteService getClienteService() {
        return clienteService;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public List<ItemPedidoDTO> getItensCarrinho() {
        return itensCarrinho;
    }

    public PedidoDTO getPedidoSelecionado() {
        return pedidoSelecionado;
    }

    public void setPedidoSelecionado(PedidoDTO pedidoSelecionado) {
        this.pedidoSelecionado = pedidoSelecionado;
    }
}
