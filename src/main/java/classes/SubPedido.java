/**
 * Classe SubPedido (Parte do Arthur).
 * Representa uma parte de um pedido, associando um item a uma quantidade.
 * Agrega um IItem (interface da Malu).
 */
class SubPedido {
    private IItem item;
    private int quantidade;

    /**
     * Construtor para a classe SubPedido.
     * @param item O item do menu (IItem da Malu).
     * @param quantidade A quantidade do item.
     */
    public SubPedido(IItem item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }

    // Getters
    public IItem getItem() {
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
        return quantidade + "x " + item.getNome() + " (R$" + String.format("%.2f", item.getPreco()) + ")";
    }
}
