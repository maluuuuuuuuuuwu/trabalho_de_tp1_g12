package classes;
/**
 * 
 * Classe SubPedido (Parte do Arthur).
 * Representa uma parte de um pedido, associando um item a uma quantidade.
 * Agrega um IItem (interface da Malu).
 */
/**
 *
 * @author malu
 */
class SubPedido {
    public Item item;
    public int quantidade;

    /**
     * Construtor para a classe SubPedido.
     * @param item O item do menu (IItem da Malu).
     * @param quantidade A quantidade do item.
     */
    public SubPedido(Item item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }

    // Getters
    public Item getItem() {
        return item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Calcula o subtotal do subpedido (preço do item * quantidade).
     * @return O subtotal do subpedido.
     */
    public double calculaSubtotal() {
        return item.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return quantidade + "x " + item.getIngredientes() + " (R$" + String.format("%.2f", item.getPreco()) + ")";
    }
}
