import java.util.LinkedList;
import java.util.List;

public class Inmobiliaria {
    private String nombre;
    private String direccion;
    private String ciudad;
    private List<Cliente> clientes;
    private LinkedList<Propiedad> propiedades;

    
    public Inmobiliaria() {
        this.clientes= new LinkedList<Cliente>();
        this.propiedades = new LinkedList<Propiedad>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public int sizeClientes(){
        return this.clientes.size();
    }

    public void addCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public Cliente getCliente(int i) {
        return clientes.get(i);
    }

    public int sizePropiedades() {
        return propiedades.size(); 
    }
    public void addPropiedad(Propiedad propiedad){
        this.propiedades.add(propiedad);
    }
    public Propiedad getPropiedad(int i){
        return propiedades.get(i);
    }

    @Override
    public String toString() {
        return "Inmobiliaria [nombre=" + nombre + ", direccion=" + direccion + ", ciudad=" + ciudad
                + ", clientes=" + clientes + ", propiedades=" + propiedades + "]";
    }

      
}
