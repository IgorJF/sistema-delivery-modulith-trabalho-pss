package br.ufes.pedido.internal.service;

import br.ufes.cliente.ClienteDTO;
import br.ufes.cliente.ClienteService;
import br.ufes.item.ItemDTO;
import br.ufes.item.ItemService;
import br.ufes.pagamento.IPagamentoObserver;
import br.ufes.pagamento.IPagamentoService;
import br.ufes.pagamento.PagamentoAprovadoEvento;
import br.ufes.pagamento.PagamentoRecusadoEvento;
import br.ufes.pedido.ItemPedidoDTO;
import br.ufes.pedido.PedidoDTO;
import br.ufes.pedido.PedidoService;
import br.ufes.pedido.StatusPedido;
import br.ufes.pedido.internal.dao.IPedidoDAO;
import br.ufes.pedido.internal.model.ItemPedido;
import br.ufes.pedido.internal.model.Pedido;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoServiceImpl implements PedidoService, IPagamentoObserver {

    private final IPedidoDAO pedidoDAO;
    private final ClienteService clienteService;
    private final ItemService itemService;
    private final IPagamentoService pagamentoService;

    public PedidoServiceImpl(IPedidoDAO pedidoDAO, ItemService itemService, IPagamentoService pagamentoService, ClienteService clienteService){

        this.pedidoDAO = pedidoDAO;
        this.itemService = itemService;
        this.pagamentoService = pagamentoService;
        this.clienteService = clienteService;
        pagamentoService.adicionarObservador(this);


    }

    @Override
    public PedidoDTO criarPedido(long clienteId, List<ItemPedidoDTO> itens) {

        ClienteDTO cliente = clienteService.buscarPorId(clienteId);
        if(cliente == null) throw new RuntimeException("Cliente não encontrado com o ID: "+clienteId);

        List<ItemPedido> itensPedido = validarEConverterItens(itens);


        Pedido pedido = new Pedido(0, cliente.getId(), cliente.getNome(), cliente.getEndereco(), itensPedido, 0,
                StatusPedido.AGUARDANDO_PAGAMENTO, null );
        pedido.setValorTotal(pedido.calcularValorTotal());

        pedidoDAO.salvar(pedido);

        return converterParaDTO(pedido);

    }

    @Override
    public PedidoDTO realizarPagamento(long pedidoId, String tipoPagamento) {

        Pedido pedido = buscarEntidadePorId(pedidoId);

        if(pedido.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) throw new RuntimeException("Este pedido não está aguardando pagamento.");
        if(tipoPagamento == null || tipoPagamento.isBlank()) throw new RuntimeException("Informe o tipo de pagamento");
        pedido.setTipoPagamento(tipoPagamento);
        pagamentoService.processarPagamento(pedido.getId(), pedido.getValorTotal(), tipoPagamento);

        return converterParaDTO(buscarEntidadePorId(pedidoId));
    }

    @Override
    public void cancelar(long id) {

        Pedido pedido = buscarEntidadePorId(id);
        if(pedido.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) throw new RuntimeException("Não é possível cancelar um pedido já processado.");
        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoDAO.atualizar(pedido);

    }

    @Override
    public void notificarAprovacao(PagamentoAprovadoEvento eventoAprovado) {

        Pedido pedido = buscarEntidadePorId(eventoAprovado.getPedidoId());

        for (ItemPedido itemPedido : pedido.getItens()){

            ItemDTO itemAtual = itemService.buscarPorId(itemPedido.getId());
            int novaQuantidade = itemAtual.getQuantidade() - itemPedido.getQuantidade();
            itemService.atualizar(itemAtual.getId(), itemAtual.getDescricao(), itemAtual.getPreco(), novaQuantidade);

        }

        pedido.setStatus(StatusPedido.PAGO);
        pedidoDAO.atualizar(pedido);

    }

    @Override
    public void notificarRecusa(PagamentoRecusadoEvento eventoRecusado) {

        Pedido pedido = buscarEntidadePorId(eventoRecusado.getPedidoId());
        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoDAO.atualizar(pedido);

    }

    @Override
    public Optional<PedidoDTO> buscarPorId(long id) {

        Optional<Pedido> pedido = pedidoDAO.buscarPorId(id);
        if(pedido.isEmpty()) {

            return Optional.empty();

        }

        return Optional.of(converterParaDTO(pedido.get()));

    }

    @Override
    public List<PedidoDTO> listar() {

        List<PedidoDTO> dtos = new ArrayList<>();

        for (Pedido pedido : pedidoDAO.listar()){

            dtos.add(converterParaDTO(pedido));

        }

        return dtos;
    }

    private Pedido buscarEntidadePorId(long id){

        Optional<Pedido> pedido = pedidoDAO.buscarPorId(id);

        if(pedido.isEmpty()) throw new RuntimeException("Pedido nao encontrado com ID: " + id);

        return pedido.get();

    }

    private List<ItemPedido> validarEConverterItens(List<ItemPedidoDTO> itensDTO){

        List<ItemPedido> itensConvertidos = new ArrayList<>();

        if(itensDTO == null || itensDTO.isEmpty()) throw new RuntimeException("A lista de itens não pode ser nula.");

        for (ItemPedidoDTO dto : itensDTO){

            if(dto.getQuantidade() <= 0) throw new RuntimeException("A quantidade dos itens deve ser maior que zero.");
            ItemDTO itemEstoque = itemService.buscarPorId(dto.getId());
            if(itemEstoque == null) throw new RuntimeException("Item não encontrado.");
            if(itemEstoque.getQuantidade() < dto.getQuantidade()) throw new RuntimeException("Estoque insuficiente.");
            itensConvertidos.add(new ItemPedido(itemEstoque.getId(), itemEstoque.getDescricao(), dto.getQuantidade(), itemEstoque.getPreco()));
        }

        return itensConvertidos;

    }

    private PedidoDTO converterParaDTO(Pedido pedido){

        List<ItemPedidoDTO> itemPedidoDTOS = new ArrayList<>();

        for(ItemPedido item : pedido.getItens()){

            itemPedidoDTOS.add(new ItemPedidoDTO(item.getId(), item.getDescricaoItem(), item.getQuantidade(), item.getPrecoUnitario(), item.getSubTotal()));


        }
        return new PedidoDTO(pedido.getId(), pedido.getClienteId(), pedido.getNomeCliente(), pedido.getEnderecoCliente(), itemPedidoDTOS,
                pedido.calcularValorTotal(), pedido.getStatus(), pedido.getTipoPagamento());

    }

}
