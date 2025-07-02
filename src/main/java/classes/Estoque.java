package classes;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerencia todas as operações relacionadas ao estoque da loja.
 * Controla adição, remoção e consulta de itens, com notificação de mudanças.
 */
/**
 *
 * @author malu
 */
public class Estoque {
    private List<ItemEstoque> estoque = new ArrayList<>();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Adiciona um listener para monitorar mudanças no estoque.
     * @param listener Objeto que será notificado sobre mudanças
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Adiciona um novo item ao estoque.
     * @param item Item a ser adicionado (não pode ser nulo)
     * @throws IllegalArgumentException Se o item for nulo
     */
    public void addItem(ItemEstoque item) {
        estoque.add(item);
    }

    /**
     * Remove um item do estoque.
     * @param item Item a ser removido
     * @return true se o item foi removido com sucesso
     */
    public boolean removerItem(ItemEstoque item) {
        return item != null && estoque.remove(item);
    }

    /**
     * Diminui em 1 a quantidade de um item no estoque, se ele existir e tiver quantidade maior que 0.  
     * Entrada: itemName (String)  
     * Saída: nenhuma
     */
    public void removerUmItemEstoque(String itemName) {
        for (ItemEstoque item : estoque) {
            if (item.getNome().equals(itemName) && item.getQuantidade() > 0) {
                item.setQuantidade(item.getQuantidade() - 1);
                break;
            }
        }
    }

    /**
     * Obtém uma cópia segura da lista de estoque.
     * @return Lista imutável de itens em estoque
     */
    public List<ItemEstoque> getEstoque() {
        return new ArrayList<>(estoque);
    }

    /**
     * Verifica se um ingrediente está disponível no estoque.
     * @param ingrediente Nome do ingrediente a verificar
     * @return true se o ingrediente está disponível com quantidade > 0
     */
    public boolean verificarDisponibilidade(String ingrediente) {
        return estoque.stream()
                     .anyMatch(item -> item.getNome().equals(ingrediente) && item.getQuantidade() > 0);
    }

    /**
     * Busca um item no estoque pelo nome.
     * @param nome Nome do item a buscar
     * @return Item encontrado ou null se não existir
     */
    public ItemEstoque buscarItem(String nome) {
        return estoque.stream()
                    .filter(item -> item.getNome().equals(nome))
                    .findFirst()
                    .orElse(null);
    }
}
