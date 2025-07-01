import classes.Loja;
import telas.Tela_inicial;

/**
 *
 * @author malu
 */
public class TP1 {
    public static void main(String[] args) {
        Loja loja = new Loja();
        new Tela_inicial(loja).setVisible(true);
    }
}
