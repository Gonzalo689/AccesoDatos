import java.util.LinkedList;

public class Proveedor {
    private int id;
    private String nombre; 
    private String pais;
    private LinkedList<Producto> productos;

    public Proveedor(int id, String nombre, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
        this.productos = new LinkedList<Producto>();
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LinkedList<Producto> getProductos() {
        return productos;
    }
    public void aniadirProducto(Producto p){
        this.productos.add(p);
    }

    public void setProductos(LinkedList<Producto> productos) {
        this.productos = productos;
    }

}
