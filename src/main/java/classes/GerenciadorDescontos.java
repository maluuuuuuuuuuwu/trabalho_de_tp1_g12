package classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia todos os descontos e promoções do sistema.
 * Responsável por aplicar e gerenciar diferentes tipos de descontos.
 */
/**
 *
 * @author malu
 */
public class GerenciadorDescontos {
    private List<Descontos> descontos = new ArrayList<>();

    /**
     * Adiciona um novo desconto ao sistema.
     * @param desconto Desconto a ser adicionado (não pode ser nulo)
     * @throws IllegalArgumentException Se o desconto for nulo
     */
    public void adicionarDesconto(Descontos desconto) {
        if (desconto == null) {
            throw new IllegalArgumentException("Desconto não pode ser nulo");
        }
        descontos.add(desconto);
    }

    /**
     * Aplica o melhor desconto disponível para um valor e quantidade.
     * @param valorOriginal Valor original do pedido
     * @param quantidadeItens Quantidade de itens no pedido
     * @return Valor com desconto aplicado, ou 0 se nenhum desconto foi aplicado
     */
    public double aplicarMelhorDesconto(double valorOriginal, int quantidadeItens) {
        double melhorValor = valorOriginal;

        for (Descontos d : descontos) {
            double valorComDesconto;

            if (d instanceof DescontoQuantidade) {
                valorComDesconto = ((DescontoQuantidade) d).aplicarDesconto(valorOriginal, quantidadeItens);
            } else {
                valorComDesconto = d.aplicarDesconto(valorOriginal);
            }

            if (valorComDesconto < melhorValor) {
                melhorValor = valorComDesconto;
            }
        }

        // Se nenhum desconto foi aplicado, retornar 0
        return (melhorValor == valorOriginal) ? 0.0 : melhorValor;
    }
    /**
     * Obtém uma cópia segura de todos os descontos.
     * @return Lista imutável de descontos
     */
    public List<Descontos> getDescontosDisponiveis() {
        return new ArrayList<>(descontos);
    }

    /**
     * Remove um desconto do sistema.
     * @param desconto Desconto a ser removido
     * @return true se o desconto foi removido com sucesso
     */
    public boolean removerDesconto(Descontos desconto) {
        return desconto != null && descontos.remove(desconto);
    }

    /**
     * Busca descontos por tipo específico.
     * @param tipo Classe do tipo de desconto (ex: DescontoFixo.class)
     * @return Lista de descontos do tipo especificado
     */
    public <T extends Descontos> List<T> buscarDescontosPorTipo(Class<T> tipo) {
        List<T> resultado = new ArrayList<>();
        for (Descontos d : descontos) {
            if (tipo.isInstance(d)) {
                resultado.add(tipo.cast(d));
            }
        }
        return resultado;
    }

    double aplicarMelhorDesconto(Pedido aThis) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
