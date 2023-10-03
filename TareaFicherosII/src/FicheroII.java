import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class FicheroII {
    private static LinkedList<Estudiante> estudiantes = new LinkedList<>();
    private static Map<String, Double[]> hashMap = new HashMap<>();
    private static LinkedList<String> especialidades = new LinkedList<>();

    public static double promedio(){
        double notas = 0;
        for (Estudiante estudiante : estudiantes) 
            notas += estudiante.getNota();
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
                for (int j = 0; j < estudiantes.size(); j++) 
                    if(estudiantes.get(j).getNota() == estudiantes.get(i).getNota())
                        cont++;
                
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
        for (Estudiante estudiante : estudiantes) 
            numeros.add(estudiante.getNota());
        
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

    public static String especialidades(){
        String espec =  "";
        for (Estudiante estudiante : estudiantes) 
            if(!especialidades.contains(estudiante.getEspecialidad())){
                especialidades.add(estudiante.getEspecialidad());
                espec += estudiante.getEspecialidad() + " ";
            }
        
        return espec;
    }

    public static Map<String, Double[]> espcialidaMap(){
        for (String especialidad : especialidades) 
            hashMap.put(especialidad, new Double[] {promedioEspec(especialidad) , aprobadosEspec(especialidad),
                 (100.0 - aprobadosEspec(especialidad))});

        return hashMap;
    }
    public static double promedioEspec(String especialidad){
        double notas = 0;
        int estud = 0;
        for (Estudiante estudiante : estudiantes) 
            if(estudiante.getEspecialidad().equals(especialidad)){
                notas += estudiante.getNota();
                estud++;
            }
        
        return notas/estud;
    }
    public static double aprobadosEspec(String especialidad){
        double aprobados = 0;
        double estud = 0;
        for (Estudiante estudiante : estudiantes) 
            if(estudiante.getEspecialidad().equals(especialidad)){
                estud++;
                if(estudiante.getNota() >= 5)
                    aprobados++;
            }        
        
        return Double.valueOf((aprobados * 100)/estud);
    }
    

    public static void main(String[] args) throws Exception {
         String usuario = System.getProperty("user.home");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(
                    new FileInputStream(usuario + "\\Desktop\\alumnos.csv"), "UTF-8"));
            BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(usuario + "\\Desktop\\salida.txt",false), "UTF-8"));

        String linea;
        String[] crearEstudiante;
        DecimalFormat formato = new DecimalFormat("#.##");  

        while((linea = bfr.readLine()) != null){
            crearEstudiante = linea.split(";");
            if(!crearEstudiante[2].equals("Nota"))
            estudiantes.add(new Estudiante(crearEstudiante[0], crearEstudiante[1], Integer.valueOf(crearEstudiante[2])));
        }
        double aprobad = aprobados();
        bfw.write("N.º Total: " + estudiantes.size() + "\n" );
        bfw.write("Promedio: " + promedio() + "\n");
        bfw.write("Moda: " + moda() + "\n");
        bfw.write("Mediana: " + mediana() + "\n");
        bfw.write("Nº Aprobados(%): " + formato.format(aprobad)  + "%\n");
        bfw.write("Nº Suspensos(%): " + formato.format((100 - aprobad)) + "%\n");
        bfw.write("Listado Especialidades: " + especialidades() + "\n"); 
        hashMap = espcialidaMap();
        
        hashMap.forEach((k,v) -> {
            try {
                bfw.write("Para " + k + 
                "\n   Promedio " + v[0] + 
                "\n   Nº Aprobados(%) " + formato.format(v[1]) + 
                "\n   Nº Suspensos(%) " + formato.format(v[2]) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
        hashMap.forEach((k,v) -> System.out.println("Fruta: " + k ));


    }
}
