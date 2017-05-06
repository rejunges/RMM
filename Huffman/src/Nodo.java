
/**
 *
 * @author Renata e Yan - RMM 2017/1
 */


public class Nodo {
    
    float probabilidade;
    String nome;
    Nodo esquerdo;
    Nodo direito;
    Nodo pai;
    
    public Nodo(String nome, float probabilidade){
        this.probabilidade = probabilidade;
        this.nome = nome;
        this.esquerdo = null;
        this.direito = null;
        this.pai = null;
    }
    
    public void adicionaEsquerdo(Nodo esquerdo){
        this.esquerdo = esquerdo;
    }
    
    public void adicionaDireito(Nodo direito){
        this.direito = direito;
    }
    
    public void adicionaPai(Nodo pai){
        this.pai = pai;
    }
        
}
