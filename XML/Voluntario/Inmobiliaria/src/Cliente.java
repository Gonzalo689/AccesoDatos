import java.util.LinkedList;
import java.util.List;

public class Cliente {
    private String nombre;
    private Tipo tipo;    
    private List<Integer> propiedades;

    public Cliente() {
        propiedades = new LinkedList<Integer>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int sizePropiedades() {
        return propiedades.size();
    }
    public void addPropiedad(int propiedad) {
        this.propiedades.add(propiedad);
    }

    public int getPropiedad(int i) {
        return propiedades.get(i);
    }
    public boolean contaisPropiedad(int propiedad) {
        return propiedades.contains(propiedad);
    }

    @Override
    public boolean equals(Object obj) {
        Cliente cliente = (Cliente) obj;
        if (!this.nombre.equals(cliente.nombre))
            return false;
        if (this.tipo != cliente.getTipo())
            return false;  
        return false;
    }
    


    @Override
    public String toString() {
        return "Cliente [nombre=" + nombre + ", tipo=" + tipo + ", Propiedad_id=" + propiedades + "]";
    }

    

    
    

}   
