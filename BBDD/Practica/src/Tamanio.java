public class Tamanio {
    private int id;
    private int cod;
    private int clasificacion;

    public Tamanio(int id, int cod, int clasificacion) {
        this.id = id;
        this.cod = cod;
        this.clasificacion = clasificacion;
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

    public int getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    @Override
    public String toString() {
        return "Tamanio [id=" + id + ", cod=" + cod + ", clasificacion=" + clasificacion + "]";
    }

}
