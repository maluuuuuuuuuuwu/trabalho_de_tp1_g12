/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe que representa um cliente da pizzaria, herdando de Pessoa.
 * 
 * @author alexb
 */
public class Cliente extends Pessoa {
    private List<Pedido> pedidos = new ArrayList<>();
    private int pizzasCompradas;

    public Cliente(String cpf, String endereco, String telefone, String nome, String senha) {
        super(cpf, endereco, telefone, nome, senha);
    }

    /**
     * Adiciona um novo pedido à lista do cliente
     * @param pedido O pedido a ser adicionado
     */
    public void adicionarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
    }

    /**
     * Incrementa o contador de pizzas compradas
     * @param quantidade A quantidade de pizzas a adicionar
     */
    public void adicionarPizzasCompradas(int quantidade) {
        this.pizzasCompradas += quantidade;
    }

    /**
     * @return Total de pizzas compradas pelo cliente
     */
    public int getPizzasCompradas() {
        return this.pizzasCompradas;
    }

    /**
     * Método para resetar o contador quando o cliente resgata uma pizza grátis
     */
    public void resetarPizzasCompradas() {
        this.pizzasCompradas = this.pizzasCompradas % 5;
    }

    /**
     * @return Lista de pedidos do cliente (cópia defensiva)
     */
    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }

    /**
     * Implementação do método abstrato para exibir informações do cliente
     */
    @Override
    public void exibirInformacoes() {
        System.out.println("Cliente: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Endereço: " + getEndereco());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("Total de pizzas compradas: " + pizzasCompradas);
        System.out.println("Total de pedidos: " + pedidos.size());
    }
}