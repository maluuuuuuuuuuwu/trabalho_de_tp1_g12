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
    private double valorDesconto;

    public DescontoFixo(String descricao, double valorDesconto) {
        super(descricao);
        this.valorDesconto = valorDesconto;
    }

    @Override
    public double aplicarDesconto(double valorOriginal) {
        return Math.max(0, valorOriginal - valorDesconto);
    }
}
