public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String moneda;

    public Producto(int id, String nombre, double precio, String moneda) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.moneda = moneda;
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
    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public String getMoneda() {
        return moneda;
    }
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

       
    
    
    
    
    
}
