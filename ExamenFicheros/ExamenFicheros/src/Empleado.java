public class Empleado {
    private String nombre ;
    private int numeroEmpl;
    private int salario;
    
    public Empleado(String nombre, int numeroEmpl, int salario){
        this.nombre = nombre;
        this.numeroEmpl = numeroEmpl;
        this.salario = salario;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getNumeroEmpl() {
        return numeroEmpl;
    }
    public void setNumeroEmpl(int numeroEmpl) {
        this.numeroEmpl = numeroEmpl;
    }
    public int getSalario() {
        return salario;
    }
    public void setSalario(int salario) {
        this.salario = salario;
    }

    public String toStringCSV(){
        return nombre + "," + numeroEmpl + "," + salario;
    }

    public String herenciaToString(){
        return "Nombre: " + nombre + ", Numero de empleado: " + numeroEmpl + ", Salario: " + salario;
    }

    @Override
    public String toString() {
        return "Empleado[" + herenciaToString()  +  "]";
    }
}
