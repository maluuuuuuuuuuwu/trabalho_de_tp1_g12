/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.projetotp1;

import classes.Loja;
import telas.Login_inicial;

/**
 *
 * @author malu
 */
public class Projetotp1 {

    public static void main(String[] args) {
        Loja loja = new Loja();
        new Login_inicial(loja).setVisible(true);
    }
}
