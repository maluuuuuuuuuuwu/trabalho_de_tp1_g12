package classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia todos os usuários do sistema (clientes e funcionários).
 * Responsável por cadastro, autenticação e consulta de usuários.
 */
/**
 *
 * @author malu
 */
public class GerenciadorUsuarios {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Funcionarios> funcionarios = new ArrayList<>();

    /**
     * Cadastra um novo cliente no sistema.
     * @param cliente Cliente a ser cadastrado (não pode ser nulo)
     * @throws IllegalArgumentException Se o cliente for nulo
     */
    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    /**
     * Cadastra um novo funcionário no sistema.
     * @param funcionario Funcionário a ser cadastrado (não pode ser nulo)
     * @throws IllegalArgumentException Se o funcionário for nulo
     */
    public void cadastrarFuncionario(Funcionarios funcionario) {
        funcionarios.add(funcionario);
    }

    /**
     * Autentica um usuário com CPF e senha.
     * @param cpf CPF do usuário
     * @param senha Senha do usuário
     * @return Objeto Usuario_Interface autenticado ou null se falhar
     */
    public Usuario_Interface autenticar(String cpf, String senha) {
        List<Usuario_Interface> todosUsuarios = new ArrayList<>();
        todosUsuarios.addAll(clientes);
        todosUsuarios.addAll(funcionarios);

        return todosUsuarios.stream()
                          .filter(u -> u.getCpf().equals(cpf) && u.getSenha().equals(senha))
                          .findFirst()
                          .orElse(null);
    }

    public boolean removerFuncionario(String cpf) {
        return funcionarios.removeIf(func -> func.getCpf().equals(cpf));
    }
    
    /**
     * Obtém lista segura de todos os clientes.
     * @return Cópia da lista de clientes
     */
    public List<Cliente> getClientes() {
        return new ArrayList<>(clientes);
    }

    /**
     * Obtém lista segura de todos os funcionários.
     * @return Cópia da lista de funcionários
     */
    public List<Funcionarios> getFuncionarios() {
        return new ArrayList<>(funcionarios);
    }

}
