/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;
import java.io.Serializable;
/**
 * Classe que representa um funcionário da loja, herdando de Pessoa.
 * 
 * @author malu
 */
public class Funcionarios extends Pessoa implements Usuario_Interface, Serializable{
    private String funcao;

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
     * @return A função atual do funcionário
     */
    public String getFuncao() {
        return funcao;
    }

    /**
     * Define ou atualiza a função do funcionário
     * @param funcao Nova função do funcionário
     */
    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    /**
     * Implementação do método abstrato para exibir informações do funcionário
     */
    @Override
    public void exibirInformacoes() {
        System.out.println("Funcionário: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Função: " + funcao);
        System.out.println("Telefone: " + getTelefone());
        System.out.println("Endereço: " + getEndereco());
    }
}