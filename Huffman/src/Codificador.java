
import java.util.HashMap;
import java.io.*;
import java.lang.reflect.Array;
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
    
    private static int bufferBit; //os 2 atributos privados aqui são usados para escrever bit a bit no arquivo de saída (função escreveBit)
    private static int n;
    HashMap<Character, Integer> tabelaOcorrencias; //tabela de ocorrências de cada caractere do texto de entrada
    HashMap<Character, Float> tabelaProbabilidades; //tabela de probabilidade de cada caractere do texto de entrada
    HashMap<Character, String> tabelaCodigo; //tabela com o código correspondente a cada caractere da entrada, após a codificação de Huffman
    HashMap<Character, Integer> tabelaComprimento; //tabela com o comprimento do código correspondente de cada caractere
    ArvoresHuffman arvoreHuffman; //árvore de huffman, criada com a tabela de probabilidades -> retorna o nó raíz
    String arquivoEntrada; //String que armazena o texto de entrada
    int tamanhoEntrada; //número total de caracteres da entrada
    
    public Codificador(){
        tabelaOcorrencias = new HashMap<>();
        tabelaProbabilidades = new HashMap<>();
        tabelaCodigo = new HashMap<>();
        tabelaComprimento = new HashMap<>();
        tamanhoEntrada = 0;
        arquivoEntrada = "";
        arvoreHuffman = new ArvoresHuffman(tabelaProbabilidades);
    }
    
    public void codifica(File entrada) throws IOException{
        Nodo raiz;
        
        geraTabelaOcorrencia(entrada);
        geraTabelaProbabilidade();
        gravaArquivoProbabilidade();
        raiz = arvoreHuffman.geraArvore();
        arvoreHuffman.geraCodigoCaracteres(raiz, tabelaCodigo, tabelaComprimento);
        /*---DEBUG---
        for(char c: tabelaComprimento.keySet()){
            System.out.println("COMPRIMENTO DO CHAR " + c + ": " + tabelaComprimento.get(c));
        }
        for(char c: tabelaCodigo.keySet()){
            System.out.println("CODIGO DO CHAR " + c + ": " + tabelaCodigo.get(c));
        }*/
        gravaArquivoCodificado();
        gravaArquivoEntrada();
    }
    
    public void geraTabelaOcorrencia(File entrada) throws FileNotFoundException, IOException{ //envia o arquivo de entrada como parâmetro, para gerar seu FileReader e completar a tabelaOcorrencias;
        
        BufferedReader leitor = new BufferedReader(new FileReader(entrada));
        int valorChar;
        char c;
        
        while((valorChar = leitor.read()) != -1){ //pega o valor int (ascii) de cada caractere
            
            tamanhoEntrada++;
            c = (char) valorChar;
            arquivoEntrada += c; //vai concatenando a string resultante do arquivo
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
    
    public void gravaArquivoCodificado() throws IOException{
        
        File arquivo = new File ("saidacodificada.dat");
        DataOutputStream escreve = new DataOutputStream(new FileOutputStream(arquivo));
        
        for(char c : arquivoEntrada.toCharArray()){
            for(int i = 0; i < tabelaComprimento.get(c); i++){
                if(tabelaCodigo.get(c).charAt(i) == '0') escreveBit(false, escreve);
                else escreveBit(true, escreve);
            }
        }
        limpaBuffer(escreve);
        escreve.flush();
        escreve.close();
        
    }
    
    
    public void escreveBit(boolean bit, DataOutputStream escreve){
        bufferBit <<= 1;
       
        if(bit) bufferBit |= 1;
        
        n++;
        if(n == 8) limpaBuffer(escreve);
           
    }
    
    private static void limpaBuffer(DataOutputStream escreve){
        
        if (n == 0) return;
        if (n > 0) bufferBit <<= (8 - n);
        try {
            escreve.write(bufferBit);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        n = 0;
        bufferBit = 0;
    }
    
    public void gravaArquivoEntrada() throws FileNotFoundException, IOException{
        File arquivo = new File ("textoOriginalBinario.dat");
        DataOutputStream escreve = new DataOutputStream(new FileOutputStream(arquivo));
        
        escreve.writeBytes(arquivoEntrada);
        escreve.flush();
        escreve.close();
        
    }
    
    public static void main(String[] args) throws IOException{
        
        Codificador codificador = new Codificador();
        File entrada = new File("entrada.txt");
        codificador.codifica(entrada);

    }
   
}
