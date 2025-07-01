/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author malu
 */
public class DescontoQuantidade extends Descontos {
    private int quantidadeMinima;
    private double percentual;

    public DescontoQuantidade(String descricao, int quantidadeMinima, double percentual) {
        super(descricao);
        this.quantidadeMinima = quantidadeMinima;
        this.percentual = percentual;
    }

    public double aplicarDesconto(double valorOriginal, int quantidade) {
        if (quantidade >= quantidadeMinima) {
            return valorOriginal * (1 - percentual);
        }
        return valorOriginal;
    }

    @Override
    public double aplicarDesconto(double valorOriginal) {
        return valorOriginal; // fallback neutro (não usado diretamente)
    }
}
