public class Vehiculo {
    private int numSerie ;
    private String modelo;
    private String anio;
    private String color;

    public Vehiculo(int numSerie, String modelo, String anio, String color) {
        this.numSerie = numSerie;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
    }

    public int getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(int numSerie) {
        this.numSerie = numSerie;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Vehiculo [numSerie=" + numSerie + ", modelo=" + modelo + ", anio=" + anio + ", color=" + color + "]";
    }
    

}
