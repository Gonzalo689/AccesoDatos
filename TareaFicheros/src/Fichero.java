import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Fichero {
    public static void main(String[] args)  {
        try {
            String usuario = System.getProperty("user.home");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(
                    new FileInputStream(usuario + "\\Desktop\\fichero.txt"), "UTF-8"));
            BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(usuario + "\\Desktop\\fichero2.txt",false), "UTF-8"));
            
            char caracBuscar = 't';
            
            int caracter;
            int cantidad = 1;
            int fila = 1;
            int columna = 1;
            String texto= "";

            while ((caracter = bfr.read()) != -1) {
                char c = (char)caracter;
                if(c == '\n'){
                    fila++;
                    columna = 1;
                }
                if(c == caracBuscar){
                    texto += "Fila: " + fila + "\n";
                    texto += "Columna: " + columna + "\n\n";
                    cantidad++;
                }   
                
                columna++;   
            }
            bfw.write("Cantidad de " + caracBuscar + " = " + cantidad +  "\n");
            bfw.newLine();
            bfw.write(texto);
            bfw.close();
            bfr.close();

        } catch (IOException e) {
           System.out.println("No se encontro el fichero");
        }
    }
}
