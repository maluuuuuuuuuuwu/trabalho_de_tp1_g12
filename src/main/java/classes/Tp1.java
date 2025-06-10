/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tp1;

/**
 *
 * @author alexb
 */
import java.util.Arrays;

public class Tp1 {

    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("123.456.789-00", "Rua Exemplo, 123", "(61) 99999-9999", "Alex");

        Pedido pedido1 = new Pedido(Arrays.asList(100.0, 200.0, 150.0), Arrays.asList(20.0, 30.0), 50.0);
        System.out.printf("Total do pedido: R$ %.2f%n", pedido1.calculaTotal());
    }
}

