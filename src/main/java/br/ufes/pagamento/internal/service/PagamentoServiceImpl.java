package br.ufes.pagamento.internal.service;

import br.ufes.pagamento.IPagamentoObserver;
import br.ufes.pagamento.IPagamentoService;
import br.ufes.pagamento.PagamentoAprovadoEvento;
import br.ufes.pagamento.PagamentoDTO;
import br.ufes.pagamento.PagamentoRecusadoEvento;
import br.ufes.pagamento.internal.dao.IPagamentoDAO;
import br.ufes.pagamento.internal.dao.PagamentoDAOImpl;
import br.ufes.pagamento.internal.model.Pagamento;
import br.ufes.pagamento.internal.strategy.IEstrategiaPagamento;
import br.ufes.pagamento.internal.strategy.PagamentoCartaoStrategy;
import br.ufes.pagamento.internal.strategy.PagamentoDinheiroStrategy;
import br.ufes.pagamento.internal.strategy.PagamentoPixStrategy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PagamentoServiceImpl implements IPagamentoService {
    private IPagamentoDAO pagamentoDAO;
    private Map<String, IEstrategiaPagamento> estrategiasPagamentos;
    private List<IPagamentoObserver> observadores;

    public PagamentoServiceImpl() {
        this.pagamentoDAO = new PagamentoDAOImpl();
        this.observadores = new ArrayList<>();
        this.estrategiasPagamentos = new HashMap<>(Map.of(
                "PIX", new PagamentoPixStrategy(),
                "CARTAO", new PagamentoCartaoStrategy(),
                "DINHEIRO", new PagamentoDinheiroStrategy()
        ));
    }

    @Override
    public PagamentoDTO processarPagamento(long pedidoId, double valorCobrado, String tipoPagamento) {
        if (pedidoId <= 0) {
            throw new IllegalArgumentException("O ID do pedido deve ser maior que zero.");
        }
        if (valorCobrado <= 0) {
            throw new IllegalArgumentException("O valor cobrado deve ser maior que zero.");
        }
        if (tipoPagamento == null || tipoPagamento.isBlank()) {
            throw new IllegalArgumentException("O tipo de pagamento é obrigatório.");
        }

        String tipoNormalizado = tipoPagamento.trim().toUpperCase();
        IEstrategiaPagamento estrategia = estrategiasPagamentos.get(tipoNormalizado);

        if (estrategia == null) {
            throw new IllegalArgumentException("Tipo de pagamento não suportado: " + tipoPagamento);
        }

        boolean aprovado = estrategia.processarPagamento(valorCobrado);
        String status;

        if(aprovado){
            status = "APROVADO";
        } else{
            status = "RECUSADO";
        }

        Pagamento pagamento = new Pagamento(pedidoId, valorCobrado, tipoNormalizado, status);
        pagamentoDAO.inserirPagamento(pagamento);

        if (aprovado) {
            notificarAprovacao(new PagamentoAprovadoEvento(pedidoId));
        } else {
            notificarRecusa(new PagamentoRecusadoEvento(pedidoId, "Pagamento recusado via " + tipoNormalizado));
        }

        return converterParaDTO(pagamento);
    }

    @Override
    public PagamentoDTO buscarPorPedidoId(long pedidoId) {
        Pagamento pagamento = pagamentoDAO.buscarPorPedidoId(pedidoId);
        if (pagamento == null) {
            return null;
        }
        return converterParaDTO(pagamento);
    }

    @Override
    public List<PagamentoDTO> listarPagamentos() {
        List<PagamentoDTO> dtos = new ArrayList<>();
        for (Pagamento pagamento : pagamentoDAO.listarPagamentos()) {
            dtos.add(converterParaDTO(pagamento));
        }
        return dtos;
    }

    @Override
    public void adicionarObservador(IPagamentoObserver observador) {
        if (observador != null && !observadores.contains(observador)) {
            observadores.add(observador);
        }
    }

    @Override
    public void removerObservador(IPagamentoObserver observador) {
        observadores.remove(observador);
    }

    private void notificarAprovacao(PagamentoAprovadoEvento evento) {
        for (IPagamentoObserver observador : observadores) {
            observador.notificarAprovacao(evento);
        }
    }

    private void notificarRecusa(PagamentoRecusadoEvento evento) {
        for (IPagamentoObserver observador : observadores) {
            observador.notificarRecusa(evento);
        }
    }

    private PagamentoDTO converterParaDTO(Pagamento pagamento) {
        return new PagamentoDTO(
                pagamento.getPagamentoId(),
                pagamento.getPedidoId(),
                pagamento.getValorCobrado(),
                pagamento.getTipoPagamento(),
                pagamento.getStatus()
        );
    }
}
