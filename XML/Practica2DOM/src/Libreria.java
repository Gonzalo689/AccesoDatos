import java.util.LinkedList;
import java.util.List;

public class Libreria {
    private int id;
    private String calle;
    private List<Libro> libros;

    public Libreria() {
        this.libros = new LinkedList<>();
    }

    public Libreria(int id, String calle) {
        this.id = id;
        this.calle = calle;
        this.libros = new LinkedList<>();
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }
    public void addLibro(Libro l){
        this.libros.add(l);
    }
    public boolean contains(Libro l){
        return this.libros.contains(l);
    }
    public Libro getLibroIndex(int i){
        return this.libros.get(i);
    }
    public int sizeLibros() {
        return this.libros.size();
    }

    @Override
    public String toString() {
        return "Libreria{" + "id=" + id + ", calle=" + calle + ", libros=" + libros + '}';
    }
    
    
}
