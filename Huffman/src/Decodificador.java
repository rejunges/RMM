
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Renata e Yan - RMM 2017/1
 */


public class Decodificador {
    String resultado;
    
    public void gravaResultado() throws IOException{
        
        File arquivo = new File("saida.txt");
        BufferedWriter informacoes = new BufferedWriter(new FileWriter(arquivo));
        
        informacoes.write(resultado);
        informacoes.flush();
        informacoes.close();
    }
    
    public static void main(String[] args) throws IOException{
        
        Decodificador decodificador = new Decodificador();

    }
    
}
