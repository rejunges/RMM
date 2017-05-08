
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
    
    HashMap<Character, Float> tabelaProbabilidades;
    HashMap<Character, Integer> tabelaVerificaResultado;
    HashMap<Character, String> tabelaCodigo;
    HashMap<Character, Integer> tabelaComprimento;
    File arquivoComprimido;
    ArvoresHuffman arvoreHuffman;
    int numeroCaracteres; //número da caracteres (calculado através da tabela de probabilidade)
    String resultado;
    
    //Receber uma entrada comprimida e o arquivo com a probabilidade de ocorrência de cada caractere
    public Decodificador(File entradaComprimida, File arquivoProbabilidade) throws FileNotFoundException, IOException{
        
        tabelaProbabilidades = new HashMap<>();
        tabelaVerificaResultado = new HashMap<>();
        geraTabelaProbabilidade(arquivoProbabilidade);
        arvoreHuffman = new ArvoresHuffman(tabelaProbabilidades);
        tabelaCodigo = new HashMap<>();
        tabelaComprimento = new HashMap<>();
        arquivoComprimido = entradaComprimida;
        resultado = "";
        
    }
    
    public void decodifica() throws FileNotFoundException, IOException{
        
        Nodo raiz = arvoreHuffman.geraArvore();
        arvoreHuffman.geraCodigoCaracteres(raiz, tabelaCodigo, tabelaComprimento);
        byte[] vetorBytes = new byte[(int) arquivoComprimido.length()];
        String bits;
        int cont = 0; //Contador pra ver o total de caracteres que tem na string final
        boolean flag = false; //Verifica se está naqueles ultimos 0s
        int ponteiro; //ponteiro que vai percorrer cada bit
                
        DataInputStream leitorBinario = new DataInputStream(new FileInputStream(arquivoComprimido));
        leitorBinario.readFully(vetorBytes, 0, (int) arquivoComprimido.length());
        bits = leBytes(vetorBytes);
                
        for(ponteiro = 0; ponteiro < bits.length(); ponteiro++){ //vai procurando cada bit na tabela até achar o código correspondente
            String codigo = "";
            codigo += bits.toCharArray()[ponteiro];
            
            while(!tabelaCodigo.containsValue(codigo)){ //enquanto não achar o código correspondente, vai incrementando o comprimento a procurar
                ponteiro++;
                
                if (ponteiro < bits.length()){
                    codigo += bits.toCharArray()[ponteiro];
                }
                else{
                    flag = true;
                    break;
                }
            }
            
            if (!flag){
                for(char c: tabelaCodigo.keySet()){ //procura o caractere correspondente ao código e armazena na String resultado
                    if(tabelaCodigo.get(c).equals(codigo)){
                        resultado += c;
                        cont++;
                        
                        if(tabelaVerificaResultado.containsKey(c)){
                            tabelaVerificaResultado.put(c, tabelaVerificaResultado.get(c)+1); //incrementa quando acha o caractere
                        }
                        else{
                            tabelaVerificaResultado.put(c, 1); //primeira ocorrencia do caractere
                        }
                        
                        break;
                    }
                    
                }
            }

            
        }

        verificaResultado(cont);
        gravaResultado();
        
    }
    
    public void verificaResultado(int cont){
        float prob = 0;
        String resultadoParcial;
        char ultimaLetra;
        boolean flag = false;
        
        for (char c : tabelaProbabilidades.keySet()){
            while (!flag){
                prob = (float)(tabelaVerificaResultado.get(c) * 100)/cont;
                resultadoParcial = "";
                if (!(prob == tabelaProbabilidades.get(c))){

                    for(int i = 0; i < resultado.length() - 1; i++){
                        resultadoParcial += resultado.charAt(i);
                    }

                    ultimaLetra = resultado.charAt(resultado.length()-1);
                    resultado = resultadoParcial;
                    tabelaVerificaResultado.replace(ultimaLetra, tabelaVerificaResultado.get(ultimaLetra) - 1);
                    cont--;
                }
                else flag = true;
            }
        }
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
            if(linha.startsWith(" ")){ //caso especial de ser o caractere espaço
                c = ' ';
                probabilidade = Float.valueOf(linha.substring(2, linha.length())); //pega o valor da probabilidade
                tabelaProbabilidades.put(c, probabilidade);
            }
            else{
                valoresLinha = linha.split(" ");
                c = valoresLinha[0].toCharArray()[0];
                probabilidade = Float.valueOf(valoresLinha[1]);
                tabelaProbabilidades.put(c, probabilidade);
            }    
        }
        

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
