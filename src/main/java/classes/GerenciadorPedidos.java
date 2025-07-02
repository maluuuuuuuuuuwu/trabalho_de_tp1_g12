package classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por gerenciar os pedidos realizados,
 * incluindo cadastro, cálculo de totais e filtragem por cliente.
 */
/**
 *
 * @author malu
 */
public class GerenciadorPedidos {
    /** Lista de todos os pedidos cadastrados no sistema. */
    private List<Pedido> pedidos = new ArrayList<>();

    /**
     * Adiciona um novo pedido ao sistema.
     * Também atualiza o número de pizzas compradas pelo cliente.
     *
     * @param pedido O pedido a ser adicionado.
     * @throws IllegalArgumentException se o pedido for nulo.
     */
    public void adicionarPedido(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não pode ser nulo");
        }
        pedidos.add(pedido);

        Cliente cliente = pedido.getCliente();
        if (cliente != null) {
            cliente.adicionarPizzasCompradas(pedido.getQuantidadeItens());
        }
    }

    /**
     * Retorna todos os pedidos associados a um determinado CPF de cliente.
     *
     * @param cpfCliente O CPF do cliente.
     * @return Lista de pedidos do cliente, ou lista vazia se não houver nenhum.
     */
    public List<Pedido> getPedidosPorCliente(String cpfCliente) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.getCliente() != null && p.getCliente().getCpf().equals(cpfCliente)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    /**
     * Retorna uma cópia da lista com todos os pedidos registrados.
     *
     * @return Lista de todos os pedidos.
     */
    public List<Pedido> getTodosPedidos() {
        return new ArrayList<>(pedidos);
    }

}
