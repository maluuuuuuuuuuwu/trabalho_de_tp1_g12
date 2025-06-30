/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

import java.util.List;
import java.util.ArrayList;

/**
 * Classe que representa um item vendido na loja, com nome, ingredientes, preço e referência à loja.
 * 
 * Autor: malu
 */
public class Item {
    private String nome;
    private List<String> ingredientes;
    private double preco;
    private Loja loja;

    /**
     * Construtor para criar um item com nome, ingredientes, preço e loja associada.
     * 
     * @param nome Nome do item
     * @param ingredientes Lista de ingredientes necessários
     * @param preco Preço do item
     * @param loja Instância da loja associada
     */
    public Item(String nome, List<String> ingredientes, double preco, Loja loja) {
        this.ingredientes = ingredientes;
        this.preco = preco;
        this.loja = loja;
        this.nome = nome;
    }

    /**
     * Retorna a lista de ingredientes do item.
     * 
     * @return Lista de Strings com os ingredientes
     */
    public List<String> getIngredientes() {
        return ingredientes;
    }

    /**
     * Retorna o preço do item.
     * 
     * @return Valor em double
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Retorna o nome do item.
     * 
     * @return String com o nome
     */
    public String getnome() {
        return nome;
    }

    /**
     * Define ou atualiza a lista de ingredientes do item.
     * 
     * @param ingredientes Lista de Strings com os ingredientes
     */
    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    /**
     * Define ou atualiza o preço do item.
     * 
     * @param preco Valor do novo preço
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Verifica se todos os ingredientes do item estão disponíveis no estoque da loja.
     * 
     * @return true se todos os ingredientes estão disponíveis com quantidade > 0, false caso contrário
     */
    public boolean verificaEstoque() {
        List<ItemEstoque> estoque = loja.getEstoque();
        for (String ingrediente : ingredientes) {
            boolean disponivel = estoque.stream()
                .filter(item -> item.getNome().equals(ingrediente))
                .anyMatch(item -> item.getQuantidade() > 0);

            if (!disponivel) {
                return false;
            }
        }
        return true;
    }

    /**
     * Remove uma unidade de cada ingrediente necessário do estoque, se estiver disponível.
     * 
     * Entrada: nenhuma  
     * Saída: nenhuma (modifica o estoque internamente)
     */
    public void remove_estoque() {
        List<ItemEstoque> estoque = loja.getEstoque();
        for (String ingrediente : ingredientes) {
            if (verificaEstoque()) {
                loja.removerUmItemEstoque(ingrediente);
            }
        }
    }
}
