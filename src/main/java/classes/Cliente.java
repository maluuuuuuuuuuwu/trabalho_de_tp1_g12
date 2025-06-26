/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author alexb
 */
public class Cliente extends Pessoa{
    public List<Pedido> pedido =  new ArrayList<>();;

    public Cliente(String cpf, String endereco, String telefone, String nome, String senha) {
        super(cpf, endereco, telefone, nome, senha);
    }

    public void setPedido(Pedido pedido) {
        this.pedido.add(pedido);
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Pedido> getPedido() {
        return new ArrayList<>(pedido);
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    
}
