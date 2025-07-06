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

    public void setNome(String nome) {
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
    public String getNome() {
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
public boolean verificaEstoque(int quantidade) {
    // Itera sobre a lista de ingredientes do item
    for (String nomeIngrediente : ingredientes) {

        // Passo 1: Busca o item no estoque da loja
        ItemEstoque ingredienteNoEstoque = loja.getEstoque().buscarItem(nomeIngrediente);

        // Passo 2: VERIFICA SE O INGREDIENTE FOI ENCONTRADO (esta é a correção)
        if (ingredienteNoEstoque == null) {
            // Se o ingrediente nem existe, não há como ter estoque dele.
            System.err.println("AVISO DE ESTOQUE: O ingrediente '" + nomeIngrediente + "' não foi encontrado no cadastro do estoque!");
            return false; 
        }

        // Passo 3: Agora que sabemos que o ingrediente existe, verificamos a quantidade
        if (ingredienteNoEstoque.getQuantidade() < quantidade) {
            // Encontrou o ingrediente, mas não tem o suficiente.
            System.err.println("AVISO DE ESTOQUE: Estoque insuficiente para o ingrediente '" + nomeIngrediente + "'");
            return false; 
        }
    }

    // Se o loop terminar, significa que todos os ingredientes foram checados e há estoque suficiente.
    return true;
}


    /**
     * Remove uma unidade de cada ingrediente necessário do estoque, se estiver disponível.
     * 
     * Entrada: nenhuma  
     * Saída: nenhuma (modifica o estoque internamente)
     */
    public void remove_estoque(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            for (String ingrediente : ingredientes) {
                loja.getEstoque().removerUmItemEstoque(ingrediente);
            }
        }
    }


}
