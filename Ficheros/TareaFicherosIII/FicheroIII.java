import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;

public class FicheroIII {

    public void generarIndice(String src, String dst){

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