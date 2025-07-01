package classes;

/**
 * Classe abstrata Descontos.
 * Define o comportamento genérico para aplicar descontos.
 */
public abstract class Descontos {
    protected String descricao;

    public Descontos(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    // Método polimórfico
    public abstract double aplicarDesconto(double valorOriginal);
}
