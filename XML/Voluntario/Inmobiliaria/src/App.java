
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class App extends DefaultHandler {
    public List<Inmobiliaria> inmobiliarias;
    private Inmobiliaria inmobiliaria;
    private Cliente cliente;
    private Propiedad propiedad;
    private LinkedList<String> elementos;
    private Connection c;
    private PreparedStatement s;
    

    public App (){
        inmobiliarias = new LinkedList<>();
        inmobiliaria = new Inmobiliaria();
        cliente = new Cliente();
        propiedad = new Propiedad();
        elementos = new LinkedList<>();
    }

    @Override
    public void startDocument(){}

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        // Inicializar nueva inmobiliaria
        if (qName.trim().equalsIgnoreCase("inmobiliaria")) {
            inmobiliaria = new Inmobiliaria();
        }
        // Inicializar nuevo Cliente
        if (qName.trim().equalsIgnoreCase("cliente")) {
            cliente = new Cliente();
        }
        // Inicializar nueva Propiedad
        if (qName.trim().equalsIgnoreCase("propiedad")) {
            propiedad = new Propiedad();
        }
        
        // Guardar el nombre de la etiqueta en la lista
        elementos.add(qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String text3 = "";
        String text = new String(ch, start, length);
        //System.out.println(dato);
        if(text.trim().length()>0){
            
            String[] text2 = text.split("\n");
            for (String  c : text2) {
                if (!c.isEmpty()) {
                    text3 += c.trim() +" ";
                }
            }
            datosInmobiliaria(elementos.getLast(),text3);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        if (qName.trim().equalsIgnoreCase("inmobiliaria")) {
            inmobiliarias.add(inmobiliaria);
        }
        
        if (qName.trim().equalsIgnoreCase("cliente")) {
            inmobiliaria.addCliente(cliente);
        }
        
        if (qName.trim().equalsIgnoreCase("propiedad")) {
            inmobiliaria.addPropiedad(propiedad);
        }
        elementos.removeLast();
    }

    @Override
    public void endDocument() {
        try {
            // Abre una conexión a la base de datos
            crearConexion();
            crearTablas();
            // Inserta los datos en la base de datos
            for (Inmobiliaria inmobiliaria : inmobiliarias) {
                insertarInmobiliaria(inmobiliaria);
            }
            
            // Cierra la conexión a la base de datos
            cerrarConexion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void datosInmobiliaria(String datoNombre,String valor){
        String elemtPadre = elementos.get(elementos.size()-2);
        System.out.println(datoNombre + ": " + valor);
		switch (datoNombre.trim().toLowerCase()) {
        case "nombre":
            if(elemtPadre.trim().equalsIgnoreCase("inmobiliaria"))
                inmobiliaria.setNombre(valor);
            else
                cliente.setNombre(valor);
            break;
        case "direccion":
            if(elemtPadre.trim().equalsIgnoreCase("inmobiliaria"))
                inmobiliaria.setDireccion(valor);
            else
                propiedad.setDireccion(valor);   
            break;
        case "ciudad":
            inmobiliaria.setCiudad(valor);    
            break;
        case "tipo":
            if(elemtPadre.trim().equalsIgnoreCase("propiedad"))
                propiedad.setTipo(valor);
            else{
                if (valor.trim().equalsIgnoreCase("comprador")) {
                    cliente.setTipo(Tipo.COMPRADOR);
                }else
                    cliente.setTipo(Tipo.VENDEDOR);
            }
            break;
        case "propiedad_id":
            cliente.addPropiedad(Integer.valueOf(valor.trim()));
            break;
        case "id":
            propiedad.setId(Integer.valueOf(valor.trim()));
            break;
        case "habitaciones":
            propiedad.setHabitaciones(Integer.valueOf(valor.trim()));
            break;
        case "baños":
            propiedad.setBanos(Integer.valueOf(valor.trim())); 
            break;
        case "precio":
            propiedad.setPrecio(Integer.valueOf(valor.trim()));
            break;
		}
	}
    

    public void sacarPropiedades(Cliente c){
        for (Inmobiliaria inmo : inmobiliarias) {
            for (int j = 0; j < inmo.sizePropiedades(); j++) {
                if(c.contaisPropiedad(inmo.getPropiedad(j).getId()) && c.getTipo() == Tipo.VENDEDOR){
                    System.out.println(inmo.getPropiedad(j).toString());
                }
            }
        }
    }
    public void sacarPropiedades2(String  nombre){
        for (Inmobiliaria inmo : inmobiliarias) {
            for (int j = 0; j < inmo.sizePropiedades(); j++) {
               for (int i = 0; i < inmo.sizeClientes(); i++) {
                    if (inmo.getCliente(i).getNombre().trim().equalsIgnoreCase(nombre.trim()) && inmo.getCliente(i).getTipo() == Tipo.VENDEDOR && 
                        inmo.getCliente(i).contaisPropiedad(inmo.getPropiedad(j).getId())) {
                        System.out.println("--------------------------------------------------");
                        System.out.println(inmo.getPropiedad(j).toString());
                    }
               }
            }
        }
    }

    public void crearConexion()  {
        String db = "inmobiliaria";
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

    public void crearTablas() {
        try {
            c.setAutoCommit(false);
            s = c.prepareStatement("DROP TABLE IF EXISTS Propiedades");
            s.execute();
            s = c.prepareStatement("DROP TABLE IF EXISTS Vendedores");
            s.execute();
            s = c.prepareStatement("DROP TABLE IF EXISTS Compradores");
            s.execute();
            s = c.prepareStatement("DROP TABLE IF EXISTS Inmobiliarias");
            s.execute();
            
            s = c.prepareStatement("CREATE TABLE Inmobiliarias (" + 
                            "    id INT PRIMARY KEY AUTO_INCREMENT," +
                            "    nombre VARCHAR(255) NOT NULL," +
                            "    direccion VARCHAR(255) NOT NULL," +
                            "    ciudad VARCHAR(255) NOT NULL" + 
                            ");");
            s.execute();
            s = c.prepareStatement("CREATE TABLE Vendedores (" + 
                            "    id INT PRIMARY KEY AUTO_INCREMENT," + 
                            "    nombre VARCHAR(255) NOT NULL," + 
                            "    inmobiliaria_id INT," + 
                            "    FOREIGN KEY (inmobiliaria_id) REFERENCES Inmobiliarias(id)" + 
                            ");");
            s.execute();
            s = c.prepareStatement("CREATE TABLE Compradores (" +
                            "    id INT PRIMARY KEY AUTO_INCREMENT," +
                            "    nombre VARCHAR(255) NOT NULL," + 
                            "    inmobiliaria_id INT," + 
                            "    FOREIGN KEY (inmobiliaria_id) REFERENCES Inmobiliarias(id)" +
                            ");");
            s.execute();
            
            
            s = c.prepareStatement("CREATE TABLE Propiedades ("+
                " id INT PRIMARY KEY," +
                " tipo VARCHAR(50) NOT NULL," + 
                " direccion VARCHAR(255) NOT NULL," +
                " habitaciones INT," + 
                " banos INT," +
                " precio DECIMAL(10, 2)," +
                " propietario_id INT," +
                " comprador_id INT," +
                " inmobiliaria_id INT," + 
                " FOREIGN KEY (inmobiliaria_id) REFERENCES Inmobiliarias(id)," + 
                " FOREIGN KEY (propietario_id) REFERENCES Vendedores(id)," + 
                " FOREIGN KEY (comprador_id) REFERENCES Compradores(id)" + 
                ");");
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
    

    public void insertarInmobiliaria(Inmobiliaria inmobiliaria) {
        try {
            c.setAutoCommit(false);
            // Inserta la inmobiliaria en la tabla 'Inmobiliarias'
            s = c.prepareStatement("INSERT INTO Inmobiliarias (nombre, direccion, ciudad) VALUES (?, ?, ?)");
            s.setString(1, inmobiliaria.getNombre());
            s.setString(2, inmobiliaria.getDireccion());
            s.setString(3, inmobiliaria.getCiudad());
            s.executeUpdate();
            
            // Obtén el ID de la inmobiliaria recién insertada
            int inmobiliariaId = obtenerUltimoIdInsertado();

            // Inserta los clientes de la inmobiliaria en las tablas 'Compradores' o 'Vendedores'
            for (int i = 0; i < inmobiliaria.sizeClientes(); i++) {
                insertarCliente(inmobiliaria.getCliente(i), inmobiliariaId);
            }
    
            // Inserta las propiedades de la inmobiliaria en la tabla 'Propiedades'
            for (int i = 0; i < inmobiliaria.sizePropiedades(); i++) {
                boolean propiedadAsignada = false;
                Propiedad prop = inmobiliaria.getPropiedad(i);
                for (int j = 0; j < inmobiliaria.sizeClientes(); j++) {
                    Cliente client = inmobiliaria.getCliente(j);
                    if(inmobiliaria.getCliente(j).contaisPropiedad(prop.getId())){
                        if( client.getTipo() == Tipo.COMPRADOR ){
                            int propietario = getClientID(client.getNombre());
                            if (!verificarPropiedad(prop))
                                insertarPropiedad(prop, inmobiliariaId, propietario, 0);
                            else
                                agregarPropietarioOVendedorFaltante(prop, propietario, 0);
                            propiedadAsignada = true;
                        }
                        if (client.getTipo() == Tipo.VENDEDOR) {
                            int vendedor = getClientID(client.getNombre());
                            if (!verificarPropiedad(prop))
                                insertarPropiedad(prop, inmobiliariaId, 0, vendedor);
                            else
                                agregarPropietarioOVendedorFaltante (prop, 0, vendedor);
                            propiedadAsignada = true;
                        }
                    }
                }
                if (!propiedadAsignada)
                    insertarPropiedad(prop, inmobiliariaId, 0 , 0);
            }
    
            
            
            c.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                c.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
    public boolean verificarPropiedad(Propiedad propiedad) throws SQLException {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM Propiedades WHERE id = ?");
            ps.setInt(1, propiedad.getId());
            ResultSet rs = ps.executeQuery();
           
            return rs.next();        
    }
    public void agregarPropietarioOVendedorFaltante(Propiedad propiedad, int propietarioId, int vendedorId) {
        try {
            // Consultar la propiedad en la base de datos para obtener su estado actual
            PreparedStatement consultaPropiedad = c.prepareStatement("SELECT propietario_id, comprador_id FROM Propiedades WHERE id = ?");
            consultaPropiedad.setInt(1, propiedad.getId());
            ResultSet resultado = consultaPropiedad.executeQuery();
            
            // Verificar si el propietario o el vendedor están ausentes
            if (resultado.next()) {
                int propietarioActual = resultado.getInt("propietario_id");
                int vendedorActual = resultado.getInt("comprador_id");
    
                // Verificar y realizar la actualización para agregar el propietario si está ausente
                if (propietarioActual == 0 && propietarioId != 0) {
                    PreparedStatement actualizarPropietario = c.prepareStatement("UPDATE Propiedades SET propietario_id = ? WHERE id = ?");
                    actualizarPropietario.setInt(1, propietarioId);
                    actualizarPropietario.setInt(2, propiedad.getId());
                    actualizarPropietario.executeUpdate();
                    System.out.println("Se ha agregado el propietario a la propiedad con ID: " + propiedad.getId());
                }
    
                // Verificar y realizar la actualización para agregar el vendedor si está ausente
                if (vendedorActual == 0 && vendedorId != 0) {
                    PreparedStatement actualizarVendedor = c.prepareStatement("UPDATE Propiedades SET comprador_id = ? WHERE id = ?");
                    actualizarVendedor.setInt(1, vendedorId);
                    actualizarVendedor.setInt(2, propiedad.getId());
                    actualizarVendedor.executeUpdate();
                    System.out.println("Se ha agregado el vendedor a la propiedad con ID: " + propiedad.getId());
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getClientID(String nombre) throws SQLException {
        int clientId = 0;
        s = c.prepareStatement("SELECT id FROM Compradores WHERE nombre = ?");
        s.setString(1, nombre);
        ResultSet rs = s.executeQuery();
    
        if (rs.next()) {
            clientId = rs.getInt(1);
        } else {
            s = c.prepareStatement("SELECT id FROM Vendedores WHERE nombre = ?");
            s.setString(1, nombre);
            rs = s.executeQuery();
    
            if (rs.next()) {
                clientId = rs.getInt(1);
            }
        }
    
        return clientId;
    }

    
    public void insertarPropiedad(Propiedad propiedad, int inmobiliariaId, int propietarioId, int compradorId) throws SQLException {
        // Inserta la propiedad en la tabla 'Propiedades'
        s = c.prepareStatement("INSERT INTO Propiedades (id, tipo, direccion, habitaciones, banos, precio, inmobiliaria_id, propietario_id, comprador_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        s.setInt(1, propiedad.getId());
        s.setString(2, propiedad.getTipo());
        s.setString(3, propiedad.getDireccion());
        s.setInt(4, propiedad.getHabitaciones());
        s.setInt(5, propiedad.getBanos());
        s.setInt(6, propiedad.getPrecio());
        s.setInt(7, inmobiliariaId);
        if (propietarioId == 0) {
            s.setNull(8, java.sql.Types.INTEGER );
        } else {
            s.setInt(8, propietarioId);
        }

        // Si compradorId es null, establece el valor de la columna como null
        if (compradorId == 0) {
            s.setNull(9, java.sql.Types.INTEGER);
        } else {
            s.setInt(9, compradorId);
        }   
        s.executeUpdate();
    }

    public void insertarCliente(Cliente cliente, int inmobiliariaId) throws SQLException {
        // Inserta el cliente en la tabla 'Compradores' o 'Vendedores' según su tipo
        if (cliente.getTipo() == Tipo.COMPRADOR) {
            System.out.println(cliente.getTipo());
            s = c.prepareStatement("INSERT INTO Compradores (nombre, inmobiliaria_id) VALUES (?, ?)");
        } else {
            s = c.prepareStatement("INSERT INTO Vendedores (nombre, inmobiliaria_id) VALUES (?, ?)");
        }
        s.setString(1, cliente.getNombre());
        s.setInt(2, inmobiliariaId);
        s.executeUpdate();
    }

    public int obtenerUltimoIdInsertado() throws SQLException {
        s = c.prepareStatement("SELECT LAST_INSERT_ID()");
        ResultSet rs = s.executeQuery();
        rs.next();
        return rs.getInt(1);
    }




    public static void main(String[] args) throws Exception {
        try{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        App app = new App();
        parser.parse("inmobiliaria.xml", app);
        
        for (int i = 0; i < app.inmobiliarias.size(); i++) {
            System.out.println(app.inmobiliarias.get(i).toString());
        }
        app.sacarPropiedades2("María González");
       } catch(Exception e){
            e.printStackTrace();
       }
    }
}
