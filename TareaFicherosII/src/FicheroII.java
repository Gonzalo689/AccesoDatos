import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.LinkedList;


public class FicheroII {
    private static LinkedList<Estudiante> estudiantes = new LinkedList<>();

    public static double promedio(){
        double notas = 0;
        for (Estudiante estudiante : estudiantes) {
            notas += estudiante.getNota();
        }
        return notas/estudiantes.size(); 
    }
    public static int moda(){
        LinkedList<Integer> numeros = new LinkedList<>();

        int cantMax = 0;
        int moda = estudiantes.get(0).getNota();
        
        for (int i = 0; i < estudiantes.size(); i++) {
            int cont=0;
            if(!numeros.contains(estudiantes.get(i).getNota())){
                numeros.add(estudiantes.get(i).getNota());
                for (int j = 0; j < estudiantes.size(); j++) {
                    if(estudiantes.get(j).getNota() == estudiantes.get(i).getNota())
                        cont++;
                }
                if(cont > cantMax){
                    cantMax = cont;
                    moda = estudiantes.get(i).getNota();
                }
                cont = 0;
            }
        }
        return moda;
    }
    public static int mediana(){
        LinkedList<Integer> numeros = new LinkedList<>();
        for (Estudiante estudiante : estudiantes) {
            numeros.add(estudiante.getNota());
        }
        Collections.sort(numeros);
        int cantNum = numeros.size();
        if( cantNum % 2 != 0)
        
            return numeros.get(cantNum/2);
        else
            return (numeros.get((cantNum/2)-1) + numeros.get(cantNum/2))/2;        
    }

    public static double aprobados(){
        double aprobados = 0;
        for (Estudiante estudiante : estudiantes) 
            if(estudiante.getNota()>=5)
                aprobados++;
        return Double.valueOf((aprobados * 100)/estudiantes.size());
    }
    public static double suspensos(){
        double suspensos = 0;
        for (Estudiante estudiante : estudiantes) 
            if(estudiante.getNota() < 5)
                suspensos++;
        return (suspensos * 100)/estudiantes.size();
    }


    public static void main(String[] args) throws Exception {
         String usuario = System.getProperty("user.home");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(
                    new FileInputStream(usuario + "\\Desktop\\alumnos.csv"), "UTF-8"));
            BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(usuario + "\\Desktop\\fichero2.txt",true), "UTF-8"));

        String linea;
        String[] crearEstudiante;
        

        while((linea = bfr.readLine()) != null){
            crearEstudiante = linea.split(";");
            if(!crearEstudiante[2].equals("Nota"))
            estudiantes.add(new Estudiante(crearEstudiante[0], crearEstudiante[1], Integer.valueOf(crearEstudiante[2])));
        }
        
        System.out.println("Numero de estudiantes: " + estudiantes.size());
        System.out.println("Promedio: " + promedio());
        System.out.println("Moda: " + moda());
        System.out.println("Mediana: " + mediana());
        System.out.println("Nº Aprobados(%): " + aprobados() + "%");
        System.out.println("Nº Suspensos(%): " + suspensos() + "%");
        
        
        bfr.close();
        bfw.close();


    }
}
