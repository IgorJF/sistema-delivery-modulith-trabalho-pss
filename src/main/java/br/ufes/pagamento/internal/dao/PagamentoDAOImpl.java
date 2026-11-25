package br.ufes.pagamento.internal.dao;

import br.ufes.pagamento.internal.model.Pagamento;

import java.util.ArrayList;
import java.util.List;

public class PagamentoDAOImpl implements IPagamentoDAO {
    private List<Pagamento> pagamentos;
    private long proximoId;

    public PagamentoDAOImpl(){
        this.pagamentos = new ArrayList<>();
        this.proximoId = 1;
    }
    @Override
    public void inserirPagamento(Pagamento pagamento) {
        pagamento.setPagamentoId(proximoId);
        proximoId++;
        pagamentos.add(pagamento);
    }

    @Override
    public Pagamento buscarPorPedidoId(long pedidoId) {
        for(Pagamento pagamento : pagamentos){
            if(pagamento.getPedidoId() == pedidoId){
                return pagamento;
            }
        }
        return null;
    }

    @Override
    public List<Pagamento> listarPagamentos() {
        return new ArrayList<>(pagamentos);
    }
}
