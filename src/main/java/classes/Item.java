/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;
import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author malu
 */
public class Item {
    private String nome;
    private List<String> ingredientes;
    private double preco;
    private Loja loja;

    public Item(String nome, List<String> ingredientes, double preco, Loja loja) {
        this.ingredientes = ingredientes;
        this.preco = preco;
        this.loja = loja;
        this.nome = nome;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public double getPreco() {
        return preco;
    }

    public String getnome() {
        return nome;
    }
    
    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
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

    public void remove_estoque() {
        List<ItemEstoque> estoque = loja.getEstoque();
        for(String ingrediente : ingredientes){
            if(verificaEstoque()){
                loja.removerUmItemEstoque(ingrediente);
            }
        }
    }
}
