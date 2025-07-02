/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author malu
 */
public class DescontoFixo extends Descontos {
    /**
     * O valor fixo do desconto a ser aplicado.
     * Este valor será subtraído do valor original durante o cálculo.
     */
    private double valorDesconto;

    /**
     * Constrói um novo DescontoFixo com descrição e valor específicos.
     * 
     * @param descricao A descrição do desconto (ex: "Black Friday", "Cupom R$10")
     * @param valorDesconto O valor absoluto do desconto (deve ser positivo)
     */
    public DescontoFixo(String descricao, double valorDesconto) {
        super(descricao);
        this.valorDesconto = valorDesconto;
    }

    /**
     * Aplica o desconto fixo ao valor original, garantindo que o resultado
     * nunca seja negativo.
     * 
     * @param valorOriginal O valor original antes do desconto
     * @return O valor com desconto aplicado (nunca menor que zero)
     */
    @Override
    public double aplicarDesconto(double valorOriginal) {
        return Math.max(0, valorOriginal - valorDesconto);
    }

    /**
     * Retorna o valor fixo configurado para este desconto.
     * 
     * @return O valor do desconto fixo
     */
    public double getValorDesconto() {
        return valorDesconto;
    }

    /**
     * Define um novo valor para o desconto fixo.
     * 
     * @param valorDesconto O novo valor do desconto (deve ser positivo)
     */
    public void setValorDesconto(double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }
}