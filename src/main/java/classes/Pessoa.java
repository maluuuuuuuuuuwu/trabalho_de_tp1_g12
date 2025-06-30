/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 * Classe que representa uma pessoa genérica com informações básicas como CPF, 
 * endereço, telefone, nome e senha.
 * 
 * @author malu
 */
public class Pessoa {
    protected String cpf;
    protected String endereco;
    protected String telefone;
    protected String nome;
    protected String senha;

    /**
     * Construtor padrão. Inicializa uma pessoa sem definir atributos.
     * Entrada: nenhuma  
     * Saída: objeto Pessoa com atributos nulos
     */
    public Pessoa() {
    }

    /**
     * Construtor com todos os atributos.
     * Entrada: cpf, endereco, telefone, nome, senha  
     * Saída: objeto Pessoa com os atributos definidos
     */
    public Pessoa(String cpf, String endereco, String telefone, String nome, String senha) {
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.nome = nome;
        this.senha = senha;
    }

    /**
     * Retorna o CPF da pessoa.
     * Entrada: nenhuma  
     * Saída: String cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Retorna o endereço da pessoa.
     * Entrada: nenhuma  
     * Saída: String endereco
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Retorna o telefone da pessoa.
     * Entrada: nenhuma  
     * Saída: String telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Retorna o nome da pessoa.
     * Entrada: nenhuma  
     * Saída: String nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a senha da pessoa.
     * Entrada: nenhuma  
     * Saída: String senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define o CPF da pessoa.
     * Entrada: String cpf  
     * Saída: nenhuma
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Define o endereço da pessoa.
     * Entrada: String endereco  
     * Saída: nenhuma
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Define o telefone da pessoa.
     * Entrada: String telefone  
     * Saída: nenhuma
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Define o nome da pessoa.
     * Entrada: String nome  
     * Saída: nenhuma
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define a senha da pessoa.
     * Entrada: String senha  
     * Saída: nenhuma
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
