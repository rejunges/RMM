
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Renata e Yan - RMM 2017/1
 */
public class ArvoresHuffman {
    
    private HashMap<Character, Float> tabelaProbabilidades;
    private PriorityQueue<Nodo> filaNodos;
    private Comparator<Nodo> comparadorNodos;
    private Stack <Boolean> pbits;
    
    public ArvoresHuffman(HashMap<Character, Float> tabelaProbabilidades){
        
        this.tabelaProbabilidades = tabelaProbabilidades;
        comparadorNodos = new ComparadorProbabilidades();
        filaNodos = new PriorityQueue<>(27, comparadorNodos);
        pbits = new Stack<Boolean>();
        
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
        
       /* ---DEBUG ---*/
       //leArvore(nodo);
       
        return nodo; //Vai ser sempre a raiz
        
    }
    
    /*----DEBUG---
    private void leArvore(Nodo nodo){
        
        System.out.println("\nNodo: " + nodo.nome);
        if (nodo.pai != null){
            System.out.println("Pai: " + nodo.pai.nome);
        }
        if (nodo.esquerdo != null){
            System.out.println("Esquerdo: " + nodo.esquerdo.nome);
        }
        if (nodo.direito != null){
            System.out.println("Direito: " + nodo.direito.nome);
        }
        if (nodo.esquerdo != null){
            leArvore(nodo.esquerdo);
        }
        if(nodo.direito != null){
            leArvore(nodo.direito);
        }        
        
        
        
    }*/
    
    /*---DEBUG---
    private void printFilaNodos(){
        System.out.println();
        for(Nodo nodo : filaNodos){
            System.out.println(nodo.nome + " " + nodo.probabilidade);
        }
    }
    */
    
    public void geraCodigoCaracteres(Nodo raiz, HashMap<Character, String> tabelaCodigo, HashMap<Character, Integer> tabelaComprimento){
        
        if (raiz.esquerdo != null || raiz.direito != null){
            pbits.push(false);
            geraCodigoCaracteres(raiz.esquerdo, tabelaCodigo, tabelaComprimento);
            pbits.pop();
            pbits.push(true);
            geraCodigoCaracteres(raiz.direito, tabelaCodigo, tabelaComprimento);
            pbits.pop();
        }
        
        else{
            //Chegou na folha
            Stack <Boolean> pilhaCodigo = new Stack<>();
            pilhaCodigo = (Stack<Boolean>) pbits.clone();
            String codigo = "";
            int comprimento = 0;
            
            while(!pilhaCodigo.isEmpty()){
            
                if(pilhaCodigo.pop() == true){
                    codigo = "1" + codigo;
                }
                else{
                    codigo = "0" + codigo;
                }
                comprimento++;
                
            }
            
            tabelaCodigo.put(raiz.nome.charAt(0), codigo);
            tabelaComprimento.put(raiz.nome.charAt(0), comprimento);    

        }
        
    }
    
    private void geraFilaNodos(){
        
        for(char c: tabelaProbabilidades.keySet()){
            Nodo nodo = new Nodo(String.valueOf(c), tabelaProbabilidades.get(c));
            filaNodos.add(nodo);
        //    System.out.println("NOME DA STRING INSERIDA NO NODO: " + nodo.nome);   ---DEBUG---      
        }

    }
    
    
    
}
