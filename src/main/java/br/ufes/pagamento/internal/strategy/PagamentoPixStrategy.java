package br.ufes.pagamento.internal.strategy;

import java.util.Random;

public class PagamentoPixStrategy implements IEstrategiaPagamento {
    private Random random;

    public PagamentoPixStrategy(){
        this.random = new Random();
    }
    @Override
    public boolean processarPagamento(double valorCobrado) {
        int numeroSorteado = random.nextInt(10) + 1;
        return numeroSorteado <= 9;
    }
}
