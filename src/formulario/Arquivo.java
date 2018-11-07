/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formulario;

/**
 *
 * @author Magnero
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Arquivo {
	public Arquivo() {		
	}
	public void gerarArquivo(String titulo, String conteudo) throws IOException{
                File arquivo = new File(titulo);
		FileWriter inserindo = new FileWriter(arquivo, true);
		inserindo.write(conteudo);
		inserindo.close();
	}
}
