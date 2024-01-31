import java.util.LinkedList;

public class Propietario {

    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    LinkedList<Vehiculo> vehiculos ;

    public Propietario(int id, String nombre, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.vehiculos = new LinkedList<>();
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void aniadirVehiculo(Vehiculo v) {
        this.vehiculos.add(v);
    }
    public Vehiculo getVehiculo(int i) {
        return this.vehiculos.get(i);
    }
    public int sizeVehiculos() {
        return this.vehiculos.size();
    }

    @Override
    public String toString() {
        return "Propietario [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono
                + ", vehiculos=" + vehiculos + "]";
    }

    
    
}
