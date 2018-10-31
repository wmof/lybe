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
	private File arquivo;
	public Arquivo() {
		arquivo = new File("arquivo.txt");
	}
	public void escreveTexto() throws IOException{
		FileWriter inserindo = new FileWriter(arquivo, true);
		inserindo.write("1. Mario"+"\n");
		inserindo.write("2. Luigi"+"\n");
		inserindo.write("3. Jose"+"\n");
		inserindo.write("4. Pedro"+"\n");
		inserindo.write("5. Francisco");
		inserindo.close();
	}
}
