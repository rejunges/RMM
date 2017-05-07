
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Renata e Yan - RMM 2017/1
 */


public class Decodificador {
    
    private static int bufferBit;
    private static int n;
    HashMap<Character, Float> tabelaProbabilidades;
    HashMap<Character, String> tabelaCodigo;
    HashMap<Character, Integer> tabelaComprimento;
    File arquivoComprimido;
    ArvoresHuffman arvoreHuffman;
    String resultado;
    
    //Receber uma entrada comprimida e o arquivo com a probabilidade de ocorrência de cada caractere
    public Decodificador(File entradaComprimida, File arquivoProbabilidade) throws FileNotFoundException, IOException{
        
        tabelaProbabilidades = new HashMap<>();
        geraTabelaProbabilidade(arquivoProbabilidade);
        arvoreHuffman = new ArvoresHuffman(tabelaProbabilidades);
        tabelaCodigo = new HashMap<>();
        tabelaComprimento = new HashMap<>();
        arquivoComprimido = entradaComprimida;
        
    }
    
    public void decodifica() throws FileNotFoundException, IOException{
        
        Nodo raiz = arvoreHuffman.geraArvore();
        arvoreHuffman.geraCodigoCaracteres(raiz, tabelaCodigo, tabelaComprimento);
        byte[] vetorBytes = new byte[(int) arquivoComprimido.length()];
        String bits;
        int ponteiro; //ponteiro que vai percorrer cada bit
        
        DataInputStream leitorBinario = new DataInputStream(new FileInputStream(arquivoComprimido));
        leitorBinario.readFully(vetorBytes, 0, (int) arquivoComprimido.length());
        bits = leBytes(vetorBytes);
        
        System.out.println("STRING DE BITS: " + bits); //---DEBUG---
        
        for(ponteiro = 0; ponteiro < bits.length(); ponteiro++){ //vai procurando cada bit na tabela até achar o código correspondente
            String codigo = "";
            codigo += bits.toCharArray()[ponteiro];
            
            while(!tabelaCodigo.containsValue(codigo)){ //enquanto não achar o código correspondente, vai incrementando o comprimento a procurar
                ponteiro++;
                codigo += bits.toCharArray()[ponteiro];
                System.out.println("Codigo: " + codigo); //DEBUG
            }
            
            for(char c: tabelaCodigo.keySet()){ //procura o caractere correspondente ao código e armazena na String resultado
                if(tabelaCodigo.get(c).equals(codigo)){
                    resultado += c;
                    System.out.println("Adicionou " + c + "\n"); //DEBUG
                    break;
                }
            }
            
            ponteiro++;
            
        }
        
        gravaResultado();
        
    }
    
    public String leBytes(byte[] bytesLeitura){
        String bits = "";
        
        for(byte b: bytesLeitura){ //transforma cada byte em uma cadeia de bits (String)
            String cadeiaBits = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            bits += cadeiaBits;
        }
                
        return bits;
    }
    
    public void geraTabelaProbabilidade(File arquivoProbabilidade) throws FileNotFoundException, IOException{
        
        BufferedReader leitor = new BufferedReader(new FileReader(arquivoProbabilidade));
        String linha;
        String[] valoresLinha;
        char c;
        float probabilidade;
        
        while((linha = leitor.readLine()) != null){
            valoresLinha = linha.split(" ");
            c = valoresLinha[0].toCharArray()[0];
            probabilidade = Float.valueOf(valoresLinha[1]);
            tabelaProbabilidades.put(c, probabilidade);
        }
        
        /*---DEBUG---
        for(char car: tp.keySet()){
            System.out.println("PROBABILIDADE " + car + ": " + String.valueOf(tp.get(car)));
        }*/

    }
    
    public void gravaResultado() throws IOException{
        
        File arquivo = new File("saidaDecodificada.txt");
        BufferedWriter saidaEscrita = new BufferedWriter(new FileWriter(arquivo));
        
        saidaEscrita.write(resultado);
        saidaEscrita.flush();
        saidaEscrita.close();
    }
    
    public static void main(String[] args) throws IOException{
        
        File prob = new File("probabilidade.txt");
        File entrada = new File("saidacodificada.dat");
        Decodificador decodificador = new Decodificador(entrada, prob);
        decodificador.decodifica();

    }
    
}
