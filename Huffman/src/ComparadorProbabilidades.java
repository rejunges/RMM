
import java.util.Comparator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author C3PO
 */
public class ComparadorProbabilidades implements Comparator<Nodo> {
    
    @Override
    public int compare(Nodo primeiro, Nodo segundo){
        if(primeiro.probabilidade < segundo.probabilidade) return -1;
        if(primeiro.probabilidade > segundo.probabilidade) return 1;
        return 0;
    }
        
}
