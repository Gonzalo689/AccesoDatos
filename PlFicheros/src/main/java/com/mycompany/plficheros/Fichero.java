
package com.mycompany.plficheros;

/**
 *
 * @author Gonzalo
 */
public class Fichero implements Comparable<Fichero>{

    private int id;
    private String nombre;
    private String extension;
    private double tamano;
    private Autor autor;
    

    public Fichero(int id, String nombre, String extension, double tamano, Autor autor) {
        this.id = id;
        this.nombre = nombre;
        this.extension = extension;
        this.tamano = tamano;
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public double getTamano() {
        return tamano;
    }

    public void setTamano(double tamano) {
        this.tamano = tamano;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    
    @Override
    public String toString() {
        if (id < 10) 
            return "+Fichero 0" + id + ": " + nombre  + extension + "\n -Tamaño: " + tamano + "kb\n  +" + autor;
        else
            return "+Fichero " + id + ": " + nombre  + extension + "\n -Tamaño: " + tamano + "kb\n  +" + autor;

    }
    
    
    
    @Override
    public int compareTo(Fichero f) {
        if (this.nombre.compareToIgnoreCase(f.getNombre()) == 0) {
            return this.extension.compareToIgnoreCase(f.getExtension());
        }
        return this.nombre.compareToIgnoreCase(f.getNombre());
    }

}
