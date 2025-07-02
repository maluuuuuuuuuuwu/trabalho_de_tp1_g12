/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classes;

/**
 *
 * @author alexb e malu
 */
import java.util.List;
public class Pedido {
    private List<SubPedido> itens;
    private GerenciadorDescontos listaDescontos;
    private Loja loja;
    private Cliente cliente;

    public Pedido(List<SubPedido> itens, GerenciadorDescontos listaDescontos, Loja loja, Cliente cliente) {
        this.itens = itens;
        this.listaDescontos = listaDescontos;
        this.loja = loja;
        this.cliente = cliente;
    }

    public double calculaTotal() {
        double totalItens = 0;
        for (SubPedido item : itens) {
            totalItens += item.calculaSubtotal();
        }
        //double totalDescontos = listaDescontos.aplicarMelhorDesconto(totalItens, getQuantidadeItens());
        return totalItens + getFrete();
    }
    
    public double calculaTotal_desconto(){
        int totalItens = 0;
        for (SubPedido item : itens) {
            totalItens += item.getQuantidade();
        }
        return calculaTotal() - listaDescontos.aplicarMelhorDesconto(calculaTotal(), totalItens);
    }
    
    public List<SubPedido> getItens() {
        return itens;
    }

    public GerenciadorDescontos getListaDescontos() {
        return listaDescontos;
    }

    public Loja getLoja() {
        return loja;
    }

    public Cliente getCliente() {
        return cliente;
    }
    
    public int getQuantidadeItens(){
        int totalItens =0;
        for(SubPedido item : itens){
            totalItens += item.getQuantidade();
        }
        return totalItens;
    }
    
    public double getFrete(){
        return this.loja.calcularFrete(cliente.getEndereco());
    }
    
    public double getTempo(){
        return loja.calcularTempo();
    }
    
}


