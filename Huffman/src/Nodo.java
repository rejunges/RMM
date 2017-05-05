/**
 *
 * @author Renata e Yan - RMM 2017/1
 */


public class Nodo {
    
    float probabilidade;
    String nome;
    Nodo esquerdo;
    Nodo direito;
    
    public Nodo(String nome, float probabilidade){
        this.probabilidade = probabilidade;
        this.nome = nome;
        this.esquerdo = null;
        this.direito = null;
    }
    
}
