package classes;

/**
 *
 * @author malu
 */
public class DescontoPorcentagem extends Descontos {
    private double percentual; // Ex: 0.15 para 15%

    public DescontoPorcentagem(String descricao, double percentual) {
        super(descricao);
        this.percentual = percentual;
    }

    @Override
    public double aplicarDesconto(double valorOriginal) {
        return valorOriginal * (1 - percentual);
    }
}
