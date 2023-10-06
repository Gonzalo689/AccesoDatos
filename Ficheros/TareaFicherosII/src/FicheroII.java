import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FicheroII {
    private static Map<String, LinkedList<Integer>> hashMap = new HashMap<>();
    private static LinkedList<Estudiante> estudiantes = new LinkedList<>();

    public static double promedio(LinkedList<Integer> notas){
        double total = 0.0;
        for (Integer nota : notas) 
            total += nota;
        return total/notas.size(); 
    }
    public static int moda(LinkedList<Integer> notas){
        LinkedList<Integer> numeros = new LinkedList<>();
        int cantMax = 0;
        int moda = notas.get(0);
        
        for (int i = 0; i < notas.size(); i++) {
            int cont=0;
            if(!numeros.contains(notas.get(i))){
                numeros.add(notas.get(i));
                for (int j = 0; j < notas.size(); j++) 
                    if(notas.get(j) == notas.get(i))
                        cont++;
                
                if(cont > cantMax){
                    cantMax = cont;
                    moda = notas.get(i);
                }
                cont = 0;
            }
        }
        return moda;
    }
    public static int mediana(LinkedList<Integer> notas){
        LinkedList<Integer> numeros = new LinkedList<>();
        for (Integer nota : notas) 
            numeros.add(nota);
        
        Collections.sort(numeros);
        int cantNum = numeros.size();
        if( cantNum % 2 != 0)
            return numeros.get(cantNum/2);
        else
            return (numeros.get((cantNum/2)-1) + numeros.get(cantNum/2))/2;        
    }

    public static double aprobados(LinkedList<Integer> notas){
        double aprobados = 0;
        for (Integer nota : notas) 
            if(nota >=5)
                aprobados++;

        return Double.valueOf((aprobados * 100)/notas.size());
    }
   /**
    * Función que devuelve el nombre completo de los tres mejores estudiantes de cada asignatura
    * @param asignatura nombre de la asignatura
    * @return devuelve un string con el nombre completo de los tres mejores estudiante de la asignatura
    */
    public static String topTres(String asignatura){
        String top = "";
        Comparator<Estudiante> c = new Comparator<Estudiante>() {
            @Override
            public int compare(Estudiante e1, Estudiante e2) {
                return Integer.compare(e1.getNota(), e2.getNota());
            }
        };
        estudiantes.sort(c);
        int cont = 0;
        for (int i = estudiantes.size() - 1; i > 0 && cont != 3 ; i--) 
            if (estudiantes.get(i).getEspecialidad().equals(asignatura)){
                if(cont != 0)
                    top += ", " + estudiantes.get(i).getNombre();
                else
                    top += estudiantes.get(i).getNombre();
                cont ++;
            }   

        top += ".";
        return top ;
    }

    public static void escribirArchivo(BufferedWriter bfw, LinkedList<Integer> notas) throws IOException{
         // Formato para que solo muestre dos decimales
        DecimalFormat formato = new DecimalFormat("#.##"); 
        
        double aprobad = aprobados(notas);
        bfw.write("N.º Total: " + notas.size() + "\n" );
        bfw.write("Promedio: " + promedio(notas) + "\n");
        bfw.write("Moda: " + moda(notas) + "\n");
        bfw.write("Mediana: " + mediana(notas) + "\n");
        bfw.write("Nº Aprobados(%): " + formato.format(aprobad)  + "%\n");
        bfw.write("Nº Suspensos(%): " + formato.format((100 - aprobad)) + "%\n");
        bfw.write("Listado Especialidades:");

        // forEach que me lanza el nombre de las asignaturas que hay con el porcentaje de alumnos que hay de cada una
        hashMap.forEach((k,v) -> {
            try {
                bfw.write(" " + k + "(" + Double.valueOf((v.size()*100) /estudiantes.size()) + "%)");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bfw.newLine();
        hashMap.forEach((k,v) -> {
            try {
                bfw.write("Para " + k + 
                "\n   Promedio " + promedio(v) +
                "\n   Nº Aprobados(%) " + formato.format(aprobados(v)) + "%" + 
                "\n   Nº Suspensos(%) " + formato.format((100 - aprobados(v)))  + "%"+
                "\n   Top 3 Estudiantes: " + topTres(k) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        try {
            String usuario = System.getProperty("user.home");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(
                    new FileInputStream(usuario + "\\Desktop\\alumnos.csv"), "UTF-8"));
            BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(usuario + "\\Desktop\\salida.txt",false), "UTF-8"));

            String linea;
            String[] estudiante;
            LinkedList<Integer> notas = new LinkedList<>();

            //Leer archivo csv
            while((linea = bfr.readLine()) != null){
                estudiante = linea.split(";");
                if(!estudiante[2].equals("Nota")){
                    estudiantes.add(new Estudiante(estudiante[0], estudiante[1], Integer.valueOf(estudiante[2]))) ;
                    notas.add(Integer.valueOf(estudiante[2]));
                    if(!hashMap.containsKey(estudiante[1])){
                        hashMap.put(estudiante[1], new LinkedList<>());
                        hashMap.get(estudiante[1]).add(Integer.valueOf(estudiante[2]));
                    }else
                        hashMap.get(estudiante[1]).add(Integer.valueOf(estudiante[2]));
                }
            }

            // Escribir nuevo archivo
            escribirArchivo(bfw, notas);

            bfr.close();
            bfw.close();

        } catch (Exception e) {
            System.out.println("Fallo ");
        }
        
    }
}
