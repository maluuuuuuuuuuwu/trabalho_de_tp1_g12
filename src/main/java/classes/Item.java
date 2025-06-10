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
    private List<String> ingredientes;
    private double preco;
    private Loja loja;

    public Item(List<String> ingredientes, double preco, Loja loja) {
        this.ingredientes = ingredientes;
        this.preco = preco;
        this.loja = loja;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public double getPreco() {
        return preco;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean verificaEstoque() {
        List<String> estoque = loja.getEstoque();
        for (String ingrediente : ingredientes) {
            if (!estoque.contains(ingrediente)) {
                return false;
            }
        }
        return true;
    }
}
