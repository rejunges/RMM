
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Renata e Yan
 */
public class ArvoresHuffman {
    
    HashMap<Character, Float> tabelaProbabilidades;
    PriorityQueue<Nodo> filaNodos;
    Comparator<Nodo> comparadorNodos;
    
    public ArvoresHuffman(HashMap<Character, Float> tabelaProbabilidades){
        
        this.tabelaProbabilidades = tabelaProbabilidades;
        comparadorNodos = new ComparadorProbabilidades();
        filaNodos = new PriorityQueue<>(27, comparadorNodos);
        
    }
    
    public Nodo geraArvore(){
        
        Nodo menor;
        Nodo maior;
        Nodo nodo = null;
        geraFilaNodos();
               
        if (filaNodos.size() == 1){
            menor = filaNodos.poll();
            nodo = new Nodo(menor.nome, menor.probabilidade);
        }
        
        while(filaNodos.size() > 1){
                menor = filaNodos.poll();
                maior = filaNodos.poll();
                nodo = new Nodo(maior.nome + "+" + menor.nome, menor.probabilidade + maior.probabilidade);
                nodo.adicionaEsquerdo(maior);
                nodo.adicionaDireito(menor);
                menor.adicionaPai(nodo);
                maior.adicionaPai(nodo);
                filaNodos.add(nodo);
                //printFilaNodos();   ----DEBUG       
        }
        
        while (nodo.esquerdo != null){
            Nodo esquerdo = nodo.esquerdo;
            System.out.println(esquerdo.nome + " " + esquerdo.probabilidade);
            nodo = esquerdo;
            
        }
        return nodo;
        
    }
    
    //---DEBUG---
    private void printFilaNodos(){
        System.out.println();
        for(Nodo nodo : filaNodos){
            System.out.println(nodo.nome + " " + nodo.probabilidade);
        }
    }
    //---FIM DEBUG---
    
    private void geraFilaNodos(){
        
        for(char c: tabelaProbabilidades.keySet()){
            Nodo nodo = new Nodo(String.valueOf(c), tabelaProbabilidades.get(c));
            filaNodos.add(nodo);
        //    System.out.println("NOME DA STRING INSERIDA NO NODO: " + nodo.nome);   ---DEBUG---      
        }

    }
    
}
