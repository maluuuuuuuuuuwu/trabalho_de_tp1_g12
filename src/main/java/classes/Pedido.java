/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.util.List;

/**
 *
 * @author alexb e malu
 */
public class Pedido {
    private List<SubPedido> itens;
    private GerenciadorDescontos listaDescontos;
    private Loja loja;
    private Cliente cliente;

    /**
     * Constrói um novo Pedido com todos os elementos necessários
     * 
     * @param itens Lista de itens do pedido (não pode ser nula ou vazia)
     * @param listaDescontos Gerenciador de descontos aplicáveis (não pode ser nulo)
     * @param loja Loja de origem do pedido (não pode ser nula)
     * @param cliente Cliente destinatário (não pode ser nulo)
     * @throws IllegalArgumentException Se qualquer parâmetro obrigatório for nulo
     */
    public Pedido(List<SubPedido> itens, GerenciadorDescontos listaDescontos, Loja loja, Cliente cliente) {
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("Lista de itens não pode ser nula ou vazia");
        }
        if (listaDescontos == null) {
            throw new IllegalArgumentException("Gerenciador de descontos não pode ser nulo");
        }
        if (loja == null) {
            throw new IllegalArgumentException("Loja não pode ser nula");
        }
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo");
        }
        
        this.itens = itens;
        this.listaDescontos = listaDescontos;
        this.loja = loja;
        this.cliente = cliente;
    }

    /**
     * Calcula o valor total do pedido incluindo itens e frete, sem descontos
     * 
     * @return Valor total do pedido (itens + frete)
     */
    public double calculaTotal() {
        double totalItens = itens.stream()
                               .mapToDouble(SubPedido::calculaSubtotal)
                               .sum();
        return totalItens + getFrete();
    }
    
    /**
     * Calcula o valor total do pedido com o melhor desconto aplicável
     * 
     * @return Valor total com desconto aplicado (itens + frete - desconto)
     */
    public double calculaTotal_desconto() {
        int quantidadeTotal = getQuantidadeItens();
        double total = calculaTotal();
        return listaDescontos.aplicarMelhorDesconto(total, quantidadeTotal);
    }
    
    // Getters e métodos auxiliares
    
    public List<SubPedido> getItens() {
        return List.copyOf(itens); // Retorna cópia imutável para encapsulamento
    }

    public GerenciadorDescontos getListaDescontos() {
        return listaDescontos;
    }

    public Loja getLoja() {
        return loja;
    }

    public Cliente getCliente() {
        return cliente;
    }
    
    /**
     * Calcula a quantidade total de itens no pedido (soma de todas as quantidades)
     * 
     * @return Quantidade total de itens
     */
    public int getQuantidadeItens() {
        return itens.stream()
                   .mapToInt(SubPedido::getQuantidade)
                   .sum();
    }
    
    /**
     * Calcula o valor do frete conforme regras da loja
     * 
     * @return Valor do frete calculado
     */
    public double getFrete() {
        return loja.calcularFrete(cliente.getEndereco());
    }
    
    /**
     * Estima o tempo total de entrega
     * 
     * @return Tempo estimado em horas
     */
    public double getTempo() {
        return loja.calcularTempo();
    }
    
    /**
     * Representação textual do pedido
     * 
     * @return String formatada com informações resumidas do pedido
     */
    @Override
    public String toString() {
        return String.format("Pedido [%d itens, Total: R$%.2f, Cliente: %s]", 
                           getQuantidadeItens(), calculaTotal(), cliente.getNome());
    }
}


