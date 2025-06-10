import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Painel para adicionar itens e aplicar descontos a um pedido.
 * Esta é a minha (Arthur) interface de usuário principal para o projeto.
 * Ela interage com as interfaces de Loja, Pedido e Cliente, usando GroupLayout.
 */
public class Adicionar_item extends JPanel {

    private JButton voltarButton;
    private JTextField clienteCpfField;
    private JButton carregarClienteButton;
    private JLabel clienteInfoLabel;
    private JComboBox<IItem> itemComboBox;
    private JTextField quantidadeField;
    private JButton adicionarItemButton;
    private JButton aplicarDescontoPercentualButton;
    private JButton aplicarCombo2PizzasRefriButton;
    private JButton aplicarComboPizzaRefriSobremesaButton;
    private JButton aplicarDescontoFidelidadeButton;
    private JTextArea pedidoDisplayArea;
    private JScrollPane scrollPane; // O scrollPane agora é um componente

    // Dependências injetadas (Interfaces de Alex e Malu)
    private ILoja loja;
    private IPedido pedidoAtual;
    private List<ICliente> clientesCadastrados;

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
        // inicializarComponentesPersonalizados() agora é chamado por initComponents()
        atualizarExibicaoPedido();
    }

    /**
     * Inicializa os componentes e configura o GroupLayout.
     * Este método agora reflete o estilo de código gerado pelo NetBeans.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        // Inicialização de Componentes
        voltarButton = new JButton();
        clienteCpfField = new JTextField();
        carregarClienteButton = new JButton();
        clienteInfoLabel = new JLabel();
        itemComboBox = new JComboBox<>();
        quantidadeField = new JTextField();
        adicionarItemButton = new JButton();
        aplicarDescontoPercentualButton = new JButton();
        aplicarCombo2PizzasRefriButton = new JButton();
        aplicarComboPizzaRefriSobremesaButton = new JButton();
        aplicarDescontoFidelidadeButton = new JButton();
        pedidoDisplayArea = new JTextArea();
        scrollPane = new JScrollPane();

        // Configuração de Propriedades dos Componentes
        setBackground(new Color(0, 0, 0));
        setPreferredSize(new Dimension(1134, 624));

        voltarButton.setFont(new Font("Inter", Font.BOLD, 14));
        voltarButton.setBackground(new Color(50, 50, 50));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        voltarButton.setText("Voltar");

        clienteCpfField.setFont(new Font("Inter", Font.PLAIN, 14));
        clienteCpfField.setForeground(Color.GRAY);
        clienteCpfField.setText("CPF do Cliente (ex: 111.111.111-11)");
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

        carregarClienteButton.setFont(new Font("Inter", Font.BOLD, 14));
        carregarClienteButton.setBackground(new Color(100, 100, 0));
        carregarClienteButton.setForeground(Color.WHITE);
        carregarClienteButton.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 0), 2, true));
        carregarClienteButton.setText("Carregar Cliente");

        clienteInfoLabel.setFont(new Font("Inter", Font.ITALIC, 12));
        clienteInfoLabel.setForeground(new Color(180, 180, 180));
        clienteInfoLabel.setText("Nenhum cliente selecionado.");

        itemComboBox.setFont(new Font("Inter", Font.PLAIN, 16));
        // Popula o ComboBox com IItem da ILoja
        itemComboBox.setModel(new DefaultComboBoxModel<>(loja.getCardapio().toArray(new IItem[0])));

        quantidadeField.setFont(new Font("Inter", Font.PLAIN, 16));
        quantidadeField.setText("1");

        adicionarItemButton.setFont(new Font("Inter", Font.BOLD, 16));
        adicionarItemButton.setBackground(new Color(0, 150, 0));
        adicionarItemButton.setForeground(Color.WHITE);
        adicionarItemButton.setBorder(BorderFactory.createLineBorder(new Color(0, 100, 0), 2, true));
        adicionarItemButton.setText("Adicionar Item");

        aplicarDescontoPercentualButton.setFont(new Font("Inter", Font.BOLD, 16));
        aplicarDescontoPercentualButton.setBackground(new Color(0, 100, 150));
        aplicarDescontoPercentualButton.setForeground(Color.WHITE);
        aplicarDescontoPercentualButton.setBorder(BorderFactory.createLineBorder(new Color(0, 70, 100), 2, true));
        aplicarDescontoPercentualButton.setText("Aplicar Desconto (10%)");

        aplicarCombo2PizzasRefriButton.setFont(new Font("Inter", Font.BOLD, 16));
        aplicarCombo2PizzasRefriButton.setBackground(new Color(150, 0, 150));
        aplicarCombo2PizzasRefriButton.setForeground(Color.WHITE);
        aplicarCombo2PizzasRefriButton.setBorder(BorderFactory.createLineBorder(new Color(100, 0, 100), 2, true));
        aplicarCombo2PizzasRefriButton.setText("Aplicar Combo: 2 Pizzas + Refri Grátis");

        aplicarComboPizzaRefriSobremesaButton.setFont(new Font("Inter", Font.BOLD, 16));
        aplicarComboPizzaRefriSobremesaButton.setBackground(new Color(150, 80, 0));
        aplicarComboPizzaRefriSobremesaButton.setForeground(Color.WHITE);
        aplicarComboPizzaRefriSobremesaButton.setBorder(BorderFactory.createLineBorder(new Color(100, 50, 0), 2, true));
        aplicarComboPizzaRefriSobremesaButton.setText("Aplicar Combo: Pizza + Refri + Sobremesa (15% OFF)");

        aplicarDescontoFidelidadeButton.setFont(new Font("Inter", Font.BOLD, 16));
        aplicarDescontoFidelidadeButton.setBackground(new Color(200, 200, 0));
        aplicarDescontoFidelidadeButton.setForeground(Color.BLACK);
        aplicarDescontoFidelidadeButton.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 0), 2, true));
        aplicarDescontoFidelidadeButton.setText("Aplicar Desconto Fidelidade");

        pedidoDisplayArea.setColumns(20);
        pedidoDisplayArea.setRows(5);
        pedidoDisplayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        pedidoDisplayArea.setEditable(false);
        pedidoDisplayArea.setBackground(new Color(30, 30, 30));
        pedidoDisplayArea.setForeground(new Color(200, 200, 200));
        scrollPane.setViewportView(pedidoDisplayArea); // Adiciona o JTextArea ao JScrollPane


        // --- Configuração do GroupLayout ---
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(voltarButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(clienteCpfField, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(carregarClienteButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(clienteInfoLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(itemComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(quantidadeField)
                            .addComponent(adicionarItemButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(aplicarDescontoPercentualButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(aplicarCombo2PizzasRefriButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(aplicarComboPizzaRefriSobremesaButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(aplicarDescontoFidelidadeButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(voltarButton)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(clienteCpfField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(carregarClienteButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clienteInfoLabel)
                        .addGap(18, 18, 18)
                        .addComponent(itemComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantidadeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(adicionarItemButton)
                        .addGap(18, 18, 18)
                        .addComponent(aplicarDescontoPercentualButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aplicarCombo2PizzasRefriButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aplicarComboPizzaRefriSobremesaButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aplicarDescontoFidelidadeButton)
                        .addGap(0, 181, Short.MAX_VALUE))
                    .addComponent(scrollPane))
                .addContainerGap())
        );


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

        // Atualizado para usar Desconto.DescontoPercentual
        aplicarDescontoPercentualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarDesconto(new Desconto.DescontoPercentual("Desconto Genérico (10%)", 0.10));
            }
        });

        // Atualizado para usar Desconto.DescontoComboCompre2PizzasGanhe1Refri
        aplicarCombo2PizzasRefriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarDesconto(new Desconto.DescontoComboCompre2PizzasGanhe1Refri());
            }
        });

        // Atualizado para usar Desconto.DescontoComboPizzaRefriSobremesa15Percent
        aplicarComboPizzaRefriSobremesaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarDesconto(new Desconto.DescontoComboPizzaRefriSobremesa15Percent());
            }
        });

        // Atualizado para usar Desconto.DescontoFidelidade5PizzasGratis
        aplicarDescontoFidelidadeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aplicarDesconto(new Desconto.DescontoFidelidade5PizzasGratis());
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
            if (desconto instanceof Desconto.DescontoFidelidade5PizzasGratis && pedidoAtual.getCliente() != null) {
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
}
