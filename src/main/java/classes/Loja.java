package classes;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma loja com gerenciamento de estoque, clientes, pedidos, funcionários e descontos.
 * 
 * author malu
 */
public class Loja {
    private Estoque estoque = new Estoque();
    private GerenciadorUsuarios usuarios = new GerenciadorUsuarios();
    private GerenciadorPedidos lista_pedidos = new GerenciadorPedidos();
    private GerenciadorDescontos lista_descontos = new GerenciadorDescontos();
    private List<Item> itens_oferecidos = new ArrayList<>();

    /**
     * Lista unificada de usuários do sistema (clientes e funcionários).
     * Utilizada para login e listagem genérica de usuários.
     */
    private List<Usuario_Interface> lista_usuarios = new ArrayList<>();

    private final String endereco = "UnB, campus Darcy Ribeiro, ICC Norte, subsolo modulo 9, linf 3";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Construtor padrão da classe Loja.
     * Entrada: nenhuma  
     * Saída: objeto Loja com listas vazias
     */
    public Loja() {}

     /**
     * adiciona um novo item ao estoque.
     * @param item Item a ser adicionado (não pode ser nulo)
     * @throws IllegalArgumentException Se o item for nulo
     */
    public void addItemEstoque(ItemEstoque item){
        this.estoque.addItem(item);
    }
    
    /**
     * Remove um item do estoque.
     * @param item Item a ser removido
     * @return true se o item foi removido com sucesso
     */
    public boolean removerItemEstoque(ItemEstoque item){
        return this.estoque.removerItem(item);
    }
    
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
     * Adiciona um item à lista de itens oferecidos.  
     * Entrada: item (Item)  
     * Saída: nenhuma
     */
    public void addItemOferecido(Item item) {
        if (item != null) {
            itens_oferecidos.add(item);
            support.firePropertyChange("itens_oferecidos", null, item);
        }
    }

    /**
     * Adiciona um cliente à lista de clientes e à lista unificada de usuários.  
     * Entrada: cliente (Cliente)  
     * Saída: nenhuma
     */
    public void addCliente(Cliente cliente) {
        usuarios.cadastrarCliente(cliente);
    }

    /**
     * Adiciona um funcionário à lista de funcionários e à lista unificada de usuários.  
     * Entrada: funcionario (Funcionarios)  
     * Saída: nenhuma
     */
    public void addFuncionario(Funcionarios funcionario) {
        usuarios.cadastrarFuncionario(funcionario);
    }

    /**
     * Adiciona um pedido à lista de pedidos.  
     * Entrada: pedido (Pedido)  
     * Saída: nenhuma
     */
    public void addPedido(Pedido pedido) {
        lista_pedidos.adicionarPedido(pedido);
    }

    /**
     * Adiciona um desconto à lista de descontos.  
     * Entrada: desconto (Descontos)  
     * Saída: nenhuma
     */
    public void addDesconto(Descontos desconto) {
        lista_descontos.adicionarDesconto(desconto);
    }

    /**
     * Retorna uma cópia da lista de itens em estoque.  
     * Entrada: nenhuma  
     * Saída: lista de ItemEstoque
     */
    public Estoque getEstoque() {
        return estoque;
    }

    /**
     * Retorna uma cópia da lista de funcionários.  
     * Entrada: nenhuma  
     * Saída: lista de Funcionarios
     */
    public List<Funcionarios> getLista_funcionarios() {
        return usuarios.getFuncionarios();
    }

    /**
     * Retorna uma cópia da lista de clientes.  
     * Entrada: nenhuma  
     * Saída: lista de Cliente
     */
    public List<Cliente> getLista_clientes() {
        return usuarios.getClientes();
    }

    /**
     * Retorna uma cópia da lista de pedidos.  
     * Entrada: nenhuma  
     * Saída: lista de Pedido
     */
    public List<Pedido> getLista_pedidos() {
        return lista_pedidos.getTodosPedidos();
    }

    /**
     * Retorna uma cópia da lista de descontos.  
     * Entrada: nenhuma  
     * Saída: lista de Descontos
     */
    public List<Descontos> getLista_descontos() {
        return lista_descontos.getDescontosDisponiveis();
    }
    
    public GerenciadorDescontos getDescontos(){
        return lista_descontos;
    }

    /**
     * Retorna a lista de itens oferecidos pela loja.  
     * Entrada: nenhuma  
     * Saída: lista de Item
     */
    public List<Item> getItens_oferecidos() {
        return new ArrayList<>(itens_oferecidos);
    }

    // remove fuincionario
    public boolean removerFuncionario(String cpf) {
        return usuarios.removerFuncionario(cpf);
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
     * Verifica se as credenciais são válidas e retorna o tipo de usuário (cliente ou funcionário).  
     * Entrada: cpf (String), senha (String)  
     * Saída: "cliente", "funcionario" ou null se inválido
     */
    public String verificarCredenciais(String cpf, String senha) {
        if (cpf == null || senha == null || cpf.trim().isEmpty() || senha.trim().isEmpty()) {
            return null;
        }
        List<Usuario_Interface> lista_usuarios = new ArrayList<>();
        lista_usuarios.addAll(usuarios.getClientes());
        lista_usuarios.addAll(usuarios.getFuncionarios());

        for (Usuario_Interface usuario : lista_usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                if (usuario.getSenha().equals(senha)) {
                    return (usuario instanceof Cliente) ? "cliente" : "funcionario";
                }
                return null; // CPF existe, mas senha incorreta
            }
        }

        return null;
    }
    
    /**
     * Cadastra um novo cliente a partir de dados fornecidos.  
     * Entrada: cpf, endereco, telefone, nome, senha (todos String)  
     * Saída: nenhuma
     */
    public void cadastrarCliente(String cpf, String endereco, String telefone, String nome, String senha) {
        Cliente cliente = new Cliente(cpf, endereco, telefone, nome, senha);
        usuarios.cadastrarCliente(cliente);
    }

    /**
     * Cadastra um novo funcionário com função e dados pessoais.  
     * Entrada: cpf, funcao, endereco, telefone, nome, senha (todos String)  
     * Saída: nenhuma
     */
    public void cadastrarFuncionario(String cpf, String funcao, String endereco, String telefone, String nome, String senha) {
        Funcionarios funcionario = new Funcionarios(funcao, cpf, endereco, telefone, nome, senha);
        usuarios.cadastrarFuncionario(funcionario);
    }

    /**
     * Calcula o tempo médio (em minutos) de atendimento com base no número de pedidos e funcionários.  
     * Entrada: nenhuma  
     * Saída: tempo médio (double), -1 se não há funcionários
     */
    public double calcularTempo() {
        int numeroPedidos = lista_pedidos.getTodosPedidos().size();
        int numeroFuncionarios = usuarios.getFuncionarios().size();

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

}
