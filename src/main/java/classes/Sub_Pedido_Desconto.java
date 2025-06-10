import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// =======================================================
// INTERFACES PARA AS CLASSES EXTERNAS (Alex e Malu)
// Esse código interage com estas interfaces, não com as implementações concretas
// =======================================================

/**
 * Interface ICliente (Contrato para a classe Cliente do Alex).
 * Define os métodos que esse código precisa da classe Cliente.
 */
interface ICliente {
    String getCpf();
    String getNome();
    int getPizzasCompradas();
    // Em um sistema real, o método adicionarPizzasCompradas() seria chamado pela Loja ou Pedido ao finalizar um pedido.
    // void adicionarPizzasCompradas(int quantidade);
}

/**
 * Interface IItem (Contrato para a classe Item da Malu).
 * Define os métodos que esse código precisa da classe Item.
 */
interface IItem {
    String getCodigo();
    String getNome();
    double getPreco();
    String getTipo(); // Ex: "Pizza", "Refrigerante", "Sobremesa"
    String toString();
}

/**
 * Interface ILoja (Contrato para a classe Loja da Malu).
 * Define os métodos que esse código precisa da classe Loja.
 */
interface ILoja {
    List<IItem> getCardapio();
    boolean verificarDisponibilidade(IItem item, int quantidade);
}

/**
 * Interface IPedido (Contrato para a classe Pedido do Alex).
 * Define os métodos que esse código precisa da classe Pedido.
 */
interface IPedido {
    void setCliente(ICliente cliente);
    ICliente getCliente();
    void adicionarSubPedido(SubPedido subPedido);
    void adicionarDesconto(Desconto desconto);
    double calculaTotal();
    List<SubPedido> getSubpedidos();
    List<Desconto> getDescontosAplicados();
}

// =======================================================
// MINHAS CLASSES (Arthur)
// Classes SubPedido e Desconto, e suas implementações específicas.
// =======================================================

/**
 * Classe SubPedido.
 * Representa uma parte de um pedido, associando um item a uma quantidade.
 * Agrega um IItem (interface da Malu).
 */
class SubPedido {
    private IItem item;
    private int quantidade;

    /**
     * Construtor para a classe SubPedido.
     * @param item O item do menu (IItem da Malu).
     * @param quantidade A quantidade do item.
     */
    public SubPedido(IItem item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }

    // Getters
    public IItem getItem() {
        return item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Calcula o subtotal do subpedido (preço do item * quantidade).
     * @return O subtotal do subpedido.
     */
    public double calculaSubtotal() {
        return item.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return quantidade + "x " + item.getNome() + " (R$" + String.format("%.2f", item.getPreco()) + ")";
    }
}

/**
 * Classe abstrata Desconto.
 * Classe base para diferentes tipos de descontos.
 */
abstract class Desconto {
    protected String descricao;

    /**
     * Construtor para a classe abstrata Desconto.
     * @param descricao A descrição do desconto.
     */
    public Desconto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Método abstrato para aplicar o desconto.
     * @param valorOriginal O valor original sobre o qual o desconto será aplicado.
     * @param subpedidos A lista de subpedidos no pedido.
     * @param cliente O cliente associado ao pedido (ICliente do Alex), para lógica de fidelidade.
     * @return O valor após a aplicação do desconto.
     */
    public abstract double aplicaDesconto(double valorOriginal, List<SubPedido> subpedidos, ICliente cliente);

    /**
     * Método abstrato para verificar se o desconto é elegível.
     * @param subpedidos A lista de subpedidos no pedido.
     * @param cliente O cliente associado ao pedido (ICliente do Alex).
     * @return true se o desconto for elegível, false caso contrário.
     */
    public abstract boolean isElegivel(List<SubPedido> subpedidos, ICliente cliente);
}

/**
 * Implementação de um desconto baseado em porcentagem.
 * Exemplo: Desconto de 15% no subtotal.
 */
class DescontoPercentual extends Desconto {
    private double porcentagem;

    /**
     * Construtor para DescontoPercentual.
     * @param descricao A descrição do desconto.
     * @param porcentagem A porcentagem do desconto (ex: 0.10 para 10%).
     */
    public DescontoPercentual(String descricao, double porcentagem) {
        super(descricao);
        this.porcentagem = porcentagem;
    }

    /**
     * Aplica o desconto percentual ao valor original.
     * @param valorOriginal O valor sobre o qual o desconto será aplicado.
     * @return O valor com o desconto aplicado.
     */
    @Override
    public double aplicaDesconto(double valorOriginal, List<SubPedido> subpedidos, ICliente cliente) {
        return valorOriginal * (1 - porcentagem);
    }

    /**
     * Para este exemplo, o desconto percentual é sempre elegível.
     * Em um sistema real, poderia ter regras como valor mínimo do pedido.
     * @return Sempre true para este exemplo simples.
     */
    @Override
    public boolean isElegivel(List<SubPedido> subpedidos, ICliente cliente) {
        return true; // Por simplificação, sempre elegível
    }
}

/**
 * Implementação do combo "Compre 2 pizzas e ganhe 1 refrigerante grátis".
 */
class DescontoComboCompre2PizzasGanhe1Refri extends Desconto {
    /**
     * Construtor para DescontoComboCompre2PizzasGanhe1Refri.
     */
    public DescontoComboCompre2PizzasGanhe1Refri() {
        super("Combo: Compre 2 pizzas e ganhe 1 refrigerante grátis");
    }

    /**
     * Verifica se o pedido contém pelo menos 2 pizzas e 1 refrigerante.
     * @param subpedidos A lista de subpedidos no pedido.
     * @return true se o combo for elegível, false caso contrário.
     */
    @Override
    public boolean isElegivel(List<SubPedido> subpedidos, ICliente cliente) {
        long numPizzas = subpedidos.stream()
                .filter(sp -> "Pizza".equalsIgnoreCase(sp.getItem().getTipo()))
                .mapToInt(SubPedido::getQuantidade)
                .sum();
        long numRefrigerantes = subpedidos.stream()
                .filter(sp -> "Refrigerante".equalsIgnoreCase(sp.getItem().getTipo()))
                .mapToInt(SubPedido::getQuantidade)
                .sum();

        return numPizzas >= 2 && numRefrigerantes >= 1;
    }

    /**
     * Aplica o desconto, subtraindo o preço do refrigerante mais barato.
     * @param valorOriginal O valor original do pedido.
     * @param subpedidos A lista de subpedidos no pedido.
     * @return O valor com o desconto aplicado.
     */
    @Override
    public double aplicaDesconto(double valorOriginal, List<SubPedido> subpedidos, ICliente cliente) {
        if (!isElegivel(subpedidos, cliente)) {
            return valorOriginal; // Não elegível, não aplica desconto
        }

        // Encontra o refrigerante mais barato no pedido
        double precoRefriMaisBarato = subpedidos.stream()
                .filter(sp -> "Refrigerante".equalsIgnoreCase(sp.getItem().getTipo()))
                .mapToDouble(sp -> sp.getItem().getPreco())
                .min()
                .orElse(0.0); // Se não encontrar, o desconto é 0

        return valorOriginal - precoRefriMaisBarato;
    }
}

/**
 * Implementação do combo "Compre 1 pizza, 1 refrigerante e 1 sobremesa e ganhe um desconto de 15% no subtotal".
 */
class DescontoComboPizzaRefriSobremesa15Percent extends Desconto {
    /**
     * Construtor para DescontoComboPizzaRefriSobremesa15Percent.
     */
    public DescontoComboPizzaRefriSobremesa15Percent() {
        super("Combo: 1 Pizza + 1 Refri + 1 Sobremesa = 15% OFF");
    }

    /**
     * Verifica se o pedido contém pelo menos 1 pizza, 1 refrigerante e 1 sobremesa.
     * @param subpedidos A lista de subpedidos no pedido.
     * @return true se o combo for elegível, false caso contrário.
     */
    @Override
    public boolean isElegivel(List<SubPedido> subpedidos, ICliente cliente) {
        boolean temPizza = subpedidos.stream().anyMatch(sp -> "Pizza".equalsIgnoreCase(sp.getItem().getTipo()) && sp.getQuantidade() >= 1);
        boolean temRefri = subpedidos.stream().anyMatch(sp -> "Refrigerante".equalsIgnoreCase(sp.getItem().getTipo()) && sp.getQuantidade() >= 1);
        boolean temSobremesa = subpedidos.stream().anyMatch(sp -> "Sobremesa".equalsIgnoreCase(sp.getItem().getTipo()) && sp.getQuantidade() >= 1);
        return temPizza && temRefri && temSobremesa;
    }

    /**
     * Aplica 15% de desconto ao valor original do pedido.
     * @param valorOriginal O valor original do pedido.
     * @param subpedidos A lista de subpedidos no pedido.
     * @return O valor com o desconto aplicado.
     */
    @Override
    public double aplicaDesconto(double valorOriginal, List<SubPedido> subpedidos, ICliente cliente) {
        if (!isElegivel(subpedidos, cliente)) {
            return valorOriginal; // Não elegível, não aplica desconto
        }
        return valorOriginal * (1 - 0.15); // 15% de desconto
    }
}

/**
 * Implementação do programa de fidelidade "A cada 5 pizzas compradas, o cliente ganha 1 gratuita" (Parte do Arthur).
 */
class DescontoFidelidade5PizzasGratis extends Desconto {
    /**
     * Construtor para DescontoFidelidade5PizzasGratis.
     */
    public DescontoFidelidade5PizzasGratis() {
        super("Programa Fidelidade: 1 Pizza Grátis a cada 5 pizzas");
    }

    /**
     * Verifica se o cliente já comprou um múltiplo de 5 pizzas anteriormente
     * e se há pizzas no pedido atual para aplicar o desconto.
     * @param subpedidos A lista de subpedidos no pedido.
     * @param cliente O cliente associado ao pedido (ICliente do Alex).
     * @return true se o desconto for elegível, false caso contrário.
     */
    @Override
    public boolean isElegivel(List<SubPedido> subpedidos, ICliente cliente) {
        if (cliente == null) {
            return false; // Não há cliente para verificar fidelidade
        }
        // Verifica se o cliente já comprou pizzas suficientes no histórico
        boolean clienteElegivel = cliente.getPizzasCompradas() >= 5 && (cliente.getPizzasCompradas() % 5 == 0);

        // Verifica se há pelo menos uma pizza no pedido atual para receber a gratuidade
        boolean temPizzasNoPedidoAtual = subpedidos.stream()
                .anyMatch(sp -> "Pizza".equalsIgnoreCase(sp.getItem().getTipo()));

        return clienteElegivel && temPizzasNoPedidoAtual;
    }

    /**
     * Aplica o desconto, subtraindo o preço da pizza mais barata do pedido.
     * O contador de pizzas compradas no cliente deve ser resetado/ajustado pela Loja/Alex.
     * @param valorOriginal O valor original do pedido.
     * @param subpedidos A lista de subpedidos no pedido.
     * @return O valor com o desconto aplicado.
     */
    @Override
    public double aplicaDesconto(double valorOriginal, List<SubPedido> subpedidos, ICliente cliente) {
        if (!isElegivel(subpedidos, cliente)) {
            return valorOriginal;
        }

        // Encontra a pizza mais barata no pedido para dar de graça
        double precoPizzaMaisBarata = subpedidos.stream()
                .filter(sp -> "Pizza".equalsIgnoreCase(sp.getItem().getTipo()))
                .mapToDouble(sp -> sp.getItem().getPreco())
                .min()
                .orElse(0.0); // Se não tiver pizzas, não dá desconto

        return valorOriginal - precoPizzaMaisBarata;
    }
}

// =======================================================
// PAINEL DE INTERFACE (Arthur)
// Adicionar_item.java - Agora aceita interfaces para injeção de dependência.
// =======================================================

/**
 * Painel para adicionar itens e aplicar descontos a um pedido.
 * Esta é a minha interface de usuário principal para o projeto.
 * Ela interage com as interfaces de Loja, Pedido e Cliente.
 */
public class Adicionar_item extends JPanel {

    private JComboBox<IItem> itemComboBox;
    private JTextField quantidadeField;
    private JButton adicionarItemButton;
    private JButton aplicarDescontoPercentualButton;
    private JButton aplicarCombo2PizzasRefriButton;
    private JButton aplicarComboPizzaRefriSobremesaButton;
    private JButton aplicarDescontoFidelidadeButton;
    private JTextArea pedidoDisplayArea;
    private JButton voltarButton;

    private JTextField clienteCpfField;
    private JButton carregarClienteButton;
    private JLabel clienteInfoLabel;

    // Dependências injetadas
    private ILoja loja;
    private IPedido pedidoAtual;
    private List<ICliente> clientesCadastrados; // Lista de clientes do Alex

    /**
     * Construtor para o painel Adicionar_item.
     * Recebe as interfaces das classes de Alex e Malu como dependências.
     * @param loja Instância da ILoja (da Malu).
     * @param pedidoAtual Instância do IPedido (do Alex) que está sendo construído.
     * @param clientesCadastrados Lista de ICliente (do Alex) para simular o cadastro de clientes.
     */
    public Adicionar_item(ILoja loja, IPedido pedidoAtual, List<ICliente> clientesCadastrados) {
        this.loja = loja;
        this.pedidoAtual = pedidoAtual;
        this.clientesCadastrados = clientesCadastrados;

        initComponents();
        inicializarComponentesPersonalizados();
        atualizarExibicaoPedido();
    }

    /**
     * Inicializa os componentes gerados automaticamente pelo NetBeans (mantido para compatibilidade).
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Os componentes são criados e configurados manualmente em inicializarComponentesPersonalizados()
    }

    /**
     * Inicializa e configura os componentes da interface do usuário manualmente.
     * Define o layout, adiciona campos, botões e a área de exibição.
     */
    private void inicializarComponentesPersonalizados() {
        setBackground(new Color(0, 0, 0));
        setPreferredSize(new Dimension(1134, 624));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Botão Voltar
        voltarButton = new JButton("Voltar");
        voltarButton.setFont(new Font("Inter", Font.BOLD, 14));
        voltarButton.setBackground(new Color(50, 50, 50));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        add(voltarButton, gbc);

        // --- Seção de Cliente ---
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 1;
        clienteCpfField = new JTextField("CPF do Cliente (ex: 111.111.111-11)");
        clienteCpfField.setFont(new Font("Inter", Font.PLAIN, 14));
        clienteCpfField.setForeground(Color.GRAY);
        clienteCpfField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (clienteCpfField.getText().equals("CPF do Cliente (ex: 111.111.111-11)")) {
                    clienteCpfField.setText("");
                    clienteCpfField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (clienteCpfField.getText().isEmpty()) {
                    clienteCpfField.setText("CPF do Cliente (ex: 111.111.111-11)");
                    clienteCpfField.setForeground(Color.GRAY);
                }
            }
        });
        add(clienteCpfField, gbc);

        gbc.gridy = 2;
        carregarClienteButton = new JButton("Carregar Cliente");
        carregarClienteButton.setFont(new Font("Inter", Font.BOLD, 14));
        carregarClienteButton.setBackground(new Color(100, 100, 0));
        carregarClienteButton.setForeground(Color.WHITE);
        carregarClienteButton.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 0), 2, true));
        add(carregarClienteButton, gbc);

        gbc.gridy = 3;
        clienteInfoLabel = new JLabel("Nenhum cliente selecionado.");
        clienteInfoLabel.setFont(new Font("Inter", Font.ITALIC, 12));
        clienteInfoLabel.setForeground(new Color(180, 180, 180));
        add(clienteInfoLabel, gbc);

        // --- Componentes de Adição de Item ---
        gbc.gridy = 4;
        // O ComboBox agora é populado com IItem, vindo da ILoja
        itemComboBox = new JComboBox<>(loja.getCardapio().toArray(new IItem[0]));
        itemComboBox.setFont(new Font("Inter", Font.PLAIN, 16));
        add(itemComboBox, gbc);

        gbc.gridy = 5;
        quantidadeField = new JTextField("1");
        quantidadeField.setFont(new Font("Inter", Font.PLAIN, 16));
        add(quantidadeField, gbc);

        gbc.gridy = 6;
        adicionarItemButton = new JButton("Adicionar Item");
        adicionarItemButton.setFont(new Font("Inter", Font.BOLD, 16));
        adicionarItemButton.setBackground(new Color(0, 150, 0));
        adicionarItemButton.setForeground(Color.WHITE);
        adicionarItemButton.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 0), 2, true));
        add(adicionarItemButton, gbc);

        // --- Componentes de Desconto ---
        gbc.gridy = 7;
        aplicarDescontoPercentualButton = new JButton("Aplicar Desconto (10%)");
        aplicarDescontoPercentualButton.setFont(new Font("Inter", Font.BOLD, 16));
        aplicarDescontoPercentualButton.setBackground(new Color(0, 100, 150));
        aplicarDescontoPercentualButton.setForeground(Color.WHITE);
        aplicarDescontoPercentualButton.setBorder(BorderFactory.createLineBorder(new Color(0, 70, 100), 2, true));
        add(aplicarDescontoPercentualButton, gbc);

        gbc.gridy = 8;
        aplicarCombo2PizzasRefriButton = new JButton("Aplicar Combo: 2 Pizzas + Refri Grátis");
        aplicarCombo2PizzasRefriButton.setFont(new Font("Inter", Font.BOLD, 16));
        aplicarCombo2PizzasRefriButton.setBackground(new Color(150, 0, 150));
        aplicarCombo2PizzasRefriButton.setForeground(Color.WHITE);
        aplicarCombo2PizzasRefriButton.setBorder(BorderFactory.createLineBorder(new Color(100, 0, 100), 2, true));
        add(aplicarCombo2PizzasRefriButton, gbc);

        gbc.gridy = 9;
        aplicarComboPizzaRefriSobremesaButton = new JButton("Aplicar Combo: Pizza + Refri + Sobremesa (15% OFF)");
        aplicarComboPizzaRefriSobremesaButton.setFont(new Font("Inter", Font.BOLD, 16));
        aplicarComboPizzaRefriSobremesaButton.setBackground(new Color(150, 80, 0));
        aplicarComboPizzaRefriSobremesaButton.setForeground(Color.WHITE);
        aplicarComboPizzaRefriSobremesaButton.setBorder(BorderFactory.createLineBorder(new Color(100, 50, 0), 2, true));
        add(aplicarComboPizzaRefriSobremesaButton, gbc);

        gbc.gridy = 10;
        aplicarDescontoFidelidadeButton = new JButton("Aplicar Desconto Fidelidade");
        aplicarDescontoFidelidadeButton.setFont(new Font("Inter", Font.BOLD, 16));
        aplicarDescontoFidelidadeButton.setBackground(new Color(200, 200, 0));
        aplicarDescontoFidelidadeButton.setForeground(Color.BLACK);
        aplicarDescontoFidelidadeButton.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 0), 2, true));
        add(aplicarDescontoFidelidadeButton, gbc);

        // --- Área de Exibição do Pedido ---
        pedidoDisplayArea = new JTextArea(20, 30);
        pedidoDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        pedidoDisplayArea.setEditable(false);
        pedidoDisplayArea.setBackground(new Color(30, 30, 30));
        pedidoDisplayArea.setForeground(new Color(200, 200, 200));
        JScrollPane scrollPane = new JScrollPane(pedidoDisplayArea);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 11;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);


        // --- Listeners de Eventos ---

        carregarClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarCliente();
            }
        });

        adicionarItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarItemAoPedido();
            }
        });

        aplicarDescontoPercentualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarDesconto(new DescontoPercentual("Desconto Genérico (10%)", 0.10));
            }
        });

        aplicarCombo2PizzasRefriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarDesconto(new DescontoComboCompre2PizzasGanhe1Refri());
            }
        });

        aplicarComboPizzaRefriSobremesaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarDesconto(new DescontoComboPizzaRefriSobremesa15Percent());
            }
        });

        aplicarDescontoFidelidadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarDesconto(new DescontoFidelidade5PizzasGratis());
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Adicionar_item.this, "Botão 'Voltar' clicado!", "Ação", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Lógica para carregar um cliente pelo CPF.
     * Interage com a lista de ICliente (do Alex).
     */
    private void carregarCliente() {
        String cpf = clienteCpfField.getText().trim();
        if (cpf.isEmpty() || cpf.equals("CPF do Cliente (ex: 111.111.111-11)")) {
            JOptionPane.showMessageDialog(this, "Por favor, digite um CPF para carregar o cliente.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ICliente clienteEncontrado = clientesCadastrados.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);

        if (clienteEncontrado != null) {
            pedidoAtual.setCliente(clienteEncontrado);
            clienteInfoLabel.setText("Cliente: " + clienteEncontrado.getNome() + " (Pizzas compradas: " + clienteEncontrado.getPizzasCompradas() + ")");
            clienteInfoLabel.setForeground(new Color(100, 255, 100));
            JOptionPane.showMessageDialog(this, "Cliente " + clienteEncontrado.getNome() + " carregado com sucesso!", "Cliente Carregado", JOptionPane.INFORMATION_MESSAGE);
            atualizarExibicaoPedido();
        } else {
            pedidoAtual.setCliente(null);
            clienteInfoLabel.setText("Cliente não encontrado.");
            clienteInfoLabel.setForeground(new Color(255, 100, 100));
            JOptionPane.showMessageDialog(this, "Cliente com CPF " + cpf + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            atualizarExibicaoPedido();
        }
    }

    /**
     * Lógica para adicionar um item ao pedido.
     * Cria um SubPedido e o adiciona ao Pedido atual, verificando disponibilidade com a ILoja (da Malu).
     */
    private void adicionarItemAoPedido() {
        IItem selectedItem = (IItem) itemComboBox.getSelectedItem();
        if (selectedItem == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item.", "Erro de Seleção", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeField.getText());
            if (quantidade <= 0) {
                throw new NumberFormatException("Quantidade deve ser positiva.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida. Por favor, insira um número inteiro positivo.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Interage com a ILoja (da Malu) para verificação de estoque
        if (!loja.verificarDisponibilidade(selectedItem, quantidade)) {
            JOptionPane.showMessageDialog(this, "Item '" + selectedItem.getNome() + "' não disponível em estoque na quantidade desejada.", "Sem Estoque", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SubPedido novoSubPedido = new SubPedido(selectedItem, quantidade);
        pedidoAtual.adicionarSubPedido(novoSubPedido); // Adiciona o subpedido ao IPedido (do Alex)
        atualizarExibicaoPedido();
        quantidadeField.setText("1");
    }

    /**
     * Lógica genérica para aplicar qualquer tipo de desconto.
     * Interage com o IPedido (do Alex) para adicionar o desconto.
     * @param desconto O objeto Desconto a ser aplicado.
     */
    private void aplicarDesconto(Desconto desconto) {
        if (pedidoAtual.getSubpedidos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adicione itens ao pedido antes de aplicar descontos.", "Erro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verifica elegibilidade com o cliente e subpedidos do IPedido
        if (desconto.isElegivel(pedidoAtual.getSubpedidos(), pedidoAtual.getCliente())) {
            pedidoAtual.adicionarDesconto(desconto); // Adiciona o desconto ao IPedido
            // Se for um desconto de fidelidade, um aviso para o Alex/Malu sobre a atualização do histórico.
            if (desconto instanceof DescontoFidelidade5PizzasGratis && pedidoAtual.getCliente() != null) {
                JOptionPane.showMessageDialog(this, "Desconto de fidelidade aplicado! (O histórico de pizzas compradas do cliente seria atualizado pela Loja/Alex após a finalização real do pedido)", "Fidelidade", JOptionPane.INFORMATION_MESSAGE);
            }
            atualizarExibicaoPedido();
            JOptionPane.showMessageDialog(this, "Desconto aplicado: " + desconto.getDescricao(), "Desconto Aplicado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Desconto '" + desconto.getDescricao() + "' não é elegível para este pedido.", "Desconto Não Elegível", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Atualiza a JTextArea para exibir os itens do pedido, os descontos e o total.
     */
    private void atualizarExibicaoPedido() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- PEDIDO ATUAL ---\n");
        if (pedidoAtual.getCliente() != null) {
            sb.append("Cliente: ").append(pedidoAtual.getCliente().getNome()).append(" (Pizzas: ").append(pedidoAtual.getCliente().getPizzasCompradas()).append(")\n");
        } else {
            sb.append("Cliente: Não Selecionado\n");
        }
        sb.append("--------------------\n");

        if (pedidoAtual.getSubpedidos().isEmpty()) {
            sb.append("Nenhum item adicionado ainda.\n");
        } else {
            for (SubPedido sp : pedidoAtual.getSubpedidos()) {
                sb.append(String.format("%-40s %8.2f\n", sp.toString(), sp.calculaSubtotal()));
            }
        }

        sb.append("\n--- DESCONTOS APLICADOS ---\n");
        if (pedidoAtual.getDescontosAplicados().isEmpty()) {
            sb.append("Nenhum desconto aplicado.\n");
        } else {
            for (Desconto d : pedidoAtual.getDescontosAplicados()) {
                sb.append("- ").append(d.getDescricao()).append("\n");
            }
        }

        sb.append("\n============================\n");
        sb.append(String.format("%-40s %8.2f\n", "TOTAL FINAL:", pedidoAtual.calculaTotal()));
        pedidoDisplayArea.setText(sb.toString());
    }

    /**
     * Método principal para executar o painel em um JFrame.
     * Este é o ponto de entrada da aplicação para teste.
     * Contém as implementações mock das interfaces para que o código seja executável.
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        // =======================================================
        // IMPLEMENTAÇÕES MOCK PARA DEMONSTRAÇÃO (simulando Alex e Malu)
        // Em um sistema real, estas seriam as classes concretas de Alex e Malu.
        // =======================================================

        class MockCliente implements ICliente {
            private String cpf;
            private String nome;
            private int pizzasCompradas;

            public MockCliente(String cpf, String nome, int pizzasCompradas) {
                this.cpf = cpf;
                this.nome = nome;
                this.pizzasCompradas = pizzasCompradas;
            }

            @Override public String getCpf() { return cpf; }
            @Override public String getNome() { return nome; }
            @Override public int getPizzasCompradas() { return pizzasCompradas; }
            // Mock de adição de pizzas. No sistema real, seria gerenciado pela Loja/Alex.
            public void adicionarPizzasCompradas(int quantidade) {
                this.pizzasCompradas += quantidade;
            }
            @Override public String toString() { return nome + " (CPF: " + cpf + ")"; }
        }

        class MockItem implements IItem {
            private String codigo;
            private String nome;
            private double preco;
            private String tipo;

            public MockItem(String codigo, String nome, double preco, String tipo) {
                this.codigo = codigo;
                this.nome = nome;
                this.preco = preco;
                this.tipo = tipo;
            }

            @Override public String getCodigo() { return codigo; }
            @Override public String getNome() { return nome; }
            @Override public double getPreco() { return preco; }
            @Override public String getTipo() { return tipo; }
            @Override public String toString() { return nome + " (R$" + String.format("%.2f", preco) + ") - " + tipo; }
        }

        class MockLoja implements ILoja {
            private List<IItem> cardapio;

            public MockLoja() {
                cardapio = new ArrayList<>();
                cardapio.add(new MockItem("P001", "Pizza Calabresa Grande", 45.00, "Pizza"));
                cardapio.add(new MockItem("P002", "Pizza Mussarela Média", 38.00, "Pizza"));
                cardapio.add(new MockItem("B001", "Coca-Cola 2L", 10.00, "Refrigerante"));
                cardapio.add(new MockItem("B002", "Guaraná Lata", 6.50, "Refrigerante"));
                cardapio.add(new MockItem("S001", "Brownie de Chocolate", 12.00, "Sobremesa"));
                cardapio.add(new MockItem("S002", "Pudim Individual", 8.00, "Sobremesa"));
            }

            @Override
            public List<IItem> getCardapio() {
                return cardapio;
            }

            @Override
            public boolean verificarDisponibilidade(IItem item, int quantidade) {
                // Para demonstração, assume que está sempre disponível
                return true;
            }
        }

        class MockPedido implements IPedido {
            private List<SubPedido> subpedidos;
            private List<Desconto> descontosAplicados;
            private ICliente cliente;

            public MockPedido() {
                this.subpedidos = new ArrayList<>();
                this.descontosAplicados = new ArrayList<>();
            }

            @Override public void setCliente(ICliente cliente) { this.cliente = cliente; }
            @Override public ICliente getCliente() { return cliente; }
            @Override public void adicionarSubPedido(SubPedido subPedido) { this.subpedidos.add(subPedido); }
            @Override public void adicionarDesconto(Desconto desconto) {
                boolean descontoJaAplicado = this.descontosAplicados.stream()
                                            .anyMatch(d -> d.getDescricao().equals(desconto.getDescricao()));
                if (!descontoJaAplicado) {
                    this.descontosAplicados.add(desconto);
                } else {
                    System.out.println("Desconto '" + desconto.getDescricao() + "' já foi aplicado no mock.");
                }
            }
            @Override public double calculaTotal() {
                double total = 0;
                for (SubPedido sp : subpedidos) {
                    total += sp.calculaSubtotal();
                }
                for (Desconto d : descontosAplicados) {
                    if (d.isElegivel(subpedidos, cliente)) {
                        total = d.aplicaDesconto(total, subpedidos, cliente);
                    }
                }
                return total;
            }
            @Override public List<SubPedido> getSubpedidos() { return subpedidos; }
            @Override public List<Desconto> getDescontosAplicados() { return descontosAplicados; }
        }

        // Configuração para execução da UI
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Instâncias das classes mock
                ILoja mockLoja = new MockLoja();
                IPedido mockPedido = new MockPedido();
                List<ICliente> mockClientesCadastrados = new ArrayList<>();
                mockClientesCadastrados.add(new MockCliente("111.111.111-11", "João Silva", 5)); // Cliente com 5 pizzas
                mockClientesCadastrados.add(new MockCliente("222.222.222-22", "Maria Souza", 3)); // Cliente com 3 pizzas

                // Passa as instâncias mock para o painel de Arthur
                JFrame frame = new JFrame("Sistema de Pedidos de Pizza - Arthur");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(new Adicionar_item(mockLoja, mockPedido, mockClientesCadastrados));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
