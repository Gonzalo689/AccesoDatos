import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;


public class App  {

    public Connection c = crearConexion("dbproductos");
    public LinkedList<Producto> productos;
    public LinkedList<Categoria> categorias;
    public LinkedList<Color> colores ;
    public LinkedList<Tamanio> tamanios;
    public HashMap<Producto, LinkedList<Tamanio>> relacProdTamanio;
    public HashMap<LinkedList<Producto>, LinkedList<Color>> relacProdColor ;
    public HashMap<LinkedList<Producto>, LinkedList<Categoria>> relacProdCategoria ;

    public Connection crearConexion(String baseDatos)  {
        String db = baseDatos;
		String host = "localhost";
		String port = "3306";
		String urlConnection = "jdbc:mysql://"+host+":"+port+"/"+db;
		String user = "root";
		String pwd = "infobbdd";
        try{
            return DriverManager.getConnection(urlConnection, user, pwd);
        }catch (Exception e) {
            return null;
        }
    }

    public void createTable(String sql) throws SQLException {
        Statement s = c.createStatement();
        s.execute(sql);
        s.close();
    }
    public void addData(String table, String fiels, String value) throws SQLException {
        String query = "INSERT INTO "+ table +" ("+ fiels +") VALUES (" + value + ")";
        Statement s = c.createStatement();
        s.execute(query);
        s.close();
    }
    public void delete(String table, String whereClause) throws SQLException{
        String query = "DELETE FROM "+ table +" WHERE " + whereClause+ "";
        Statement s = c.createStatement();
        s.execute(query);
        s.close();
    }
    public void updateData(String table, String newValues, String whereClause)  throws SQLException{
        String query ="UPDATE "+ table + " SET " + newValues + " WHERE " + whereClause;
        Statement s = c.createStatement();
        s.execute(query);
        s.close();
    }
    public void crearTablaTamano() throws SQLException {
        String sql = "CREATE TABLE Tamaño (tamaño_id INT PRIMARY KEY, código_tamaño INT, clasificación INT)";
        createTable(sql);
    }
    public void crearTablaColor() throws SQLException {
        String sql = "CREATE TABLE Color (color_id INT PRIMARY KEY , código_color INT, nombre_color VARCHAR(50))";
        createTable(sql);
    }
    public void crearTablaCategorías() throws SQLException {
        String sql = "CREATE TABLE Categorías (categoría_id INT PRIMARY KEY , categoría_principal INT, nombre_categoria VARCHAR(50))";
        createTable(sql);
    }
    public void crearTablaProducto() throws SQLException {
        String sql = "CREATE TABLE Producto ( producto_id INT PRIMARY KEY, nombre_producto VARCHAR(50), tamaño_id INT, FOREIGN KEY (tamaño_id) REFERENCES Tamaño(tamaño_id))";
        createTable(sql);
    }
    public void crearTablaCategoríasProducto() throws SQLException {
        String sql = "CREATE TABLE Categorías_de_producto (categoría_id INT , Producto_id INT, PRIMARY KEY (categoría_id, producto_id), FOREIGN KEY (categoría_id) REFERENCES Categorías (categoría_id), FOREIGN KEY (Producto_id) REFERENCES Producto(producto_id))";
        createTable(sql);
    }
    public void crearTablaColoresProducto() throws SQLException {
        String sql = "CREATE TABLE Colores_del_Producto (color_id INT , Producto_id INT, PRIMARY KEY (color_id, producto_id), FOREIGN KEY (color_id) REFERENCES Color (color_id), FOREIGN KEY (Producto_id) REFERENCES Producto(producto_id))";
        createTable(sql);
    }

    public void crearTodasLasTablas()throws SQLException{
        crearTablaTamano();
        crearTablaColor();
        crearTablaCategorías();
        crearTablaProducto();
        crearTablaCategoríasProducto();
        crearTablaColoresProducto();
   }

    public void informacionTabla(String tabla) throws SQLException{ 
        Statement s = c.createStatement();
        String consulta2 ="SELECT  * FROM information_schema.columns WHERE table_schema = 'dbproductos'AND table_name = '" + tabla + "'";
        ResultSet rs = s.executeQuery(consulta2); 
        while (rs.next()) {
            
            System.out.println("-----------------------------------");
            System.out.println("Nombre de la columna: " + rs.getString(4));
            System.out.println("Tipo de la columna: " + rs.getString(8));
            System.out.println("Tamaño de la columna: " + rs.getString(9));
            if(rs.getString(17).equals("PRI"))
                System.out.println("Clave primaria de la tabla");
        }
        rs.close();
        System.out.println("-----------------------------------");
    }
    public void datosTabla(String tabla) throws SQLException {
        Statement s = c.createStatement();
        Statement s2 = c.createStatement();
        ResultSet rs ;
        ResultSet rs2;
        String consulta;
        String consulta2;
        switch (tabla.toLowerCase()) {
            case "producto":
                productos = new LinkedList<>();
                relacProdTamanio = new HashMap<>();
                consulta = "SELECT * FROM " + tabla;
                rs = s.executeQuery(consulta); 
                while (rs.next()) {
                    Producto p = new Producto(rs.getInt(1), rs.getString(2));
                    productos.add(p);
                    int id = rs.getInt(1);
                    rs2 = s2.executeQuery("SELECT * FROM tamaño WHERE tamaño_id IN (SELECT tamaño_id FROM producto WHERE producto_id = "+id+" )");
                    LinkedList<Tamanio> tamaniosRelac = new LinkedList<>();
                    while (rs2.next()) {
                        tamaniosRelac.add(new Tamanio(rs2.getInt(1), rs2.getInt(2), rs2.getInt(3)));
                    }
                    relacProdTamanio.put(p, tamaniosRelac);
                    rs2.close();
                }
                rs.close();
                break;
            case "tamaño":
                tamanios = new LinkedList<>();
                consulta = "SELECT * FROM " + tabla;
                rs = s.executeQuery(consulta); 
                while (rs.next()) {
                    tamanios.add(new Tamanio(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
                }
                rs.close();
                break; 
            case "color":
                colores = new LinkedList<>();
                consulta = "SELECT * FROM " + tabla;
                rs = s.executeQuery(consulta); 
                while (rs.next()) {
                    colores.add(new Color(rs.getInt(1), rs.getInt(2), rs.getString(3)));
                }
                rs.close();
                break;   
            case "categorías":
                categorias = new LinkedList<>();
                consulta = "SELECT * FROM " + tabla;
                rs = s.executeQuery(consulta); 
                while (rs.next()) {
                    categorias.add(new Categoria(rs.getInt(1), rs.getInt(2), rs.getString(3)));
                }
                rs.close();
                break;         
            case "categorías_de_producto":
                s2 = c.createStatement();
                relacProdCategoria = new HashMap<>();
                consulta = "SELECT * from producto WHERE producto_id IN (SELECT producto_id FROM categorías_de_producto) ";
                consulta2 = "SELECT * from categorías WHERE categoría_id IN (SELECT categoría_id FROM categorías_de_producto) ";
                rs = s.executeQuery(consulta); 
                rs2 = s2.executeQuery(consulta2);
                while (rs.next()) {
                    LinkedList<Categoria> relacCategorias = new LinkedList<>();
                    while (rs2.next()) {
                        relacCategorias.add(new Categoria(rs2.getInt(1), rs2.getInt(2), rs2.getString(3)));
                    }
                    relacProdCategoria.put(productos, relacCategorias);
                    rs2.close();
                }
                rs.close();
                break;
            case "colores_del_producto":
                s2 = c.createStatement();
                relacProdCategoria = new HashMap<>();
                consulta = "SELECT * from producto WHERE producto_id IN (SELECT producto_id FROM colores_del_producto) ";
                consulta2 = "SELECT * from color WHERE color_id IN (SELECT color_id FROM colores_del_producto) ";
                rs = s.executeQuery(consulta); 
                rs2 = s2.executeQuery(consulta2);
                while (rs.next()) {
                    LinkedList<Color> relacColor = new LinkedList<>();
                    while (rs2.next()) {
                        relacColor.add(new Color(rs2.getInt(1), rs2.getInt(2), rs2.getString(3)));
                    }
                    relacProdColor.put(productos, relacColor);
                    rs2.close();
                }
                rs.close();
                break;    
            default:
                System.out.println("No existe esa tabla");
                break;
        }
        s.close();
        s2.close();
        
    }
    public void informacionTabla(String tabla, String columnas, String whereClause) throws SQLException {
        Statement s = c.createStatement();
        String consulta = "SELECT " + columnas + " FROM "+ tabla +" WHERE " + whereClause;
        ResultSet rs = s.executeQuery(consulta);

        String[] columnasNombre = columnas.split(",");
    
        while (rs.next()) {
            for (String nombreColumn : columnasNombre) {
                System.out.print(rs.getString(nombreColumn.trim()));
                System.out.print(" ");
            }
            System.out.println();
        }
        
    }
    public void informacionTablaConjunta(String tabla1, String tabla2) throws SQLException {
        Statement s1 = c.createStatement();
        Statement s2 = c.createStatement();
        Statement s3 = c.createStatement();
        String consulta1 = "";
        String consulta2 = "";
        String columnaComun = "";
        try {
            consulta1 = "SHOW COLUMNS FROM " + tabla1;
            consulta2 ="SHOW COLUMNS FROM " + tabla2;

            ResultSet rs1 = s1.executeQuery(consulta1);
            while (rs1.next()) {
                ResultSet rs2 = s2.executeQuery(consulta2);
                while (rs2.next()) {
                    String result1 = String.valueOf(rs1.getString(1));
                    String result2 = String.valueOf(rs2.getString(1));
                    if (result1.equals(result2)) {
                        columnaComun = rs1.getString(1);
                    }
                }
                    rs2.close();
            }
            rs1.close();
            if(columnaComun.equals(""))
                columnaComun = null;
            
            String cosulta3 = "Select * FROM " + tabla1 + " INNER JOIN "+ tabla2 + " ON " + tabla1 + "." + columnaComun + " = " + tabla2  + "." + columnaComun;
            ResultSet rs3 = s3.executeQuery(cosulta3);
            int columna = 1;
            System.out.println("----------------------");
            while (rs3.next()) {
                System.out.println("Tumpla: " + columna);
                for (int i = 1; i < rs3.getMetaData().getColumnCount() + 1; i++) {
                    System.out.print(rs3.getString(i) + " ");
                }
                System.out.println();
                System.out.println("----------------------");
                columna++;
            }
            rs3.close();

        } catch (Exception e) {
            if (columnaComun == null){
                System.out.println("No hay coincidencias entre tablas");
            }else
                System.out.println("Error al encontrar las tablas");
        }finally{
            s1.close();
            s2.close();
            s3.close();
        }
        
    }

    public void encontrarPatron2(String patron, Connection c1, String schema) throws SQLException{
        Statement s1 = c1.createStatement();
        Statement s2 = c1.createStatement();
        ResultSet rs1 = s1.executeQuery("SHOW TABLES");
        while (rs1.next()) {
            String tabla = rs1.getString(1);
            ResultSet rs2 = s2.executeQuery("SELECT * FROM " + tabla);
            while (rs2.next()) {
                for (int i = 1; i < rs2.getMetaData().getColumnCount() + 1; i++) {
                    String resultado = String.valueOf(rs2.getString(i));
                    if(resultado.contains(patron)){
                        System.out.println("Patron Encontrado en el la Base de datos " + schema + " tabla " + tabla + " y columna " + rs2.getMetaData().getColumnName(i));
                    }
                }
            }
            rs2.close();
        }
        rs1.close();
    }

    public void encontrarPatron(String patron) throws SQLException {
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SHOW schemas");
        while (rs.next()) {
            String schema = rs.getString(1);
            Connection c1 = crearConexion(schema);
            encontrarPatron2(patron, c1, schema);
            c1.close();
        }
        rs.close();
    }

    public void guardarCsvProducto() throws SQLException{
        Statement s1 = c.createStatement();
        String rutaFichero = "FicherosCSV/ProductosCSV.csv";
        File f = new File(rutaFichero);
        if (f.isFile()) {
            try {
                String linea;
                String[] atributo;
                BufferedReader bfr = new BufferedReader(new FileReader(f));
                while ( (linea = bfr.readLine()) != null ) {
                    atributo = linea.split(",");
                    int id = Integer.parseInt(atributo[0].trim());
                    String nombre = atributo[1].trim();
                    int tamanio = Integer.parseInt(atributo[2].trim());
                    s1.execute("INSERT INTO producto (producto_id, nombre_producto, tamaño_id) VALUES ( "+ id +", '" + nombre + "', " + tamanio +" )");
                }
                System.out.println("Productos insertados correctamente");
                bfr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void cerrarConextion(){
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    


    

    public static void main(String[] args) throws Exception {
        App a = new App();
        // a.crearTodasLasTablas();

        // a.addData("Color", " color_id, código_color, nombre_color ", "1, 1, 'Blanco'");
        // a.addData("Color", " color_id, código_color, nombre_color ", "2, 2, 'Negro'");
        // a.addData("Color", " color_id, código_color, nombre_color ", "3, 3, 'Azul'");

        // a.addData("Tamaño", " tamaño_id , código_tamaño , clasificación ", "1, 1, 10");
        // a.addData("Tamaño", " tamaño_id , código_tamaño , clasificación ", "2, 2, 50");
        // a.addData("Tamaño", " tamaño_id , código_tamaño , clasificación ", "3, 3, 100");

        // a.addData("Producto", " producto_id , nombre_producto , tamaño_id  ", "1, 'Mesa', 2");
        // a.addData("Producto", " producto_id , nombre_producto , tamaño_id  ", "2, 'Bolígrafo', 1");
        // a.addData("Producto", " producto_id , nombre_producto , tamaño_id  ", "3, 'Ratón', 1");

        // a.updateData("Producto", "nombre_producto = 'Mochila'", "producto_id = 1");

        // a.delete("Producto", "nombre_producto LIKE '%a%'");

        //Ejercicio 2
        a.informacionTabla("Producto");
        a.datosTabla("Producto");
        
        a.datosTabla("categorías_de_producto");
        a.datosTabla("color");
        a.datosTabla("colores_del_producto");
        a.datosTabla("tamaño");
        a.informacionTabla("producto" ,"producto_id, nombre_producto" , "producto_id = 1");
        
        a.informacionTablaConjunta("producto", "tamaño");
        a.encontrarPatron("Carlos");
        //a.guardarCsvProducto();

        a.cerrarConextion();
    }
}
