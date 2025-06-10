/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author malu
 */
public class Funcionarios {
    protected String nome, CPF, funcao, senha;

    public Funcionarios(String nome, String CPF, String funcao,String senha) {
        this.nome = nome;
        this.CPF = CPF;
        this.funcao = funcao;
        this.senha = senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public String getCPF() {
        return CPF;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

}
