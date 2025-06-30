/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 * Classe que representa um funcionário da loja, herdando dados de uma pessoa (nome, CPF, etc.)
 * e incluindo uma função específica.
 * 
 * Autor: malu
 */
public class Funcionarios extends Pessoa {
    protected String funcao;

    /**
     * Construtor para inicializar um funcionário com todos os dados.
     * 
     * @param funcao Função do funcionário na loja (ex: atendente, gerente)
     * @param cpf CPF do funcionário
     * @param endereco Endereço residencial
     * @param telefone Telefone de contato
     * @param nome Nome completo
     * @param senha Senha de acesso
     */
    public Funcionarios(String funcao, String cpf, String endereco, String telefone, String nome, String senha) {
        super(cpf, endereco, telefone, nome, senha);
        this.funcao = funcao;
    }

    /**
     * Define ou atualiza a função do funcionário.
     * 
     * @param funcao String com a nova função
     */
    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    /**
     * Define ou atualiza o CPF do funcionário.
     * 
     * @param cpf String com o novo CPF
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * Define ou atualiza o endereço do funcionário.
     * 
     * @param endereco String com o novo endereço
     */
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    /**
     * Define ou atualiza o telefone do funcionário.
     * 
     * @param telefone String com o novo telefone
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Define ou atualiza o nome do funcionário.
     * 
     * @param nome String com o novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define ou atualiza a senha do funcionário.
     * 
     * @param senha String com a nova senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna a função atual do funcionário.
     * 
     * @return String com a função
     */
    public String getFuncao() {
        return funcao;
    }

    /**
     * Retorna o CPF do funcionário.
     * 
     * @return String com o CPF
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Retorna o endereço do funcionário.
     * 
     * @return String com o endereço
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Retorna o telefone do funcionário.
     * 
     * @return String com o telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Retorna o nome do funcionário.
     * 
     * @return String com o nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a senha do funcionário.
     * 
     * @return String com a senha
     */
    public String getSenha() {
        return senha;
    }
}
