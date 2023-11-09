import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.sql.Statement;

public class App {
    public static LinkedList<Estudiante> estudiantes = new LinkedList<>();
    public static LinkedList<Libro> libros = new LinkedList<>();
    public static HashMap<String, LinkedList<String> > prestamos = new HashMap<>(); 
    public static Scanner num = new Scanner(System.in);
    public static Scanner texto = new Scanner(System.in);
    public static Connection c;
    public static Statement s;
    public static void main(String[] args) throws Exception {
        String db = "instituto";
		String host = "localhost";
		String port = "3306";
		String urlConnection = "jdbc:mysql://"+host+":"+port+"/"+db;
		String user = "root";
		String pwd = "infobbdd";
		c = DriverManager.getConnection(urlConnection, user, pwd);
		s = c.createStatement();
		
        estudiantes.add(new Estudiante(1, "Carlos", 29));
        estudiantes.add(new Estudiante(2, "Marina", 33));
        estudiantes.add(new Estudiante(3, "Daniel", 29));
        estudiantes.add(new Estudiante(4, "Verónica", 33));
        estudiantes.add(new Estudiante(5, "Justo", 26));
        
        // // Crear Tabla
        // // String crearTabla = "CREATE TABLE Estudiantes (id INT PRIMARY KEY, nombre VARCHAR(50), edad INT)";
        // // s.execute(crearTabla);
        
        
        // InsertarDatos
        // String query = "INSERT INTO Estudiantes VALUES (?, ?, ?)";
        // PreparedStatement preparedStatement = c.prepareStatement(query);
        // for (Estudiante estudiante : estudiantes) {
        //     preparedStatement.setInt(1, estudiante.getId());
        //     preparedStatement.setString(2, estudiante.getNombre());
        //     preparedStatement.setInt(3, estudiante.getEdad());
        //     preparedStatement.executeUpdate();
        // }
        // // Mostrar tabla estudiantes
        // ResultSet rs = s.executeQuery("SELECT * FROM estudiantes");

        // while (rs.next()) {
        //     int id = rs.getInt("id");
        //     String nombre = rs.getString("nombre");
        //     int edad = rs.getInt("edad");
        //     System.out.println("ID: " + id + ", Nombre: " + nombre + ", Edad: " + edad);
        // }
        
        // Scanner num = new Scanner(System.in);
        // // // Actualizar con un id
        // // System.out.println("Inserte la id");
        // // int id = num.nextInt();
        // // System.out.println("Inserte la edad");
        // // int edadid = num.nextInt();
        // // String actualizarDato ="UPDATE estudiantes SET edad = "+edadid+" WHERE id = "+id;
        // // s.execute(actualizarDato);

        // // // Eliminar alumno
        // // System.out.println("Inserte la id");
        // // id = num.nextInt();
        // // String eliminarDato ="DELETE FROM estudiantes WHERE id =" + id;
        // // s.execute(eliminarDato);

        // // Insertar datos en hasmap edades
        // HashMap<Integer, LinkedList<String> > edades = new HashMap<>(); 
        // rs = s.executeQuery("SELECT DISTINCT edad FROM estudiantes");
        // while (rs.next()) {
        //     edades.put(rs.getInt("edad"), new LinkedList<>());
        // }
        // for (Map.Entry<Integer, LinkedList<String>> entry : edades.entrySet()) {
		// 	ResultSet rsp = s.executeQuery("SELECT nombre FROM estudiantes WHERE edad ="+ entry.getKey());
		// 	while(rsp.next())
		// 	{
        //         entry.getValue().add(rsp.getString("nombre"));
		// 	}  
        // }
        // edades.forEach((k,v) -> System.out.println(k + " " + v));


        // num.close();
        // rs.close();
        // s.close();
        // c.close();

        //---------------------------------------------------------------------------------------------------------
        //Ejercicio 2
        
        // crearTablaLibro(s);
        // crearTablaPrestamo(s);
        // crearLibro(c);
        //buscarLibro("mio");
        //prestamo();
        devolucion();
        mostrarPrestamos();



    }
    public static void crearTablaLibro()throws Exception{
        String crearTabla = "CREATE TABLE Libros (isbn VARCHAR(50) PRIMARY KEY, titulo VARCHAR(50), autor VARCHAR(50), copias INT)";
        s.execute(crearTabla);
    }
    public static void crearTablaPrestamo()throws Exception{
        String crearTabla = "CREATE TABLE Prestamos (id INT AUTO_INCREMENT PRIMARY KEY, "+
                "    estudiante INT NOT NULL," + 
                "    isbn VARCHAR(50) NOT NULL," + 
                "    fechainicio DATE NOT NULL," + 
                "    fechafin VARCHAR(50)," + 
                "    FOREIGN KEY (estudiante) REFERENCES Estudiantes(id)," + 
                "    FOREIGN KEY (isbn) REFERENCES Libros(isbn)" + 
                ");";
        s.execute(crearTabla);
    }
    public static Libro crearLibro() throws SQLException{
        System.out.println("ISBN");
        String isbn = texto.nextLine();
        System.out.println("Titulo");
        String titulo = texto.nextLine();
        System.out.println("Autor");
        String autor = texto.nextLine();
        System.out.println("Copias");
        int copias = num.nextInt();

        String query = "INSERT INTO Libros VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setString(1, isbn);
        preparedStatement.setString(2, titulo);
        preparedStatement.setString(3, autor);
        preparedStatement.setInt(4, copias);
        preparedStatement.executeUpdate();
        
        return new Libro(isbn, titulo, autor, copias);

    }
    public static Libro buscarLibro(String t) throws SQLException{
        
        ResultSet rs = s.executeQuery("SELECT * FROM libros WHERE autor LIKE '"+t+"' OR titulo LIKE '"+t+"'");
        Libro l ;
        while (rs.next()) {
            String isbn = rs.getString("isbn");
            String titulo = rs.getString("titulo");
            String autor = rs.getString("autor");
            int copias = rs.getInt("copias");
            
            l= new Libro(isbn, titulo, autor, copias);
            System.out.println(l);
            return l;
        }
        
        return null;
    }
    public static void prestamo()throws SQLException{
        System.out.println("Prestamo de libros");
        System.out.println("ID del estudiante");
        int id = num.nextInt();
        System.out.println("ISBN libro");
        String isbn = texto.nextLine();
        Date d = new Date(1222-12-12);
        String query = "INSERT INTO prestamos(estudiante, isbn, fechainicio) VALUES ( ?, ?, ?)";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, isbn);
        preparedStatement.setDate(3, d);
        preparedStatement.executeUpdate();
        
        String actualizarDato ="UPDATE libros SET copias = copias -1 WHERE isbn LIKE '"+isbn+"'";
        s.execute(actualizarDato);
    }
    public static void devolucion()throws SQLException{
        System.out.println("Prestamo de libros");
        System.out.println("ISBN libro");
        String isbn = texto.nextLine();

        String borrarDato ="UPDATE prestamos SET fechafin = 'findevolucio' WHERE isbn LIKE '"+isbn+"'";
        s.execute(borrarDato);
        
        String actualizarDato ="UPDATE libros SET copias = copias +1 WHERE isbn LIKE '"+isbn+"'";
        s.execute(actualizarDato);
    }
    public static void mostrarPrestamos()throws SQLException{
        Statement newStatement = c.createStatement(); 
        ResultSet rs = s.executeQuery("SELECT id, nombre FROM estudiantes WHERE id IN ("
        + "Select estudiante FROM prestamos );");
        while (rs.next()) {
            int id = rs.getInt(1);
            String nombre = rs.getString(2);
            ResultSet rs2 = newStatement.executeQuery("SELECT titulo FROM libros WHERE isbn IN ("
            + "Select isbn FROM prestamos WHERE estudiante = "+id+");");
            LinkedList<String>titulos = new LinkedList<>();
            while (rs2.next()) {
                titulos.add(rs2.getString(1));
            }
            prestamos.put(nombre, titulos);
        }
        for (Map.Entry<String, LinkedList<String>> entrada : prestamos.entrySet()) {
            
            System.out.println("Estudiante: " + entrada.getKey() + ".\n     Libros: " + entrada.getValue());
        }
    }
}
