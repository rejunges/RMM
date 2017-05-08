
import java.util.Comparator;

/**
 *
 * @author Renata e Yan - RMM 2017/1
 */
public class ComparadorProbabilidades implements Comparator<Nodo> {
    
    @Override
    public int compare(Nodo primeiro, Nodo segundo){
        if(primeiro.probabilidade < segundo.probabilidade) return -1;
        if(primeiro.probabilidade > segundo.probabilidade) return 1;
        return 0;
    }
        
}
