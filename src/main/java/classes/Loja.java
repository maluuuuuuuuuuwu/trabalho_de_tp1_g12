package classes;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.ArrayList;

// Classe que representa uma loja com gerenciamento de estoque, clientes, pedidos, funcionários e descontos.
// author malu
public class Loja {
    private List<ItemEstoque> estoque = new ArrayList<>();
    private List<Funcionarios> lista_funcionarios = new ArrayList<>();
    private List<Pedido> lista_pedidos = new ArrayList<>();
    private List<Descontos> lista_descontos = new ArrayList<>();
    private final String endereco = "UnB, campus Darcy Ribeiro, ICC Norte, subsolo modulo 9, linf 3";
    private List<Cliente> lista_clientes = new ArrayList<>();
    private List<Item> itens_oferecidos = new ArrayList<>();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Construtor padrão da classe Loja (nota: esse método deveria ser um construtor de fato, sem 'void').
     * Entrada: nenhuma  
     * Saída: objeto Loja com listas vazias
     */
    public Loja() {}

    /**
     * Adiciona um ouvinte para mudanças de propriedade.  
     * Entrada: listener (PropertyChangeListener)  
     * Saída: nenhuma
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Remove um ouvinte de mudanças de propriedade.  
     * Entrada: listener (PropertyChangeListener)  
     * Saída: nenhuma
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Retorna a lista de itens oferecidos pela loja.  
     * Entrada: nenhuma  
     * Saída: lista de Item
     */
    public List<Item> getItens_oferecidos() {
        return itens_oferecidos;
    }

    /**
     * Adiciona um item à lista de itens oferecidos.  
     * Entrada: item (Item)  
     * Saída: nenhuma
     */
    public void addItemOferecido(Item item){
        if (item != null) {
            itens_oferecidos.add(item);
        }
    }

    /**
     * Adiciona um item ao estoque e notifica ouvintes da mudança.  
     * Entrada: item (ItemEstoque)  
     * Saída: nenhuma
     */
    public void addItemEstoque(ItemEstoque item) {
        if (item != null) {
            estoque.add(item);
            support.firePropertyChange("estoque", null, item);
        }
    }

    /**
     * Adiciona um cliente à lista de clientes.  
     * Entrada: cliente (Cliente)  
     * Saída: nenhuma
     */
    public void addCliente(Cliente cliente) {
        if (cliente != null) {
            lista_clientes.add(cliente);
        }
    }

    /**
     * Adiciona um funcionário à lista de funcionários.  
     * Entrada: funcionario (Funcionarios)  
     * Saída: nenhuma
     */
    public void addFuncionario(Funcionarios funcionario) {
        if (funcionario != null) {
            lista_funcionarios.add(funcionario);
        }
    }

    /**
     * Adiciona um pedido à lista de pedidos.  
     * Entrada: pedido (Pedido)  
     * Saída: nenhuma
     */
    public void addPedido(Pedido pedido) {
        if (pedido != null) {
            lista_pedidos.add(pedido);
        }
    }

    /**
     * Adiciona um desconto à lista de descontos.  
     * Entrada: desconto (Descontos)  
     * Saída: nenhuma
     */
    public void addDesconto(Descontos desconto) {
        if (desconto != null) {
            lista_descontos.add(desconto);
        }
    }

    /**
     * Retorna uma cópia da lista de itens em estoque.  
     * Entrada: nenhuma  
     * Saída: lista de ItemEstoque
     */
    public List<ItemEstoque> getEstoque() {
        return new ArrayList<>(estoque);
    }

    /**
     * Retorna uma cópia da lista de funcionários.  
     * Entrada: nenhuma  
     * Saída: lista de Funcionarios
     */
    public List<Funcionarios> getLista_funcionarios() {
        return new ArrayList<>(lista_funcionarios);
    }

    /**
     * Retorna uma cópia da lista de clientes.  
     * Entrada: nenhuma  
     * Saída: lista de Cliente
     */
    public List<Cliente> getLista_clientes() {
        return new ArrayList<>(lista_clientes);
    }

    /**
     * Retorna uma cópia da lista de pedidos.  
     * Entrada: nenhuma  
     * Saída: lista de Pedido
     */
    public List<Pedido> getLista_pedidos() {
        return new ArrayList<>(lista_pedidos);
    }

    /**
     * Retorna uma cópia da lista de descontos.  
     * Entrada: nenhuma  
     * Saída: lista de Descontos
     */
    public List<Descontos> getLista_descontos() {
        return new ArrayList<>(lista_descontos);
    }

    /**
     * Retorna o endereço fixo da loja.  
     * Entrada: nenhuma  
     * Saída: String com o endereço
     */
    public String getEndereco() {
        return endereco;
    }

    /**
     * Cadastra um novo cliente a partir de dados fornecidos.  
     * Entrada: cpf, endereco, telefone, nome, senha (todos String)  
     * Saída: nenhuma
     */
    public void cadastrarCliente(String cpf, String endereco, String telefone, String nome, String senha) {
        Cliente cliente = new Cliente(cpf, endereco, telefone, nome, senha);
        lista_clientes.add(cliente);
    }

    /**
     * Cadastra um novo funcionário com função e dados pessoais.  
     * Entrada: cpf, funcao, endereco, telefone, nome, senha (todos String)  
     * Saída: nenhuma
     */
    public void cadastrarFuncionario(String cpf, String funcao, String endereco, String telefone, String nome, String senha) {
        Funcionarios funcionario = new Funcionarios(funcao, cpf, endereco, telefone, nome, senha);
        lista_funcionarios.add(funcionario);
    }

    /**
     * Calcula o tempo médio (em minutos) de atendimento com base no número de pedidos e funcionários.  
     * Entrada: nenhuma  
     * Saída: tempo médio (double), -1 se não há funcionários
     */
    public double calcularTempo() {
        int numeroPedidos = lista_pedidos.size();
        int numeroFuncionarios = lista_funcionarios.size();

        if (numeroFuncionarios == 0) return -1;

        int tempoTotalMinutos = numeroPedidos * 50;
        return (double) tempoTotalMinutos / numeroFuncionarios;
    }

    /**
     * Calcula o valor do frete com base no endereço do cliente.  
     * Entrada: enderecoCliente (String)  
     * Saída: valor do frete (double)  
     * Lança IllegalArgumentException se endereço for inválido ou fora da área de entrega.
     */
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

    /**
     * Remove um item da lista de itens oferecidos.  
     * Entrada: item (Item)  
     * Saída: true se foi removido, false caso contrário
     */
    public boolean removerItemOferecido(Item item) {
        return item != null && itens_oferecidos.remove(item);
    }

    /**
     * Remove um item do estoque.  
     * Entrada: item (ItemEstoque)  
     * Saída: true se foi removido, false caso contrário
     */
    public boolean removerItemEstoque(ItemEstoque item) {
        return item != null && estoque.remove(item);
    }

    /**
     * Diminui em 1 a quantidade de um item no estoque, se ele existir e tiver quantidade maior que 0.  
     * Entrada: itemName (String)  
     * Saída: nenhuma
     */
    public void removerUmItemEstoque(String itemName) {
        for (ItemEstoque item : estoque) {
            if (item.getNome().equals(itemName) && item.getQuantidade() > 0) {
                item.setQuantidade(item.getQuantidade() - 1);
                break;
            }
        }
    }
}
