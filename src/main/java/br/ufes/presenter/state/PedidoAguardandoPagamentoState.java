/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.presenter.state;

import br.ufes.pedido.PedidoDTO;
import br.ufes.pedido.StatusPedido;
import br.ufes.presenter.PedidoPresenter;
import javax.swing.JOptionPane;

/**
 *
 * @author Paulo Vitor
 */

public class PedidoAguardandoPagamentoState extends PedidoPresenterState {

    public PedidoAguardandoPagamentoState(PedidoPresenter presenter) {
        super(presenter);
        configurarBotoesECampos();
    }

    private void configurarBotoesECampos() {
        presenter.getView().getCmbCliente().setEnabled(false);
        presenter.getView().getCmbItem().setEnabled(false);
        presenter.getView().getTxtQuantidadeItem().setEnabled(false);
        presenter.getView().getBtnAdicionar().setEnabled(false);
        presenter.getView().getBtnRemover().setEnabled(false);
        presenter.getView().getBtnCriar().setEnabled(false);

        presenter.getView().getCmbFormaPagamento().setEnabled(true);
        presenter.getView().getBtnRealizarPagamento().setEnabled(true);
        presenter.getView().getBtnCancelar().setEnabled(true);
        presenter.getView().getBtnLimpar().setEnabled(true);
    }

    @Override
    public void realizarPagamento() {
        PedidoDTO pedido = presenter.getPedidoSelecionado();
        if (pedido == null) {
            throw new RuntimeException("Nenhum pedido selecionado.");
        }

        Object formaObj = presenter.getView().getCmbFormaPagamento().getSelectedItem();
        if (formaObj == null) {
            throw new RuntimeException("Selecione uma forma de pagamento.");
        }

        String formaPagamento = formaObj.toString();
        PedidoDTO pedidoAtualizado = presenter.getPedidoService().realizarPagamento(
                pedido.getId(),
                formaPagamento
        );

        if (pedidoAtualizado.getStatus() == StatusPedido.PAGO) {
            JOptionPane.showMessageDialog(
                    presenter.getView(),
                    "Pagamento APROVADO via " + formaPagamento + "! Estoque atualizado."
            );
        } else {
            JOptionPane.showMessageDialog(
                    presenter.getView(),
                    "Pagamento RECUSADO via " + formaPagamento + ". O pedido foi cancelado.",
                    "Pagamento Recusado",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        presenter.setPedidoSelecionado(pedidoAtualizado);
        presenter.atualizarTabelaPedidos();
        presenter.carregarCombos();
        presenter.setEstado(new PedidoFinalizadoState(presenter));
    }

    @Override
    public void cancelarPedido() {
        PedidoDTO pedido = presenter.getPedidoSelecionado();
        if (pedido == null) {
            throw new RuntimeException("Nenhum pedido selecionado.");
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                presenter.getView(),
                "Deseja realmente cancelar o Pedido #" + pedido.getId() + "?",
                "Confirmar Cancelamento",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            presenter.getPedidoService().cancelar(pedido.getId());
            JOptionPane.showMessageDialog(presenter.getView(), "Pedido #" + pedido.getId() + " cancelado.");
            presenter.atualizarTabelaPedidos();
            presenter.setEstado(new PedidoFinalizadoState(presenter));
        }
    }

    @Override
    public void limpar() {
        presenter.limparTela();
        presenter.setEstado(new PedidoNovoState(presenter));
    }
}
