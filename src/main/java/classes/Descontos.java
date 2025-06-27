package classes;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe abstrata Desconto (Parte do Arthur).
 * Classe base para diferentes tipos de descontos.
 * As implementações específicas de desconto são classes aninhadas estáticas.
 */
public abstract class Descontos {
    protected String descricao;

    /**
     * Construtor para a classe abstrata Desconto.
     * @param descricao A descrição do desconto.
     */
    public Descontos(String descricao) {
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
    public abstract double aplicaDesconto(double valorOriginal, List<SubPedido> subpedidos, Cliente cliente);

    /**
     * Método abstrato para verificar se o desconto é elegível.
     * @param subpedidos A lista de subpedidos no pedido.
     * @param cliente O cliente associado ao pedido (ICliente do Alex).
     * @return true se o desconto for elegível, false caso contrário.
     */
    public abstract boolean isElegivel(List<SubPedido> subpedidos, Cliente cliente);

    /**
     * Implementação de um desconto baseado em porcentagem.
     * Exemplo: Desconto de 15% no subtotal.
     */
    public static class DescontoPercentual extends Descontos {
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
        public double aplicaDesconto(double valorOriginal, List<SubPedido> subpedidos, Cliente cliente) {
            return valorOriginal * (1 - porcentagem);
        }

        /**
         * Para este exemplo, o desconto percentual é sempre elegível.
         * Em um sistema real, poderia ter regras como valor mínimo do pedido.
         * @return Sempre true para este exemplo simples.
         */
        @Override
        public boolean isElegivel(List<SubPedido> subpedidos, Cliente cliente) {
            return true; // Por simplificação, sempre elegível
        }
    }

    /**
     * Implementação do combo "Compre 2 pizzas e ganhe 1 refrigerante grátis".
     */
    public static class DescontoComboCompre2PizzasGanhe1Refri extends Descontos {
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
        public boolean isElegivel(List<SubPedido> subpedidos, Cliente cliente) {
            long numPizzas = subpedidos.stream()
                    .filter(sp -> "Pizza".equalsIgnoreCase(sp.getItem().getNome()))
                    .mapToInt(SubPedido::getQuantidade)
                    .sum();
            long numRefrigerantes = subpedidos.stream()
                    .filter(sp -> "Refrigerante".equalsIgnoreCase(sp.getItem().getNome()))
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
        public double aplicaDesconto(double valorOriginal, List<SubPedido> subpedidos, Cliente cliente) {
            if (!isElegivel(subpedidos, cliente)) {
                return valorOriginal; // Não elegível, não aplica desconto
            }

            // Encontra o refrigerante mais barato no pedido
            double precoRefriMaisBarato = subpedidos.stream()
                    .filter(sp -> "Refrigerante".equalsIgnoreCase(sp.getItem().getNome()))
                    .mapToDouble(sp -> sp.getItem().getPreco())
                    .min()
                    .orElse(0.0); // Se não encontrar, o desconto é 0

            return valorOriginal - precoRefriMaisBarato;
        }
    }

    /**
     * Implementação do combo "Compre 1 pizza, 1 refrigerante e 1 sobremesa e ganhe um desconto de 15% no subtotal".
     */
    public static class DescontoComboPizzaRefriSobremesa15Percent extends Descontos {
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
        public boolean isElegivel(List<SubPedido> subpedidos, Cliente cliente) {
            boolean temPizza = subpedidos.stream().anyMatch(sp -> "Pizza".equalsIgnoreCase(sp.getItem().getNome()) && sp.getQuantidade() >= 1);
            boolean temRefri = subpedidos.stream().anyMatch(sp -> "Refrigerante".equalsIgnoreCase(sp.getItem().getNome()) && sp.getQuantidade() >= 1);
            boolean temSobremesa = subpedidos.stream().anyMatch(sp -> "Sobremesa".equalsIgnoreCase(sp.getItem().getNome()) && sp.getQuantidade() >= 1);
            return temPizza && temRefri && temSobremesa;
        }

        /**
         * Aplica 15% de desconto ao valor original do pedido.
         * @param valorOriginal O valor original do pedido.
         * @param subpedidos A lista de subpedidos no pedido.
         * @return O valor com o desconto aplicado.
         */
        @Override
        public double aplicaDesconto(double valorOriginal, List<SubPedido> subpedidos, Cliente cliente) {
            if (!isElegivel(subpedidos, cliente)) {
                return valorOriginal; // Não elegível, não aplica desconto
            }
            return valorOriginal * (1 - 0.15); // 15% de desconto
        }
    }

    /**
     * Implementação do programa de fidelidade "A cada 5 pizzas compradas, o cliente ganha 1 gratuita".
     */
    public static class DescontoFidelidade5PizzasGratis extends Descontos {
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
        public boolean isElegivel(List<SubPedido> subpedidos, Cliente cliente) {
            if (cliente == null) {
                return false; // Não há cliente para verificar fidelidade
            }
            // Verifica se o cliente já comprou pizzas suficientes no histórico
            boolean clienteElegivel = cliente.getPizzasCompradas() >= 5 && (cliente.getPizzasCompradas() % 5 == 0);

            // Verifica se há pelo menos uma pizza no pedido atual para receber a gratuidade
            boolean temPizzasNoPedidoAtual = subpedidos.stream()
                    .anyMatch(sp -> "Pizza".equalsIgnoreCase(sp.getItem().getNome()));

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
        public double aplicaDesconto(double valorOriginal, List<SubPedido> subpedidos, Cliente cliente) {
            if (!isElegivel(subpedidos, cliente)) {
                return valorOriginal;
            }

            // Encontra a pizza mais barata no pedido para dar de graça
            double precoPizzaMaisBarata = subpedidos.stream()
                    .filter(sp -> "Pizza".equalsIgnoreCase(sp.getItem().getNome()))
                    .mapToDouble(sp -> sp.getItem().getPreco())
                    .min()
                    .orElse(0.0); // Se não tiver pizzas, não dá desconto

            return valorOriginal - precoPizzaMaisBarata;
        }
    }
}
