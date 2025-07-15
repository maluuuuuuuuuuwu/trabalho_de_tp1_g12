/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telas;

import classes.Cliente;
import classes.Item;
import classes.Loja;
import classes.Pedido;
import classes.SubPedido;
import classes.Descontos;
import java.awt.GridLayout; 
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JLabel; 
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author alexb
 */
public class Pedidos extends javax.swing.JFrame {
    private Loja loja;
    private Cliente cliente;
    private Map<Item, JComboBox<String>> MapeamentoDeItens;
    private JComboBox<Descontos> comboDescontos;
    private JComboBox<String> comboCombos;


    /**
     * Creates new form Pedidos
     */
    public Pedidos(Loja loja, Cliente cliente) {
        this.loja = loja;
        this.cliente = cliente;
        this.MapeamentoDeItens = new LinkedHashMap<>(); // Usamos LinkedHashMap para manter a ordem
        
        initComponents(); // Este método é gerado pelo NetBeans e DEVE ser a primeira chamada aqui
        initCustomComponents(); // Inicializa nossos componentes personalizados
        this.setLocationRelativeTo(null); // Centraliza a tela
    }

    private void initCustomComponents() {
        // Cria um painel com abas para separar itens individuais e combos
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Painel para itens individuais
        JPanel panelItens = new JPanel();
        panelItens.setLayout(new GridLayout(0, 4, 10, 10));
        gerarCardapioDinamico(panelItens);
        
        // Painel para combos (opcional)
        JPanel panelCombos = new JPanel();
        panelCombos.setLayout(new GridLayout(0, 1, 10, 10));
        gerarCombosDinamicos(panelCombos);
        
        // Adiciona os painéis às abas
        tabbedPane.addTab("Itens Individuais", new JScrollPane(panelItens));
        tabbedPane.addTab("Combos", new JScrollPane(panelCombos));
        
        // Adiciona o painel de abas ao frame
        jTabbedPane2.add(tabbedPane, java.awt.BorderLayout.CENTER);
        
        // Configura o combo de descontos
        comboDescontos = new JComboBox<>();
        for (Descontos desconto : loja.getLista_descontos()) {
            comboDescontos.addItem(desconto);
        }
        comboDescontos.insertItemAt(null, 0); // Opção "Nenhum desconto"
        comboDescontos.setSelectedIndex(0);
        
        // Adiciona o combo de descontos ao painel inferior
        JPanel panelDescontos = new JPanel();
        panelDescontos.add(new JLabel("Aplicar Desconto:"));
        panelDescontos.add(comboDescontos);
        painelInferior.add(panelDescontos, java.awt.BorderLayout.NORTH);
    }

    private void adicionarBotaoFinalizarPedido() {
    // Exemplo: Supondo que você tenha um JButton 'botaoFinalizar'
    //JButton botaoFinalizar = new JButton("Finalizar Pedido");
    // Adicione o botão ao seu painel, por exemplo, painelResumo
    // painelResumo.add(botaoFinalizar); // Ou onde for apropriado na sua GUI

    botaoFinalizar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            finalizarPedidoAction(evt);
        }
    });
}

    private void finalizarPedidoAction(java.awt.event.ActionEvent evt) {
        try {
            // 1. Coleta os SubPedidos do mapeamento de itens
            List<SubPedido> subPedidos = new ArrayList<>();
            int quantidadeTotalItens = 0;
            for (Map.Entry<Item, JComboBox<String>> entry : MapeamentoDeItens.entrySet()) {
                Item item = entry.getKey();
                int quantidade = Integer.parseInt((String) entry.getValue().getSelectedItem());

                if (quantidade > 0) {
                    subPedidos.add(new SubPedido(item, quantidade));
                    quantidadeTotalItens += quantidade;
                }
            }

            if (subPedidos.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, adicione itens ao pedido.", "Pedido Vazio", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 2. Cria o objeto Pedido
            // O Pedido.java já espera o GerenciadorDescontos da Loja
            Pedido pedido = new Pedido(subPedidos, loja.getDescontos(), loja, cliente);

            // 3. Calcula o Valor Final (já com desconto e frete aplicados internamente no Pedido)
            double valorFinal = pedido.getValorTotal(); // Este método já considera frete e descontos
            double freteCalculado = pedido.getFrete(); // Para exibir separadamente o frete

            // Calcular o valor original antes de qualquer desconto, para exibir o desconto aplicado.
            // O Pedido.java tem um getValorBase() que retorna o valor dos itens sem frete ou desconto
            double valorBaseItens = pedido.getValorBase();
            double valorOriginalComFrete = valorBaseItens + freteCalculado;
            double descontoAplicado = valorOriginalComFrete - valorFinal; // Valor do desconto

            // 4. Exibe o Resultado na sua interface
            // Assumindo que você tem JLabels como 'labelTotal', 'labelFrete', 'labelDescontoAplicado'
            labelTotal.setText(String.format("Total: R$ %.2f", valorFinal));
            labelFrete.setText(String.format("Frete: R$ %.2f", freteCalculado));
            labelDescontoAplicado.setText(String.format("Desconto: R$ %.2f", descontoAplicado));

            // Mensagem de sucesso (opcional, pode ser uma tela de confirmação)
            JOptionPane.showMessageDialog(this,
                String.format("Pedido finalizado com sucesso!\nValor Total: R$ %.2f\nFrete: R$ %.2f\nDesconto Aplicado: R$ %.2f", valorFinal, freteCalculado, descontoAplicado),
                "Pedido Concluído", JOptionPane.INFORMATION_MESSAGE);

            // 5. Adiciona o pedido ao gerenciador da loja (importante para registro e persistência)
            loja.addPedido(pedido);

            // Opcional: Se desejar fechar a tela de pedidos após a finalização
            // this.dispose();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro no Pedido", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao finalizar o pedido: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para depuração
        }
    }

    // Certifique-se de que os métodos de acesso (getters) existam na sua classe Loja:
    // Por exemplo, em Loja.java, você precisará:
    // public GerenciadorPedidos getListaPedidos() { return lista_pedidos; }
    // public GerenciadorDescontos getListaDescontos() { return lista_descontos; }
    
    private void gerarCardapioDinamico(JPanel painel) {
        // Pega a lista de produtos do cardápio da loja
        List<Item> cardapio = loja.getItens_oferecidos();
        
        // Limpa o painel para o caso de haver algo nele
        painelCardapio.removeAll();
        
        
        for (Item item : cardapio) {
            // Cria os componentes para cada item
            JLabel nomeItemLabel = new JLabel(item.getNome());
            JComboBox<String> quantidadeComboBox = new JComboBox<>();
            
            // Adiciona as opções de quantidade (0 a 10)
            for (int i = 0; i <= 10; i++) {
                quantidadeComboBox.addItem(String.valueOf(i));
            }
            
            // Adiciona os componentes ao painel
            painel.add(nomeItemLabel);
            painel.add(quantidadeComboBox);
            
            // Guarda a referência do item e seu ComboBox no nosso mapa
            this.MapeamentoDeItens.put(item, quantidadeComboBox);
        }
        
        // Revalida e redesenha o painel para que as mudanças apareçam
        painel.revalidate();
        painel.repaint();
    }
    
     private void gerarCombosDinamicos(JPanel painel) {
        // Aqui você pode adicionar combos pré-definidos
        // Exemplo básico - na prática, você teria uma lista de combos na classe Loja
        painel.add(new JLabel("Combos disponíveis:"));
        
        comboCombos = new JComboBox<>();
        comboCombos.addItem("Nenhum combo selecionado");
        comboCombos.addItem("Combo Família - 2 pizzas grandes + 2 refrigerantes");
        comboCombos.addItem("Combo Casal - 1 pizza média + 1 refrigerante");
        comboCombos.addItem("Combo Individual - 1 pizza pequena + 1 suco");
        
        painel.add(comboCombos);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button1 = new java.awt.Button();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        painelPrincipal = new javax.swing.JPanel();
        painelInferior = new javax.swing.JPanel();
        painelCardapio = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        labelTotal = new javax.swing.JLabel();
        labelFrete = new javax.swing.JLabel();
        labelDescontoAplicado = new javax.swing.JLabel();
        botaoFinalizar = new javax.swing.JButton();

        button1.setLabel("button1");

        javax.swing.GroupLayout painelInferiorLayout = new javax.swing.GroupLayout(painelInferior);
        painelInferior.setLayout(painelInferiorLayout);
        painelInferiorLayout.setHorizontalGroup(
            painelInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        painelInferiorLayout.setVerticalGroup(
            painelInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout painelPrincipalLayout = new javax.swing.GroupLayout(painelPrincipal);
        painelPrincipal.setLayout(painelPrincipalLayout);
        painelPrincipalLayout.setHorizontalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPrincipalLayout.createSequentialGroup()
                .addContainerGap(298, Short.MAX_VALUE)
                .addComponent(painelInferior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        painelPrincipalLayout.setVerticalGroup(
            painelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPrincipalLayout.createSequentialGroup()
                .addComponent(painelInferior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 179, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        painelCardapio.setLayout(new java.awt.GridLayout(1, 0));

        botaoFinalizar.setText("Finalizar Pedido");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(painelCardapio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(labelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelFrete, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelDescontoAplicado, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 223, Short.MAX_VALUE)
                .addComponent(botaoFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(141, Short.MAX_VALUE)
                .addComponent(painelCardapio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelDescontoAplicado, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelFrete))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(botaoFinalizar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Este método main é apenas para teste - na prática, a tela é chamada passando loja e cliente
                // new Pedidos(null, null).setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botaoFinalizar;
    private java.awt.Button button1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel labelDescontoAplicado;
    private javax.swing.JLabel labelFrete;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JPanel painelCardapio;
    private javax.swing.JPanel painelInferior;
    private javax.swing.JPanel painelPrincipal;
    // End of variables declaration//GEN-END:variables
 }
