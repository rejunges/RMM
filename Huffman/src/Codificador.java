
import java.util.HashMap;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 *
 * @author Renata e Yan - RMM 2017/1
 */


public class Codificador {
    
    HashMap<Character, Integer> tabelaOcorrencias;
    HashMap<Character, Float> tabelaProbabilidades;
    ArvoresHuffman arvoreHuffman;
    String arquivoEntrada;
    Stack <Boolean> pbits;
    int tamanhoEntrada; //número total de caracteres da entrada
    
    public Codificador(){
        tabelaOcorrencias = new HashMap<>();
        tabelaProbabilidades = new HashMap<>();
        tamanhoEntrada = 0;
        arquivoEntrada = "";
        pbits = new Stack<Boolean>();
        arvoreHuffman = new ArvoresHuffman(tabelaProbabilidades);
    }
    
    public void codifica(File entrada) throws IOException{
        Nodo raiz;
        
        geraTabelaOcorrencia(entrada);
        geraTabelaProbabilidade();
        gravaArquivoProbabilidade();
        raiz = arvoreHuffman.geraArvore();
        geraCodigoCaracteres(raiz);
        
    }
    
    public void geraTabelaOcorrencia(File entrada) throws FileNotFoundException, IOException{ //envia o arquivo de entrada como parâmetro, para gerar seu FileReader e completar a tabelaOcorrencias;
        
        BufferedReader leitor = new BufferedReader(new FileReader(entrada));
        int valorChar;
        char c;
        
        while((valorChar = leitor.read()) != -1){ //pega o valor int (ascii) de cada caractere
            
            tamanhoEntrada++;
            c = (char) valorChar;
            arquivoEntrada += c;
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
    
    public void gravaArquivoProbabilidade() throws IOException{
        
        File arquivo = new File("probabilidade.txt");
        BufferedWriter informacoes = new BufferedWriter(new FileWriter(arquivo));
        
        for (char letra : tabelaProbabilidades.keySet()){
            informacoes.write(letra + " " + Float.toString(tabelaProbabilidades.get(letra)));
            informacoes.newLine();
        }
        
        informacoes.flush();
        informacoes.close();
    }
    
    public void geraCodigoCaracteres(Nodo raiz){
        Stack <Boolean> pilhaCodigo;
        boolean codigo[] = null;
        int comprimento[] = null;
        
        if (raiz.esquerdo != null || raiz.direito != null){
            pbits.push(false);
            geraCodigoCaracteres(raiz.esquerdo);
            pbits.pop();
            pbits.push(true);
            geraCodigoCaracteres(raiz.direito);
            pbits.pop();
        }
        else{
            //Chegou na folha
            pilhaCodigo = pbits;
            codigo[0] = false;
            comprimento[0] = 0;
            int i = 0;
            
            while(!pilhaCodigo.isEmpty()){
            
                codigo[i] = codigo[i] | pilhaCodigo.pop(); //Nao tenho certeza se é assim
                comprimento[i] += 1;
                i++;
                
            }
   
            
        }
        
        
    }
    
    public static void main(String[] args) throws IOException{
        
        Codificador codificador = new Codificador();
        File entrada = new File("entrada.txt");
        codificador.codifica(entrada);

    }
   
}
