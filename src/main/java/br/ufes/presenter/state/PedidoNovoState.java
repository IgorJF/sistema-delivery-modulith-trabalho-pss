/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.ufes.presenter.state;

import br.ufes.cliente.ClienteDTO;
import br.ufes.item.ItemDTO;
import br.ufes.pedido.ItemPedidoDTO;
import br.ufes.pedido.PedidoDTO;
import br.ufes.presenter.PedidoPresenter;
import javax.swing.JOptionPane;


/**
 *
 * @author Paulo Vitor
 */

public class PedidoNovoState extends PedidoPresenterState {

    public PedidoNovoState(PedidoPresenter presenter) {
        super(presenter);
        configurarBotoesECampos();
    }

    private void configurarBotoesECampos() {
        presenter.getView().getCmbCliente().setEnabled(true);
        presenter.getView().getCmbItem().setEnabled(true);
        presenter.getView().getTxtQuantidadeItem().setEnabled(true);
        presenter.getView().getBtnAdicionar().setEnabled(true);
        presenter.getView().getBtnRemover().setEnabled(true);
        presenter.getView().getBtnCriar().setEnabled(true);
        presenter.getView().getBtnLimpar().setEnabled(true);

        presenter.getView().getCmbFormaPagamento().setEnabled(false);
        presenter.getView().getBtnRealizarPagamento().setEnabled(false);
        presenter.getView().getBtnCancelar().setEnabled(false);
    }

    @Override
    public void adicionarItem() {
        Object itemObj = presenter.getView().getCmbItem().getSelectedItem();
        if (!(itemObj instanceof ItemDTO itemSelecionado)) {
            throw new RuntimeException("Selecione um item válido.");
        }

        String qtdTexto = presenter.getView().getTxtQuantidadeItem().getText().trim();
        if (qtdTexto.isEmpty()) {
            throw new RuntimeException("Informe a quantidade do item.");
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(qtdTexto);
        } catch (NumberFormatException e) {
            throw new RuntimeException("A quantidade deve ser um número inteiro.");
        }

        if (quantidade <= 0) {
            throw new RuntimeException("A quantidade deve ser maior que zero.");
        }

        // Verifica se o item já está no carrinho para somar a quantidade
        int quantidadeJaNoCarrinho = 0;
        ItemPedidoDTO itemExistente = null;
        for (ItemPedidoDTO itemCarrinho : presenter.getItensCarrinho()) {
            if (itemCarrinho.getId() == itemSelecionado.getId()) {
                quantidadeJaNoCarrinho = itemCarrinho.getQuantidade();
                itemExistente = itemCarrinho;
                break;
            }
        }

        int quantidadeTotal = quantidadeJaNoCarrinho + quantidade;
        if (itemSelecionado.getQuantidade() < quantidadeTotal) {
            throw new RuntimeException("Estoque insuficiente. Disponível: " + itemSelecionado.getQuantidade());
        }

        if (itemExistente != null) {
            presenter.getItensCarrinho().remove(itemExistente);
        }

        double subTotal = itemSelecionado.getPreco() * quantidadeTotal;
        ItemPedidoDTO novoItemCarrinho = new ItemPedidoDTO(
                itemSelecionado.getId(),
                itemSelecionado.getDescricao(),
                quantidadeTotal,
                itemSelecionado.getPreco(),
                subTotal
        );

        presenter.getItensCarrinho().add(novoItemCarrinho);
        presenter.getView().getTxtQuantidadeItem().setText("");
        presenter.atualizarTabelaCarrinho();
    }

    @Override
    public void removerItem() {
        int linhaSelecionada = presenter.getView().getTblItens1().getSelectedRow();
        if (linhaSelecionada == -1) {
            throw new RuntimeException("Selecione um item na tabela para remover.");
        }

        presenter.getItensCarrinho().remove(linhaSelecionada);
        presenter.atualizarTabelaCarrinho();
    }

    @Override
    public void criarPedido() {
        Object clienteObj = presenter.getView().getCmbCliente().getSelectedItem();
        if (!(clienteObj instanceof ClienteDTO clienteSelecionado)) {
            throw new RuntimeException("Selecione um cliente válido.");
        }

        if (presenter.getItensCarrinho().isEmpty()) {
            throw new RuntimeException("Adicione pelo menos um item ao pedido.");
        }

        PedidoDTO novoPedido = presenter.getPedidoService().criarPedido(
                clienteSelecionado.getId(),
                presenter.getItensCarrinho()
        );

        JOptionPane.showMessageDialog(
                presenter.getView(),
                "Pedido #" + novoPedido.getId() + " criado com sucesso! Aguardando pagamento."
        );

        presenter.atualizarTabelaPedidos();
        presenter.setPedidoSelecionado(novoPedido);
        presenter.setEstado(new PedidoAguardandoPagamentoState(presenter));
    }

    @Override
    public void limpar() {
        presenter.limparTela();
    }
}
