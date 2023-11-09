public class Gerente extends Empleado{
    private String departamento;

    public Gerente(String nombre, int numeroEmpl, int salario, String departamento) {
        super(nombre, numeroEmpl, salario);
        this.departamento = departamento;
        
    }
    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toStringCSV() {
        
        return super.toStringCSV() + "," + departamento;
    }
    
    @Override
    public String herenciaToString() {
        
        return super.herenciaToString() + ", Departamento: " + departamento;
    }

    @Override
    public String toString() {
        
        return "Gerente[" + this.herenciaToString() + "]";
    }
    
}