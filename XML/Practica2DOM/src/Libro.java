
public class Libro {
    private String id;
    private String autor;
    private String titulo;
    private String genero;
    private double precio;
    private String fechaPubli;
    private String descripcion;

    public Libro() {
        
    }

    public Libro(String id, String autor, String titulo, String genero, double precio, String fechaPubli, String descripcion) {
        this.id = id;
        this.autor = autor;
        this.titulo = titulo;
        this.genero = genero;
        this.precio = precio;
        this.fechaPubli = fechaPubli;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getFechaPubli() {
        return fechaPubli;
    }

    public void setFechaPubli(String fechaPubli) {
        this.fechaPubli = fechaPubli;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    @Override
    public String toString(){
        return "Libro{" + "id=" + id + ", autor=" + autor + ", titulo=" + titulo + ", genero=" + genero + ", precio=" + precio + ", fechaPubli=" + fechaPubli +"}\n";
    }
    
    
}
