import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Scanner;

public class App {
     private LinkedList<Empleado> empleados = new LinkedList<>();

    public boolean exist(File src){
        return src.exists() && src.canRead() && src.isFile();
    }

    public void insertarDatos(String src){
        File file = new File(src);
        BufferedReader bfr = null;

        try {
            if (!exist(file))
                bfr = null;
            bfr = new BufferedReader(new FileReader(file));
            String line ;
            int cont = 0;
            while ((line = bfr.readLine()) != null) {
                String[] linea = line.split(",");
                if(cont > 0){
                    if (linea.length == 3 ) {
                    empleados.add(new Empleado(linea[0], Integer.valueOf(linea[1]),Integer.valueOf(linea[2])));
                    }else if(linea.length == 4){
                        empleados.add(new Gerente(linea[0], Integer.valueOf(linea[1]),Integer.valueOf(linea[2]), linea[3]));
                    }else if(linea.length == 5){
                        empleados.add(new Director(linea[0], Integer.valueOf(linea[1]),Integer.valueOf(linea[2]), linea[3], linea[4]));
                    }
                }
                cont++;
                
            }
            bfr.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    public void agregarEmpleado(Empleado e){
        for (Empleado empleado : empleados) {
            if(empleado.getNumeroEmpl() != e.getNumeroEmpl()){
                empleados.add(e);
            }else{
                System.out.println("El empleado ya existe");
            }
        }
    }
    public void modificarEmpleados(Empleado e){
        Scanner texto = new Scanner(System.in);
        Scanner num = new Scanner(System.in);
        for (int i = 0; i < empleados.size(); i++) {
            if(empleados.get(i).getNumeroEmpl() != e.getNumeroEmpl()){
                String[] herencia = empleados.get(i).toStringCSV().split(",");
                System.out.println("Nombre del empleado");
                String nombre = texto.nextLine();
                System.out.println("Numero del empleado");
                int numEm = num.nextInt();
                System.out.println("salario del empleado");
                int salario = num.nextInt();
                
                empleados.get(i).setNombre(nombre);
                empleados.get(i).setNumeroEmpl(numEm);
                empleados.get(i).setSalario(salario);
                
                if(herencia.length == 4){
                    System.out.println("Departamento del empleado");
                    String departamento = texto.nextLine();
                    ((Gerente) empleados.get(i)).setDepartamento(departamento);
                }else if(herencia.length == 5){
                    System.out.println("Departamento del empleado");
                    String departamento = texto.nextLine();
                    System.out.println("Area responsabel del empleado");
                    String  areaResp = texto.nextLine();
                    ((Director) empleados.get(i)).setDepartamento(departamento);
                    ((Director) empleados.get(i)).setAreaResp(areaResp);
                }
                
            }
        }
        
    }
    public void eliminarEmpleado(Empleado e){
        int cont = 0;
        while (cont < empleados.size()) {
             if(empleados.get(cont).getNumeroEmpl() == e.getNumeroEmpl()){
                empleados.remove(e);
            }else{
                cont++;
            }
        }
    }
    public void guardarEmpleados(String fichero){
        File file = new File(fichero);
        
        if (!file.getParentFile().isDirectory() ) {
            file.getParentFile().mkdir();
        }
        try {
            
            BufferedWriter bfw = new BufferedWriter(new FileWriter(file.getName(), false));
            BufferedReader bfr = new BufferedReader(new FileReader("empleados.csv"));
            String line;
            while ((line = bfr.readLine()) != null) {
            bfw.write(line);
            bfw.newLine();
            }
            bfr.close();
            bfw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }

    public void formatoHerencia(){
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter("EmpleadosOrdenados.txt", false));
            int cont = 0;
            
            String empleadoString ="";
            String polimorf= "";
            
            int herencia =0;
            String total= "";

            for (int i = 0; i < 3; i++) {
                for (Empleado empleado : empleados) {
                    herencia = empleado.toStringCSV().split(",").length;
                    if(herencia == 3 && i==0){
                        polimorf = "Empleados" ;
                        cont++;
                        empleadoString +=  empleado.toStringCSV() + "\n";
                    }
                    else if(herencia==4 &&  i==1){
                        polimorf = "Gerente";
                        cont++;
                        empleadoString +=  empleado.toStringCSV() + "\n";
                    }
                    else if(herencia == 5 && i==2){
                        polimorf = "Director";
                        cont++;
                        empleadoString +=  empleado.toStringCSV() + "\n";
                    }
                    
                }
                
                total += "*****************\n";
                total += cont + " " + polimorf +"\n";
                total += empleadoString ;
                cont = 0;
                empleadoString="";
            }
            bfw.write(total);

            bfw.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void crearIndice(){
        try {
            BufferedReader bfr = new BufferedReader(new FileReader("EmpleadosOrdenados.txt"));
            BufferedWriter bfw = new BufferedWriter(new FileWriter("index.txt", false));
            String line;
            String[] linea;
            int cant = 0;
            int poli=0;
            while ((line = bfr.readLine()) != null) {
                linea = line.split(" ");
                try {
                    Integer.valueOf( linea[0]);
                    if(poli ==0){
                        bfw.write("Empleados: posicion " +cant);
                    }else if(poli ==1){
                        bfw.write("Gerente: posicion " +cant);
                    }else{
                        bfw.write("Director: posicion " +cant);
                    }
                    
                    bfw.newLine();
                    poli++;
                } catch (NumberFormatException e) {
                   
                }finally{
                    cant += line.getBytes().length;
                }
                
            }
            bfw.close();
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void obtenerInfoDepartamento(String tipoEmpleado){
        try {
            RandomAccessFile raf = new RandomAccessFile("EmpleadosOrdenados.txt", "r") ; 
            raf.seek(92);
            BufferedReader bfr = new BufferedReader(new FileReader(raf.getFD()));
            BufferedWriter bfw = new BufferedWriter(new FileWriter("departamento.txt", false));
            String line ;
            String[] linea;
            while((line = bfr.readLine()) != null){
                if(line.contains("Gerente")){
                    bfr.readLine();
                    try {
                        linea = line.split(",");
                        if(linea[3].equals(tipoEmpleado)){
                            bfw.write(line);
                     }
                    } catch (Exception e) {
                        
                    }
                   
                }
                
            }

            raf.close();
            bfr.close();
            bfw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        App a=new App();
        a.insertarDatos("empleados.csv");
        a.formatoHerencia();
        a.crearIndice();
        a.obtenerInfoDepartamento("Recursos Humanos");
    }
}
