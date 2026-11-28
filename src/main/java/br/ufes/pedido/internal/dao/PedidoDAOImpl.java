package br.ufes.pedido.internal.dao;

import br.ufes.pedido.internal.model.Pedido;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class PedidoDAOImpl implements IPedidoDAO {

    private List<Pedido> pedidos;
    private long proximoId;

    public PedidoDAOImpl(){

        this.pedidos = new ArrayList<>();
        this.proximoId = 1;

    }

    @Override
    public void salvar(Pedido pedido) {

        pedido.setId(proximoId++);
        pedidos.add(pedido);

    }

    @Override
    public void atualizar(Pedido pedido) {

        for (int i = 0; i < pedidos.size(); i++) {
            if (Objects.equals(pedidos.get(i).getId(), pedido.getId())) {
                pedidos.set(i, pedido);
                return;
            }
        }

    }

    @Override
    public Optional<Pedido> buscarPorId(long id) {

        for(Pedido pedido : pedidos){

            if(Objects.equals(pedido.getId(), id)){

                return Optional.of(pedido);

            }

        }

        return Optional.empty();
    }

    @Override
    public List<Pedido> listar() {

        return new ArrayList<>(pedidos);

    }
}
