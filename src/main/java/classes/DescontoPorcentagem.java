package classes;

/**
 *
 * @author malu
 */
public class DescontoPorcentagem extends Descontos {
    /**
     * O percentual de desconto a ser aplicado, representado como valor decimal.
     * Exemplo: 0.15 representa 15% de desconto
     */
    private double percentual;

    /**
     * Constrói um novo DescontoPorcentagem com descrição e percentual específicos.
     * 
     * @param descricao A descrição do desconto (ex: "Liquidação", "Cupom 10%")
     * @param percentual O percentual de desconto em formato decimal (0 a 1)
     * @throws IllegalArgumentException Se o percentual for menor que 0 ou maior que 1
     */
    public DescontoPorcentagem(String descricao, double percentual) {
        super(descricao);
        if (percentual < 0 || percentual > 1) {
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 1");
        }
        this.percentual = percentual;
    }

    /**
     * Aplica o desconto percentual ao valor original.
     * 
     * @param valorOriginal O valor original antes do desconto
     * @return O valor com desconto aplicado
     * @throws IllegalArgumentException Se o valorOriginal for negativo
     */
    @Override
    public double aplicarDesconto(double valorOriginal) {
        if (valorOriginal < 0) {
            throw new IllegalArgumentException("Valor original não pode ser negativo");
        }
        return valorOriginal * (1 - percentual);
    }

    /**
     * Retorna o percentual de desconto configurado.
     * 
     * @return O percentual de desconto (em formato decimal 0-1)
     */
    public double getPercentual() {
        return percentual;
    }

    /**
     * Define um novo percentual de desconto.
     * 
     * @param percentual O novo percentual de desconto (0 a 1)
     * @throws IllegalArgumentException Se o percentual for menor que 0 ou maior que 1
     */
    public void setPercentual(double percentual) {
        if (percentual < 0 || percentual > 1) {
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 1");
        }
        this.percentual = percentual;
    }

    /**
     * Retorna o percentual de desconto formatado como porcentagem.
     * 
     * @return String formatada (ex: "15%" para 0.15)
     */
    public String getPercentualFormatado() {
        return String.format("%.0f%%", percentual * 100);
    }
}