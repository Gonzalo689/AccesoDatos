public class Color {
    private int id;
    private int cod;
    private String nombre;

    public Color(int id, int cod, String nombre) {
        this.id = id;
        this.cod = cod;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "color [id=" + id + ", cod=" + cod + ", nombre=" + nombre + "]";
    }

}
