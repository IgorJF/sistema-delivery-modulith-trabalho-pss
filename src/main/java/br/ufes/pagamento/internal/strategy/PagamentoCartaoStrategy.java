package br.ufes.pagamento.internal.strategy;

import java.util.Random;

public class PagamentoCartaoStrategy implements IEstrategiaPagamento {
    private Random random;

    public PagamentoCartaoStrategy() {
        this.random = new Random();
    }

    @Override
    public boolean processarPagamento(double valorCobrado) {
        if(valorCobrado > 500){ return false; }
        int numeroSorteado = random.nextInt(10) + 1;
        return numeroSorteado <= 7;
    }
}
