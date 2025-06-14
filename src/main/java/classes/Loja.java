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
public class Loja {
    private List<String> estoque = new ArrayList<>();
    private List<Funcionarios> lista_funcionarios = new ArrayList<>();
    private List<Pedido> lista_pedidos = new ArrayList<>();
    private List<Desconto> lista_descontos = new ArrayList<>();
    private final String endereco = "UnB, campus Darcy Ribeiro, ICC Norte, subsolo modulo 9, linf 3";
    private List<Cliente> lista_clientes = new ArrayList<>();

    public void addItemEstoque(String item) {
        if (item != null && !item.trim().isEmpty()) {
            estoque.add(item);
        }
    }

    public void addCliente(Cliente cliente) {
        if (cliente != null) {
            lista_clientes.add(cliente);
        }
    }

    public void addFuncionario(Funcionarios funcionario) {
        if (funcionario != null) {
            lista_funcionarios.add(funcionario);
        }
    }

    public void addPedido(Pedido pedido) {
        if (pedido != null) {
            lista_pedidos.add(pedido);
        }
    }

    public void addDesconto(Desconto desconto) {
        if (desconto != null) {
            lista_descontos.add(desconto);
        }
    }

    public List<String> getEstoque() {
        return new ArrayList<>(estoque); // retornar cópia para segurança
    }

    public List<Funcionarios> getLista_funcionarios() {
        return new ArrayList<>(lista_funcionarios);
    }

    public List<Cliente> getLista_clientes() {
        return new ArrayList<>(lista_clientes);
    }

    public List<Pedido> getLista_pedidos() {
        return new ArrayList<>(lista_pedidos);
    }

    public List<Desconto> getLista_descontos() {
        return new ArrayList<>(lista_descontos);
    }

    public String getEndereco() {
        return endereco;
    }
    
    public void cadastrarCliente(String cpf, String endereco, String telefone, String nome) {
        Cliente cliente = new Cliente(cpf, endereco, telefone, nome);
        lista_clientes.add(cliente);
    }
    
    public double calcularTempo() {
        int numeroPedidos = lista_pedidos.size();
        int numeroFuncionarios = lista_funcionarios.size();

        if (numeroFuncionarios == 0) {
            return -1; // Erro: sem funcionários
        }

        int tempoTotalMinutos = numeroPedidos * 50;
        double tempo = (double) tempoTotalMinutos / numeroFuncionarios;

        return tempo; // em minutos, pode ser convertido para R$ se desejar
    }

    public double calcularFrete(String enderecoCliente) throws IllegalArgumentException {
        if (enderecoCliente == null || enderecoCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser nulo ou vazio!");
        }

        // Converter para minúsculas para comparação case-insensitive
        String enderecoLower = enderecoCliente.toLowerCase();

        if (enderecoLower.contains("asa sul")) {
            return 15.0;
        } else if (enderecoLower.contains("asa norte")) {
            return 10.0;
        } else if (enderecoLower.contains("lago norte")) {
            return 20.0;
        } else if (enderecoLower.contains("lago sul")) {
            return 25.0;
        } else if (enderecoLower.contains("sudoeste")) {
            return 20.0;
        } else if (enderecoLower.contains("noroeste")) {
            return 15.0;
        } else if (enderecoLower.contains("unb")) {
            return 5.0;
        } else {
            throw new IllegalArgumentException("Desculpe, não entregamos no endereço fornecido: " + enderecoCliente);
        }
    }
    
    
}
