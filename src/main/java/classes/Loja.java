package classes;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.ArrayList;
//authot malu

public class Loja {
    private List<ItemEstoque> estoque = new ArrayList<>();
    private List<Funcionarios> lista_funcionarios = new ArrayList<>();
    private List<Pedido> lista_pedidos = new ArrayList<>();
    private List<Descontos> lista_descontos = new ArrayList<>();
    private final String endereco = "UnB, campus Darcy Ribeiro, ICC Norte, subsolo modulo 9, linf 3";
    private List<Cliente> lista_clientes = new ArrayList<>();
    private List<Item> itens_oferecidos = new ArrayList<>();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void Loja(){}

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public List<Item> getItens_oferecidos() {
        return itens_oferecidos;
    }

    public void addItemOferecido(Item item){
        if (item != null) {
            itens_oferecidos.add(item);
        }
    }

    public void addItemEstoque(ItemEstoque item) {
        if (item != null) {
            estoque.add(item);
            support.firePropertyChange("estoque", null, item);
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

    public void addDesconto(Descontos desconto) {
        if (desconto != null) {
            lista_descontos.add(desconto);
        }
    }

    public List<ItemEstoque> getEstoque() {
        return new ArrayList<>(estoque);
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

    public List<Descontos> getLista_descontos() {
        return new ArrayList<>(lista_descontos);
    }

    public String getEndereco() {
        return endereco;
    }

    public void cadastrarCliente(String cpf, String endereco, String telefone, String nome, String senha) {
        Cliente cliente = new Cliente(cpf, endereco, telefone, nome, senha);
        lista_clientes.add(cliente);
    }

    public void cadastrarFuncionario(String cpf, String funcao, String endereco, String telefone, String nome, String senha) {
        Funcionarios funcionario = new Funcionarios(funcao, cpf, endereco, telefone, nome, senha);
        lista_funcionarios.add(funcionario);
    }

    public double calcularTempo() {
        int numeroPedidos = lista_pedidos.size();
        int numeroFuncionarios = lista_funcionarios.size();

        if (numeroFuncionarios == 0) return -1;

        int tempoTotalMinutos = numeroPedidos * 50;
        return (double) tempoTotalMinutos / numeroFuncionarios;
    }

    public double calcularFrete(String enderecoCliente) {
        if (enderecoCliente == null || enderecoCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço não pode ser nulo ou vazio!");
        }

        String enderecoLower = enderecoCliente.toLowerCase();

        if (enderecoLower.contains("asa sul")) return 15.0;
        else if (enderecoLower.contains("asa norte")) return 10.0;
        else if (enderecoLower.contains("lago norte")) return 20.0;
        else if (enderecoLower.contains("lago sul")) return 25.0;
        else if (enderecoLower.contains("sudoeste")) return 20.0;
        else if (enderecoLower.contains("noroeste")) return 15.0;
        else if (enderecoLower.contains("unb")) return 5.0;
        else throw new IllegalArgumentException("Desculpe, não entregamos no endereço fornecido: " + enderecoCliente);
    }

    public boolean removerItemOferecido(Item item) {
        return item != null && itens_oferecidos.remove(item);
    }

    public boolean removerItemEstoque(ItemEstoque item) {
        return item != null && estoque.remove(item);
    }

    public void removerUmItemEstoque(String itemName) {
        for (ItemEstoque item : estoque) {
            if (item.getNome().equals(itemName) && item.getQuantidade() > 0) {
                item.setQuantidade(item.getQuantidade() - 1);
                break;
            }
        }
    }
}
