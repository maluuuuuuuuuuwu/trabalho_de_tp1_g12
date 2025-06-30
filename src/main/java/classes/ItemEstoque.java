/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 * Classe que representa um item armazenado no estoque, contendo nome, código e quantidade.
 * 
 * Autor: malu
 */
public class ItemEstoque {
    protected String nome, codigo;
    protected int quantidade;

    /**
     * Construtor para inicializar um item de estoque com nome, código e quantidade.
     * Entrada: nome (String), codigo (String), quantidade (int)  
     * Saída: objeto ItemEstoque com os atributos definidos
     */
    public ItemEstoque(String nome, String codigo, int quantidade) {
        this.nome = nome;
        this.codigo = codigo;
        this.quantidade = quantidade;
    }

    /**
     * Retorna o nome do item.
     * Entrada: nenhuma  
     * Saída: String com o nome do item
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o código do item.
     * Entrada: nenhuma  
     * Saída: String com o código do item
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Retorna a quantidade disponível do item em estoque.
     * Entrada: nenhuma  
     * Saída: int representando a quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define ou atualiza o nome do item.
     * Entrada: nome (String)  
     * Saída: nenhuma
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define ou atualiza o código do item.
     * Entrada: codigo (String)  
     * Saída: nenhuma
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Define ou atualiza a quantidade do item em estoque.
     * Entrada: quantidade (int)  
     * Saída: nenhuma
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
