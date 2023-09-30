
package com.mycompany.plficheros;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Gonzalo
 */
public class PlFicheros {
    
    public static Scanner text = new Scanner(System.in);
    public static Scanner num = new Scanner(System.in);
    public static Scanner doble = new Scanner(System.in);
    public static LinkedList<Fichero> ficheros = new LinkedList<>();
    public static LinkedList<Autor> autores = new LinkedList<>();
    

    public static String sacarNombre(String nombreCompleto) {
        String[] nombreC = nombreCompleto.split("\\.");
        int i = 0;
        String nombreProv = "";
        if (nombreC.length >= 2) {
            do {
                nombreProv += nombreC[i];
                nombreProv += ".";
                i++;
            } while (i < nombreC.length - 1);
            
            return nombreProv;
        } else 
            return null;
    }

    public static String sacarExtension(String nombreCompleto) {
        String[] nombreC = nombreCompleto.split("\\.");
        if (nombreC.length >= 2)
            return nombreC[nombreC.length - 1];
        else 
            return null;
    }
    
    public static Autor crearAutor(){
        System.out.println("Creando un autor....");
        System.out.println("DNI del autor");
        String dni = text.nextLine().toUpperCase();
        System.out.println("Nombre del autor");
        String nombre = text.nextLine();
        Autor autorr = new Autor(dni, nombre);
        if (autores.contains(autorr)) {
            System.out.println("Ese autor ya esta en nuestra lista");
            return autorr;
        }
        autores.add(autorr);
        return autorr;
    }
    
    public static Autor aniadirAutor(){
        boolean fl = false;
        if (autores.isEmpty()) 
            return crearAutor();
        do { 
            System.out.println("------Lista de autores-----");
            for (Autor autor : autores) 
                System.out.println(autor);
            
            System.out.println("Alguno de estos autores es el autor del Fichero que se quiere añadir S/N");
            String resp = text.nextLine().toLowerCase();

            if (resp.equals("s")|| resp.equals("si")) {
                System.out.println("indique el DNI del autor");
                String dni = text.nextLine().toUpperCase();
                for (Autor autor : autores) 
                    if (autor.getDni().equals(dni)) 
                        return autor;
                System.out.println("Ese DNI no coindice con ninguno de la lista");
                System.out.println("Intentelo de nuevo, asegurese de insertar el DNI correctamente\n");
            }else
                fl= true;
        } while (!fl );
        
        return crearAutor();
        
    }

    public static void aniadirFichero(int idF) {
        System.out.println("Añadiendo Fichero...");
        System.out.print("Nombre del fichero completo: ");
        String nombreCompleto = text.nextLine();
        if (sacarNombre(nombreCompleto) != null) {
            System.out.print("Tamaño del fichero: ");
            double tamano = doble.nextDouble();
            ficheros.add(new Fichero(idF, sacarNombre(nombreCompleto), sacarExtension(nombreCompleto), tamano, aniadirAutor()));
        } else 
            System.out.println("Nombre del fichero incorrecto ");
    }

    public static void modificarFichero() {
        System.out.println("Modificando Fichero...");
        Fichero fichM = null;
        int id = -1;
        do{
        System.out.print("ID del fichero que deseas modificar: ");
        try {
            id = num.nextInt();
        } catch (Exception e) {
            num = new Scanner(System.in);
            System.out.println("Error al poner el ID");
        }
        }while(id == -1);
        
        for (int i = 0; i < ficheros.size(); i++) 
            if (id == ficheros.get(i).getId()) 
                fichM = ficheros.get(i);

        if (fichM != null || id != -1) {
            System.out.print("Nuevo nombre completo: ");
            String nombreCom = text.nextLine();
            if (sacarNombre(nombreCom) != null) {
                fichM.setNombre(sacarNombre(nombreCom));
                fichM.setExtension(sacarExtension(nombreCom));

                System.out.print("Nuevo tamaño: ");
                fichM.setTamano(doble.nextDouble());
            } else 
                System.out.println("Fallo al poner el nombre y su extensión");
        }
    }

    public static void buscarFichero() {
        int cont = 0;
        double sumaT = 0;
        System.out.print("Consulta: ");
        String consult = text.nextLine();
        
        for (Fichero fichero : ficheros) 
            if (fichero.getNombre().equals((consult + ".")) || fichero.getExtension().equals(consult)){
                cont++;
                sumaT += fichero.getTamano();
            }
        
        System.out.print("Encontrados: " + cont + "\nSuma total de todos los ficheros: "+ sumaT + "\n");
        
        for (Fichero fichero : ficheros) 
            if (fichero.getNombre().equals((consult + ".")) || fichero.getExtension().equals(consult)) 
                System.out.println(fichero.toString());
        
    }

    public static void borrarFichero() {
        int i = 0;
        boolean fl = false;
        if (!ficheros.isEmpty()) {
            System.out.println("Ficheros disponibles");
            for (Fichero fc : ficheros) 
                System.out.println(fc.toString());
            
            System.out.print("Indique el id del fichero que deseas eliminar: ");
            int idF = num.nextInt();
            while (i < ficheros.size() || fl != true )                 
                if (idF == ficheros.get(i).getId()) {
                    ficheros.remove(i);
                    fl = true;
                }else
                    i++;
        }else
            System.out.println("No hay ficheros para borrar");
    }
   
    
    public static void listarExtensionTodos(){
        LinkedList<Fichero> ficherosOrd = new LinkedList<>();
        LinkedList<Double> tamanos = new LinkedList<>();
        LinkedList<Integer> contador = new LinkedList<>(); 
        
        for (Fichero fichero : ficheros) {
            boolean encontrado = false;
            for (int j = 0; j < ficherosOrd.size() && !encontrado ; j++) 
                if (ficherosOrd.get(j).getExtension().equals(fichero.getExtension())) {
                    tamanos.set(j, tamanos.get(j) + fichero.getTamano());
                    contador.set(j, contador.get(j) + 1); 
                    encontrado = true;
                }
            if (!encontrado) {
                ficherosOrd.add(fichero);
                tamanos.add(fichero.getTamano());
                contador.add(1); 
            }
        }
        for (int i = 0; i < ficherosOrd.size() ; i++) 
            System.out.println("+ Extension: " + ficherosOrd.get(i).getExtension() + "\n -Tamaño total : " + tamanos.get(i) 
                    + "\n  +Total de Ficheros con esa extension: " + contador.get(i));
    }
    public static void listarExtensionEspecifico(){
        System.out.println("Indique la extensión");
        String ext = text.nextLine();
        int cont = 0;
        double total=0;
        for (Fichero fichero : ficheros) {
            if (fichero.getExtension().equals(ext)) {
                cont++;
                total += fichero.getTamano();
                System.out.println(fichero);
            }
        }
        if (cont == 0) 
            System.out.println("No hay ficheros con la extension " + ext);
        else
            System.out.println("El tamaño total de los ficheros con la extension " + ext + " es " + total);
    }
    
    // Ordena primero por tamaño si son iguales los iguala por el nombre y si son iguales nos iguala por la extensión
    public static void listarOrdenTamano(){
        Fichero aux ;
        
        for (int i = 0; i < ficheros.size() - 1 ; i++) {
            for (int j = 0; j < ficheros.size() - 1 -i ; j++) {
                if (ficheros.get(j + 1).getTamano() > ficheros.get(j).getTamano()) {
                    aux = ficheros.get(j);
                    ficheros.set(j, ficheros.get(j+1));
                    ficheros.set(j+1, aux);
                }
                if(ficheros.get(j + 1).getTamano() == ficheros.get(j).getTamano()){
                    int o = ficheros.get(j+1).compareTo(ficheros.get(j));
                    if (o != 1) {
                        aux = ficheros.get(j);
                        ficheros.set(j, ficheros.get(j+1));
                        ficheros.set(j+1, aux);
                    }
                }
            }
        }
        for (Fichero fichero : ficheros) {
            System.out.println(fichero);
        }
    }
    
    public static void main(String[] args) {
        // autores
        autores.add(new Autor("A", "aq"));
        autores.add(new Autor("B", "AS"));
        autores.add(new Autor("C", "BC"));

        // ficheros
        ficheros.add(new Fichero(0, "si.", "b", 5, new Autor("A", "aq")));
        ficheros.add(new Fichero(1, "ac.", "v", 10, new Autor("A", "aq")));
        ficheros.add(new Fichero(2, "aa.", "a", 10, new Autor("A", "aq")));
        ficheros.add(new Fichero(3, "b.", "x", 1, new Autor("B", "AS")));
        ficheros.add(new Fichero(4, "c.", "b", 11, new Autor("B", "AS")));
        ficheros.add(new Fichero(5, "d.", "a", 3, new Autor("C", "BC")));
        
        int op = -1;
        int idF = 0;
        
        do {
            do {
                System.out.println("¿Qué te gustaría hacer?");
                System.out.println("1. Añadir fichero");
                System.out.println("2. Modificar fichero");
                System.out.println("3. Buscar fichero");
                System.out.println("4. Listar ficheros");
                System.out.println("5. Borrar fichero");
                System.out.println("6. Salir");
                try {
                    op = num.nextInt();
                } catch (Exception e) {
                    System.out.println("Esa opcion no es correcta intentelo de nuevo");
                     num = new Scanner(System.in);
                }
            } while (op > 6 || op < 0);

            switch (op) {
                case 1:
                    aniadirFichero(idF);
                    idF++;
                    break;
                case 2:
                    modificarFichero();
                    break;
                case 3:
                    buscarFichero();
                    break;
                case 4:
                        if (ficheros.isEmpty()) {
                            System.out.println( "No hay ficheros que se puedan listar" );
                            break;
                        }
                        System.out.println("¿Cómo desea listar los ficheros?");
                        System.out.println("1.Todos Los ficheros");
                        System.out.println("2.Todos los ficheros por extensión");
                        System.out.println("3.Ficheros con una extensión especifica");
                        System.out.println("4.ficheros por orden de tamaños");
                        System.out.println("5.ficheros por orden Nombre");
                        System.out.println("6.ficheros por orden Extension");
                        System.out.println("7.ficheros por orden de id");
                        
                        try {
                            op = num.nextInt();
                        } catch (Exception e) {
                            op = -1;
                            num = new Scanner(System.in);
                        }
                        switch (op) {
                            case 1:
                                for (Fichero fc : ficheros) 
                                    System.out.println(fc.toString());
                                break;
                            case 2:
                                listarExtensionTodos();
                                break;    
                            case 3:
                                listarExtensionEspecifico();
                                break;
                            case 4:
                                listarOrdenTamano();
                                break;
                            case 5:
                                Collections.sort(ficheros); ;
                                for (Fichero fc : ficheros) 
                                    System.out.println(fc.toString());
                                break;  
                            case 6:
                                ficheros.sort(new OrdenarExtension());
                                for (Fichero fc : ficheros) 
                                    System.out.println(fc.toString());
                                break;  
                            case 7:
                                ficheros.sort(new OrdenarID());
                                for (Fichero fc : ficheros) 
                                    System.out.println(fc.toString());
                                break;     
                            default:
                                System.out.println("Error en marcar la opción");    
                                break;
                        }
                    
                    break;
                case 5:
                    borrarFichero();
                    break;
                case 6:
                    System.out.println("Adios :)");
                    break;

            }
            if (op != 6) {
                
                System.out.println("Pulse cualquer tecla para continuar");
                System.out.println("----------------------------");
                String c = text.nextLine();
                System.out.println(c);
            }

        } while (op != 6);
    }
}
