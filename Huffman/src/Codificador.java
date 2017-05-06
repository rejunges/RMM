
import java.util.HashMap;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Renata e Yan - RMM 2017/1
 */


public class Codificador {
    
    HashMap<Character, Integer> tabelaOcorrencias;
    HashMap<Character, Float> tabelaProbabilidades;
    ArrayList<Nodo> listaNodos;
    Nodo raiz;
    int tamanhoEntrada; //número total de caracteres da entrada
    
    public Codificador(){
        tabelaOcorrencias = new HashMap<>();
        tabelaProbabilidades = new HashMap<>();
        listaNodos = new ArrayList<>();
        tamanhoEntrada = 0;
    }
    
    public void codifica(File entrada) throws IOException{
        
        geraTabelaOcorrencia(entrada);
        geraTabelaProbabilidade();
        geraListaNodos();
        
    }
    
    public void geraTabelaOcorrencia(File entrada) throws FileNotFoundException, IOException{ //envia o arquivo de entrada como parâmetro, para gerar seu FileReader e completar a tabelaOcorrencias;
        
        BufferedReader leitor = new BufferedReader(new FileReader(entrada));
        int valorChar;
        char c;
        
        while((valorChar = leitor.read()) != -1){ //pega o valor int (ascii) de cada caractere
            
            tamanhoEntrada++;
            c = (char) valorChar;
            if(tabelaOcorrencias.containsKey(c)){
                tabelaOcorrencias.put(c, tabelaOcorrencias.get(c)+1); //incrementa quando acha o caractere
            }
            else{
                tabelaOcorrencias.put(c, 1); //primeira ocorrencia do caractere
            }
       
        }
        
        /* ---DEBUG---
        System.out.println("Tabela de ocorrencias: \n");
        for(char carac: tabelaOcorrencias.keySet()){
            System.out.println(carac + ": " + tabelaOcorrencias.get(carac).toString());
        }*/

    }
    
    public void geraTabelaProbabilidade(){
        
        int ocorrencia;
        float probabilidade = 0;
        
        for (char letra : tabelaOcorrencias.keySet()){
            
            ocorrencia = tabelaOcorrencias.get(letra);
            probabilidade = (float) (ocorrencia*100)/tamanhoEntrada ;
            tabelaProbabilidades.put(letra, probabilidade);
       
        } 
        
        /* ---DEBUG---
        System.out.println("Tabela de probabilidades: \n");
        for(char carac: tabelaProbabilidades.keySet()){
            System.out.println(carac + ": " + Float.toString(tabelaProbabilidades.get(carac)));
        }*/
        
    }
    
    public void geraListaNodos(){
        
        for(char c: tabelaProbabilidades.keySet()){
            Nodo nodo = new Nodo(String.valueOf(c), tabelaProbabilidades.get(c));
            listaNodos.add(nodo);
            //System.out.println("NOME DA STRING INSERIDA NO NODO: " + nodo.nome); ---DEBUG---      
        }
   
    }
    
    public static void main(String[] args) throws IOException{
        
        Codificador codificador = new Codificador();
        File entrada = new File("entrada.txt");
        codificador.codifica(entrada);

    }

    
}
