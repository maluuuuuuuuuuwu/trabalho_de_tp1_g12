/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representa uma transação de compra completa, unindo cliente, itens, loja e descontos.
 * @author alexb e malu
 */
public class Pedido {
    private List<SubPedido> itens;
    private GerenciadorDescontos listaDescontos;
    private Loja loja;
    private Cliente cliente;
    private LocalDate data; // A data em que o pedido foi criado

    /**
     * Constrói um novo Pedido com todos os elementos necessários.
     */
    public Pedido(List<SubPedido> itens, GerenciadorDescontos listaDescontos, Loja loja, Cliente cliente) {
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("Lista de itens não pode ser nula ou vazia");
        }
        // ... (outras validações if)
        
        this.itens = itens;
        this.listaDescontos = listaDescontos;
        this.loja = loja;
        this.cliente = cliente;
        
        // ADIÇÃO: Registra a data exata da criação do pedido
        this.data = LocalDate.now(); 
    }

    /**
     * Calcula o valor total do pedido incluindo itens e frete, SEM descontos.
     * @return Valor total bruto do pedido.
     */
    public double calculaTotal() {
        double totalItens = itens.stream()
                                  .mapToDouble(SubPedido::calculaSubtotal)
                                  .sum();
        return totalItens + getFrete();
    }
    
    /**
     * Calcula o valor total do pedido com o melhor desconto aplicável (usando o sistema refatorado).
     * @return Valor total com o melhor desconto aplicado.
     */
    public double calculaTotal_desconto() {
        // MUDANÇA: Passando o próprio pedido para o gerenciador.
        return this.listaDescontos.aplicarMelhorDesconto(this);
    }
    
    /**
    * Calcula o valor total do pedido incluindo itens e frete, COM descontos.
    * Este é o método que deve ser usado para obter o valor final.
    * @return Valor total final do pedido com desconto aplicado.
    */
    public double getValorTotal() {
        double valorBaseItens = itens.stream()
                                  .mapToDouble(SubPedido::calculaSubtotal)
                                  .sum();
        double valorComFrete = valorBaseItens + getFrete();

        // Aplica o melhor desconto disponível
        return listaDescontos.aplicarMelhorDesconto(valorComFrete, getQuantidadeItens());
    }

    /**
     * Retorna o valor base dos itens do pedido, sem considerar frete ou descontos.
     * Este método será útil para calcular o valor do desconto aplicado.
     * @return Valor base dos itens.
     */
    public double getValorBase() {
        return itens.stream()
                    .mapToDouble(SubPedido::calculaSubtotal)
                    .sum();
    }


    public String getItensFormatados() {
    StringBuilder sb = new StringBuilder();
    for (SubPedido sp : this.itens) {
        sb.append(String.format("- %d x %s (R$ %.2f)\n", 
            sp.getQuantidade(), 
            sp.getItem().getNome(), 
            sp.getItem().getPreco()
        ));
    }
    return sb.toString();
}
     /**
     * ADIÇÃO: Retorna a data do pedido formatada como "dd/MM/yyyy".
     * @return A data formatada como String.
     */
    public String getDataFormatada() {
        if (this.data == null) {
            return "Data indisponível";
        }
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.data.format(formatador);
    }

    /**
     * Retorna a quantidade total de itens no pedido.
     * @return Quantidade total de itens.
     */
    public int getQuantidadeItens() {
        return itens.stream()
                      .mapToInt(SubPedido::getQuantidade)
                      .sum();
    }
    
    // --- O resto dos seus métodos (getters, etc.) ---

    public List<SubPedido> getItens() {
        return List.copyOf(itens);
    }
    
    // ADIÇÃO: Método para permitir que promoções modifiquem o pedido
    public List<SubPedido> getItensModificavel() {
        return this.itens;
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
    
    public double getFrete() {
        return loja.calcularFrete(cliente.getEndereco());
    }
    
    public LocalDate getData() {
    return this.data;
}
    
    public double getTempo() {
        return loja.calcularTempo();
    }
    
    @Override
    public String toString() {
        return String.format("Pedido [%d itens, Total: R$%.2f, Cliente: %s]", 
                             getQuantidadeItens(), calculaTotal(), cliente.getNome());
    }
}