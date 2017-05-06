
import java.util.HashMap;
import java.io.*;

/**
 *
 * @author Renata e Yan - RMM 2017/1
 */


public class Codificador {
    
    HashMap<Character, Integer> tabelaOcorrencias;
    HashMap<Character, Float> tabelaProbabilidades;
    Nodo raiz;
    int tamanhoEntrada;
    
    public Codificador(){
        tabelaOcorrencias = new HashMap<>();
        tamanhoEntrada = 0;
    }
    
    public void geraTabelaOcorrencia(File entrada) throws FileNotFoundException, IOException{ //envia o arquivo de entrada como parâmetro, para gerar seu FileReader e completar a tabelaOcorrencias;
        
        BufferedReader leitor = new BufferedReader(new FileReader(entrada));
        int valorChar;
        char c;
        
        while((valorChar = leitor.read()) != -1){ //pega o valor int (ascii) de cada caractere
            
            tamanhoEntrada = tamanhoEntrada + 1;
            c = (char) valorChar;
            if(tabelaOcorrencias.containsKey(c)){
                tabelaOcorrencias.put(c, tabelaOcorrencias.get(c)+1); //incrementa quando acha o caractere
            }
            else{
                tabelaOcorrencias.put(c, 1); //primeira ocorrencia do caractere
            }
       
        }
    }
    
    public void geraTabelaProbabilidade(){
        int ocorrencia = 0;
        float probabilidade = 0;
        
        for (Character letra : tabelaOcorrencias.keySet()){
            
            ocorrencia = tabelaOcorrencias.get(letra);
            probabilidade = (ocorrencia*100)/tamanhoEntrada ;
            tabelaProbabilidades.put(letra, probabilidade);
       
        } 
        
    }
    
    
    
    
}
