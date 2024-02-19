public class Propiedad {
    private int id;
    private String tipo;
    private String direccion;
    private int habitaciones;
    private int banos;
    private int precio;
    public Propiedad() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }

    public int getBanos() {
        return banos;
    }

    public void setBanos(int banos) {
        this.banos = banos;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
   
    @Override
    public String toString() {
        return "Propiedad [id=" + id + ", tipo=" + tipo + ", direccion=" + direccion + ", habitaciones=" + habitaciones
                + ", banos=" + banos + ", precio=" + precio + "]";
    }

    

    
    
}
