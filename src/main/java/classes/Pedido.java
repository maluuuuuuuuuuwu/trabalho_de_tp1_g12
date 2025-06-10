/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author alexb
 */
import java.util.List;
public class Pedido {
     private List<Double> itens;
    private List<Double> listaDescontos;
    private double frete;

    public Pedido(List<Double> itens, List<Double> listaDescontos, double frete) {
        this.itens = itens;
        this.listaDescontos = listaDescontos;
        this.frete = frete;
    }

    public double calculaTotal() {
        double totalItens = itens.stream().mapToDouble(Double::doubleValue).sum();
        double totalDescontos = listaDescontos.stream().mapToDouble(Double::doubleValue).sum();
        return totalItens - totalDescontos + frete;
    }
}


