public class Director extends Gerente{
    private String areaResp;
    public Director(String nombre, int numeroEmpl, int salario, String departamento, String areaResp) {
        super(nombre, numeroEmpl, salario, departamento);
        this.areaResp = areaResp;
    }
    public String getAreaResp() {
        return areaResp;
    }
    public void setAreaResp(String areaResp) {
        this.areaResp = areaResp;
    }
    @Override
    public String toStringCSV() {
        
        return super.toStringCSV() + "," +areaResp;
    }
    @Override
    public String herenciaToString() {
        
        return super.herenciaToString() + ", Area Responsable: "  + areaResp;
    }
    @Override
    public String toString() {
        return "Director[" + super.toString() +"]" ;
    }
    
}
