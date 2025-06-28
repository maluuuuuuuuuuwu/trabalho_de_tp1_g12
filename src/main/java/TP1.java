import classes.Loja;
import telas.tela_inicial;

/**
 *
 * @author malu
 */
public class TP1 {
    public static void main(String[] args) {
        Loja loja = new Loja();
        new tela_inicial(loja).setVisible(true);
    }
}
