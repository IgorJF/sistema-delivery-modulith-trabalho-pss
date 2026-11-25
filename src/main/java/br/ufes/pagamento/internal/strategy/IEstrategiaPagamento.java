package br.ufes.pagamento.internal.strategy;

public interface IEstrategiaPagamento {
    boolean processarPagamento(double valorCobrado);
}
