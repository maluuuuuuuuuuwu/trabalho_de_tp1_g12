/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author malu
 */
/**
 * Representa um desconto progressivo baseado na quantidade de itens adquiridos.
 * Aplica um percentual de desconto quando a quantidade mínima é atingida.
 * 
 * Exemplo de uso:
 * DescontoQuantidade desconto = new DescontoQuantidade("Leve 5, pague 10% menos", 5, 0.10);
 * double valorFinal = desconto.aplicarDesconto(100.0, 5); // Retorna 90.0 (10% de desconto)
 * double valorSemDesconto = desconto.aplicarDesconto(100.0, 4); // Retorna 100.0 (sem desconto)
 */
public class DescontoQuantidade extends Descontos {
    /**
     * Quantidade mínima de itens necessária para aplicar o desconto
     */
    private int quantidadeMinima;
    
    /**
     * Percentual de desconto a ser aplicado (em decimal: 0.10 = 10%)
     */
    private double percentual;

    /**
     * Constrói um desconto por quantidade com parâmetros específicos
     * 
     * @param descricao Descrição da promoção (ex: "Leve 3, pague 10% menos")
     * @param quantidadeMinima Quantidade mínima para ativar o desconto (deve ser > 0)
     * @param percentual Percentual de desconto (0 a 1, ex: 0.10 para 10%)
     * @throws IllegalArgumentException Se quantidadeMinima ≤ 0 ou percentual fora do intervalo 0-1
     */
    public DescontoQuantidade(String descricao, int quantidadeMinima, double percentual) {
        super(descricao);
        if (quantidadeMinima <= 0) {
            throw new IllegalArgumentException("Quantidade mínima deve ser maior que zero");
        }
        if (percentual < 0 || percentual > 1) {
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 1");
        }
        this.quantidadeMinima = quantidadeMinima;
        this.percentual = percentual;
    }

    /**
     * Aplica o desconto baseado na quantidade de itens
     * 
     * @param valorOriginal Valor total da compra
     * @param quantidade Quantidade de itens no carrinho
     * @return Valor com desconto aplicado (se condições forem atendidas)
     * @throws IllegalArgumentException Se valorOriginal ou quantidade forem negativos
     */
    public double aplicarDesconto(double valorOriginal, int quantidade) {
        if (valorOriginal < 0) {
            throw new IllegalArgumentException("Valor original não pode ser negativo");
        }
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        
        if (quantidade >= quantidadeMinima) {
            return valorOriginal * (1 - percentual);
        }
        return valorOriginal;
    }

    /**
     * Implementação de fallback para compatibilidade com a classe base
     * (Não recomendado usar diretamente - utilize o método com parâmetro de quantidade)
     * 
     * @param valorOriginal Valor original da compra
     * @return O mesmo valor sem desconto (comportamento neutro)
     */
    @Override
    public double aplicarDesconto(double valorOriginal) {
        return valorOriginal;
    }

    // Getters e Setters com validação
    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        if (quantidadeMinima <= 0) {
            throw new IllegalArgumentException("Quantidade mínima deve ser maior que zero");
        }
        this.quantidadeMinima = quantidadeMinima;
    }

    public double getPercentual() {
        return percentual;
    }

    public void setPercentual(double percentual) {
        if (percentual < 0 || percentual > 1) {
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 1");
        }
        this.percentual = percentual;
    }

    /**
     * Retorna uma representação formatada da regra de desconto
     * @return String no formato "Leve X, pague Y% menos"
     */
    @Override
    public String toString() {
        return String.format("Leve %d, pague %.0f%% menos", 
                           quantidadeMinima, percentual * 100);
    }
}