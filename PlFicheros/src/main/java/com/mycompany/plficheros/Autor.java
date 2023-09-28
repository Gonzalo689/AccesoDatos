/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.plficheros;

import java.util.Objects;

/**
 *
 * @author Gonzalo
 */
public class Autor {
    private String dni;
    private String nombre;

    public Autor(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Autor{" + "dni=" + dni + ", nombre=" + nombre + '}';
    }

    @Override
    public boolean equals(Object obj) {
        Autor autorr = (Autor) obj;
        return dni.equals(autorr.dni);
    }
    
    
    
}
