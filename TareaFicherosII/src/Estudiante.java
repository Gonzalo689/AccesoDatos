
public class Estudiante{

    private String nombre;
    private String especialidad;
    private int nota;

    public Estudiante(String nombre, String especialidad, int nota){
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.nota = nota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
    
}