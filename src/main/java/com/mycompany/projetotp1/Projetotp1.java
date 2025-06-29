/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.projetotp1;

import classes.Loja;
import telas.tela_inicial;

/**
 *
 * @author malu
 */
public class Projetotp1 {

    public static void main(String[] args) {
        Loja loja = new Loja();
        new tela_inicial(loja).setVisible(true);
    }
}
