import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

public class App {
    private LinkedList<Propietario> propietarios = new LinkedList<>();
    private LinkedList<Vehiculo> vehiculosSinPropietario = new LinkedList<>();
    private Connection c;
    private PreparedStatement s;
    /**
     * Método que lee el xml y lo crea las listas con los datos que se encuentre
     */
    public void xml_Java() {
        // Propietario
        int id = -1;
        String nombre = null;
        String direccion = null;
        String telefono = null;
        // Vehiculo
        int numSerie = -1;
        String modelo = null;
        String anio = null;
        String color = null;
        int propietarioPK = -1;
        try {

            BufferedReader br = new BufferedReader(new FileReader("vehiculos.xml"));
            String linea;
            boolean objeto = false;
            while ((linea = br.readLine()) != null) {
                
                linea = linea.trim();
                if (linea.equals("<propietario>")||linea.equals("<vehiculo>")) 
                    objeto = true;
                if (linea.equals("</propietario>")||linea.equals("</vehiculo>")) 
                    objeto = false;
                String s = sacarDato(linea, objeto);
                
                String[] datos = s.split(";");
                if (s.isEmpty() && id != -1 ) {
                    Propietario p =  new Propietario(id, nombre, direccion, telefono);
                    propietarios.add(p);
                    id = -1;
                    nombre = null;
                    direccion = null;
                    telefono = null;
                   // insertarPropietario(p);
                    
                    
                }
                if (s.isEmpty() && numSerie != -1) {
                    Vehiculo v = new Vehiculo(numSerie, modelo, anio, color);
                    if (propietarioPK != -1) {
                        for (Propietario prop : propietarios) {
                            if (prop.getId() == propietarioPK) {
                                prop.aniadirVehiculo(v);
                                //insertarVehiculo(v, propietarioPK);
                            }
                        }
                    }else
                        vehiculosSinPropietario.add(v);
                       // insertarVehiculo(v, -1);
                    numSerie = -1;
                    modelo = null;
                    anio = null;
                    color = null;
                    propietarioPK = -1;
                }
                
                switch (datos[0]) {
                    case "id":
                        id = Integer.parseInt(datos[1]);
                        break;
                    case "nombre":
                        nombre = datos[1];
                        break;
                    case "direccion":
                        direccion = datos[1];
                        break;
                    case "telefono":
                        telefono = datos[1];
                        break;
                    case "numero_serie":
                        numSerie = Integer.parseInt(datos[1]);
                        break;
                    case "modelo":
                        modelo = datos[1];
                        break;
                    case "anio":
                        anio = datos[1];
                        break;
                    case "color":
                        color = datos[1];
                        break;
                    case "propietario_id":
                        propietarioPK = Integer.parseInt(datos[1]);    
                    default:
                        break;
                }
                
            }
            for (Propietario prop : propietarios) {
                System.out.println(prop.toString());
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Metodo que saca los datos de la linea del xml
     * @param lineaxml
     * @param p
     * @return
     */
    public String sacarDato(String lineaxml, boolean p) {
        if(p){
            if(!lineaxml.equals("<propietario>") && !lineaxml.equals("<vehiculo>")) {
                String[] separacion = lineaxml.split("<");
                String[] separacion2 = separacion[1].split(">");
                return separacion2[0]+";"+separacion2[1];
            }
        }
        return "";
    }


    /**
     * Metodo que crea la conexion con la base de datos
     */
    public void crearConexion()  {
        String db = "base_de_datos";
		String host = "localhost";
		String port = "3306";
		String urlConnection = "jdbc:mysql://"+host+":"+port+"/"+db;
		String user = "root";
		String pwd = "infobbdd";
        
        try{
            c= DriverManager.getConnection(urlConnection, user, pwd);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Metodo que cierra la conextion con la base de datos
     */
    public void cerrarConexion() {
        try {
            if (c!=null) 
                c.close();
            if (s!=null) 
                s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Método que crea las tablas de la base de datos
     */
    public void crearTablas() {
        try {
            c.setAutoCommit(false);
            s = c.prepareStatement("DROP TABLE IF EXISTS Vehiculos");
            s.execute();
            s = c.prepareStatement("DROP TABLE IF EXISTS Propietarios");
            s.execute();
            
            s = c.prepareStatement("CREATE TABLE Propietarios (id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(255), direccion VARCHAR(255), telefono VARCHAR(255))");
            s.execute();
            s = c.prepareStatement("CREATE TABLE Vehiculos (numero_serie INT PRIMARY KEY , modelo VARCHAR(255), anio VARCHAR(255), color VARCHAR(255), propietario_id INT, " +
            "FOREIGN KEY (propietario_id) REFERENCES Propietarios(id))");
            s.execute();
            
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                c.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    /**
     * Método que inserta las listas de propietarios a la base de datos
     * @param p propietario
     */
    public void insertarPropietario(Propietario p) {
        try {
            c.setAutoCommit(false);
            s = c.prepareStatement("INSERT INTO Propietarios (nombre, direccion, telefono) VALUES (?, ?, ?)");
            s.setString(1, p.getNombre());
            s.setString(2, p.getDireccion());
            s.setString(3, p.getTelefono());
            s.executeUpdate();
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Método que sirve para insertar un vehiculo en la base de datos y si no tiene propietario se inserta null
     * @param v vehiculo en cuestion
     * @param prop propietario del vehiculo
     */
    public void insertarVehiculo(Vehiculo v, int prop) {
        try {
            c.setAutoCommit(false);
            if (prop == -1) {
                s = c.prepareStatement("INSERT INTO Vehiculos (numero_serie, modelo, anio, color, propietario_id) VALUES (?, ?, ?, ?, null)");
                
            }else{
                s = c.prepareStatement("INSERT INTO Vehiculos (numero_serie, modelo, anio, color, propietario_id) VALUES (?, ?, ?, ?, ?)");
                s.setInt(5, prop);
            }
            s.setInt(1, v.getNumSerie());
            s.setString(2, v.getModelo());
            s.setString(3, v.getAnio());
            s.setString(4, v.getColor());
            s.executeUpdate();
            c.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Método que inserta las listas a la base de datos
     */
    public void java_BBDD() {
        for (Propietario propi : propietarios) {
            insertarPropietario(propi);
        }
        for (Propietario propi : propietarios) {
            for (int i = 0; i < propi.sizeVehiculos(); i++) {
                insertarVehiculo(propi.getVehiculo(i), propi.getId());
            }
        }
        for (Vehiculo v : vehiculosSinPropietario) {
            insertarVehiculo(v,-1);
        }

    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.crearConexion();
        app.crearTablas();
        app.xml_Java();
        app.java_BBDD();
        app.cerrarConexion();
    }
}
