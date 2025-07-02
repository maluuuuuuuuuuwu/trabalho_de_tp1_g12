package classes;

/**
 * Classe abstrata Descontos.
 * Define o comportamento genérico para aplicar descontos.
 */
/**
 *
 * @author malu
 */
public abstract class Descontos {
    /**
     * Descrição textual do desconto, para exibição ao usuário
     */
    protected String descricao;

    /**
     * Construtor base para inicialização de descontos
     * 
     * @param descricao Descrição amigável do desconto (não pode ser nula ou vazia)
     * @throws IllegalArgumentException Se a descrição for nula ou vazia
     */
    public Descontos(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia");
        }
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição do desconto
     * 
     * @return String com a descrição configurada
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Método polimórfico principal que aplica o desconto conforme a estratégia específica.
     * Deve ser implementado por cada tipo concreto de desconto.
     * 
     * @param valorOriginal Valor base para cálculo do desconto (deve ser positivo)
     * @return Valor com desconto aplicado (nunca negativo)
     * @throws IllegalArgumentException Se valorOriginal for negativo
     */
    public abstract double aplicarDesconto(double valorOriginal);

    /**
     * Método para atualizar a descrição do desconto
     * 
     * @param descricao Nova descrição (não pode ser nula ou vazia)
     * @throws IllegalArgumentException Se a descrição for nula ou vazia
     */
    public void setDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia");
        }
        this.descricao = descricao;
    }

    /**
     * Retorna uma representação textual padrão do desconto
     * 
     * @return String no formato "Desconto: [descrição]"
     */
    @Override
    public String toString() {
        return "Desconto: " + descricao;
    }
}