package br.ufes.pagamento.internal.strategy;

public class PagamentoDinheiroStrategy implements IEstrategiaPagamento {
    @Override
    public boolean processarPagamento(double valorCobrado) {
        return valorCobrado > 0;
    }
}
