import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class FicheroIII {

    public void generarIndice(String src, String dst){
        try {
        int numCaracteres=31;

        RandomAccessFile raf = new RandomAccessFile(src, "r");
        raf.seek(numCaracteres);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(
                    new FileInputStream(raf.getFD()), "UTF-8"));
        BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(dst, false), "UTF-8"));
        
        String linea;
        String[] fichero;
        
        while((linea = bfr.readLine()) != null){
            fichero = linea.split(";");
            bfw.write(fichero[0] + ";" + numCaracteres + "\n");
            numCaracteres += linea.getBytes().length;
        } 
        raf.close();
        bfr.close();
        bfw.close();

        } catch (Exception e) {
            System.out.println("Fallo en la manipulación de los ficheros");
        }
        
    }

    public void buscarInfo(String dni, String indexFile){
        try {
            RandomAccessFile raf = new RandomAccessFile("db.csv", "r");
            BufferedReader bfr2 = new BufferedReader(new InputStreamReader(
                    new FileInputStream(indexFile), "UTF-8"));      
            String linea;
            String[] fichero;
            int numCaracteres = 0;

            while((linea = bfr2.readLine()) != null){
            fichero = linea.split(";");
                if(fichero[0].equals(dni)){
                    numCaracteres = Integer.valueOf(fichero[1]);
                    raf.seek(numCaracteres);
                }
            } 
            BufferedReader bfr = new BufferedReader(new InputStreamReader(
                    new FileInputStream(raf.getFD()), "UTF-8"));
                    
            if(bfr.readLine() != null &&  numCaracteres != 0)
                System.out.println(bfr.readLine());
            
            raf.close();
            bfr2.close();
            bfr.close();

        } catch (Exception e) {
            System.out.println("Fallo en la manipulación de los ficheros");
        }
        

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