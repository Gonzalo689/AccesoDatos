import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.LinkedList;

public class FicheroIII {

    public void generarIndice(String src, String dst){
        try {
        RandomAccessFile raf = new RandomAccessFile(src, "r");
        raf.seek(30);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(
                    new FileInputStream(raf.getFD()), "UTF-8"));
        BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(dst), "UTF-8"));
        
        String linea;
        String[] fichero;
        while((linea = bfr.readLine()) != null){
            fichero = linea.split(";");
            System.out.println(fichero[0]);
            
        } 

        } catch (Exception e) {
            System.out.println("Fallo en la manipulación de los ficheros");
        }
        
    }

    public void buscarInfo(String dni, String indexFile){

    }
    public static void main(String[] args){
        FicheroIII app = new FicheroIII();
        if(args.length==3){
            switch(args[0]){
                case "-g":
                    app.generarIndice(args[1], args[2]);
                break;
                case "-f":
                    app.buscarInfo(args[1], args[2]);
                break;
                default:
                System.err.println("Operación no implementada");    
                break;
            }
        }
        else{
            System.err.println("El programa funciona con 3 argumentos");
        }
    }
}