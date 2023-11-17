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
    // Métodos del ejerccio dos
    public static void crearTablaLibro(Connection c)throws SQLException{
        s = c.createStatement();
        String crearTabla = "CREATE TABLE Libros (isbn VARCHAR(50) PRIMARY KEY, titulo VARCHAR(50), autor VARCHAR(50), copias INT)";
        s.execute(crearTabla);
    }
    public static void crearTablaPrestamo(Connection c)throws SQLException{
        s = c.createStatement();
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
    public static void eliminarLibro() throws SQLException {
        System.out.println("Eliminar libro");
        System.out.println("ISBN libro");
        String isbn = texto.nextLine();
        String borrarLibro ="DELETE FROM libros WHERE isbn LIKE '"+isbn+"'";
        s.execute(borrarLibro);
    }
    //Ejercicio 3 ------------------------------------------------------------

    public static void buscarAlumnoMasPrestamo() throws SQLException {
        System.out.println("Estadisticas: ");
        ResultSet rs = s.executeQuery("SELECT a.nombre, COUNT(*) FROM estudiantes a INNER JOIN prestamos p ON  a.id = p.estudiante"
        + " GROUP BY a.id ORDER BY COUNT(*) DESC LIMIT 1;");
            
        while (rs.next()) {
            System.out.println("El alumno con más prestamos es " + rs.getString(1) +" con "+ rs.getInt(2) + " prestamos");
        }

        rs = s.executeQuery("SELECT l.titulo, COUNT(*) FROM libros l INNER JOIN prestamos p ON l.isbn = p.isbn"
        + " GROUP BY l.isbn ORDER BY COUNT(*) DESC LIMIT 1;");
            
        while (rs.next()) {
            System.out.println("El libro mas prestado es " + rs.getString(1) +" con "+ rs.getInt(2) + " prestamos");
        }
        rs = s.executeQuery("SELECT * FROM libros WHERE isbn NOT IN (SELECT isbn FROM prestamos)");
        System.out.println("Libros que no han sido prestados:");
        while (rs.next()) {
            Libro l = new Libro(rs.getString("isbn"), rs.getString("titulo"), rs.getString("autor"), rs.getInt("copias"));
            System.out.println(l);
        }
        rs = s.executeQuery("SELECT * FROM libros WHERE isbn NOT IN (SELECT isbn FROM prestamos)");
        System.out.println("Libros que no han sido prestados:");
        while (rs.next()) {
            Libro l = new Libro(rs.getString("isbn"), rs.getString("titulo"), rs.getString("autor"), rs.getInt("copias"));
            System.out.println(l);
        }

    }

    public static void crearTablaProfesores(Connection c) throws SQLException {
        s = c.createStatement();
        String crearTabla = "CREATE TABLE Profesores (id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(50), edad INT, telefono INT)";
        s.execute(crearTabla);
    }
    public static void insertarUnProfesor()throws SQLException{
        String query = "INSERT INTO Profesores(nombre, edad, telefono) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        
        preparedStatement.setString(1, "Emilio");
        preparedStatement.setInt(2, 52);
        preparedStatement.setInt(3, 123456789);
        preparedStatement.executeUpdate();
        preparedStatement.setString(1, "Almundena");
        preparedStatement.setInt(2, 46);
        preparedStatement.setInt(3, 987654321);
        preparedStatement.executeUpdate();
        
    }
    public static void crearTablaHoraria(Connection c) throws SQLException {
        s = c.createStatement();
        String crearTabla = "CREATE TABLE Horarios (id INT PRIMARY KEY AUTO_INCREMENT, modulo VARCHAR(50), dia VARCHAR(50), estudiante INT)";
        s.execute(crearTabla);
    }
    public static void backup() throws SQLException {
        String db = "instututo_backup";
		String host = "localhost";
		String port = "3306";
		String urlConnection = "jdbc:mysql://"+host+":"+port+"/"+db;
		String user = "root";
		String pwd = "infobbdd";
		Connection c2 = DriverManager.getConnection(urlConnection, user, pwd);
		Statement s2 = c2.createStatement();
        // crearTablaEstudiantes(c2);
        // crearTablaProfesores(c2);
        // crearTablaHoraria(c2);
        // crearTablaLibro(c2);
        // crearTablaPrestamo(c2);

        // String query = "INSERT INTO Estudiantes VALUES (?, ?, ?)";
        // PreparedStatement preparedStatement = c2.prepareStatement(query);
        // ResultSet rs = s.executeQuery("SELECT * FROM estudiantes");
        // while(rs.next())
        // {
        //     preparedStatement.setInt(1, rs.getInt("id"));
        //     preparedStatement.setString(2, rs.getString("nombre"));
        //     preparedStatement.setInt(3, rs.getInt("edad"));
        //     preparedStatement.executeUpdate();
            
        // }
        // preparedStatement.close();
        // rs.close();
        
        // PreparedStatement ps2 = c2.prepareStatement("INSERT INTO Profesores VALUES (?, ?, ?, ?)");
        // ResultSet rs2 = s.executeQuery("SELECT * FROM profesores");
        // while(rs2.next())
        // {
        //     ps2.setInt(1, rs2.getInt("id"));
        //     ps2.setString(2, rs2.getString("nombre"));
        //     ps2.setInt(3, rs2.getInt("edad"));
        //     ps2.setInt(4, rs2.getInt("telefono"));
        //     ps2.executeUpdate();

        // }
        // ps2.close();
        // rs2.close();
        
        // PreparedStatement ps3 = c2.prepareStatement("INSERT INTO libros VALUES (?, ?, ?, ?)");
        // ResultSet rs3 = s.executeQuery("SELECT * FROM libros");
        // while(rs3.next())
        // {
        //     ps3.setString(1, rs3.getString("isbn"));
        //     ps3.setString(2, rs3.getString("titulo"));
        //     ps3.setString(3, rs3.getString("autor"));
        //     ps3.setInt(4, rs3.getInt("copias"));
        //     ps3.executeUpdate();

        // }   
        // ps3.close();
        // rs3.close();
        PreparedStatement ps4 = c2.prepareStatement("INSERT INTO prestamos VALUES (?, ?, ?, ?, ?)");
        ResultSet rs4 = s.executeQuery("SELECT * FROM libros");
        while(rs4.next())
        {
            ps4.setInt(1, rs4.getInt(1));
            ps4.setInt(2, rs4.getInt(2));
            ps4.setString(3, rs4.getString(3));
            ps4.setDate(4, rs4.getDate(4));
            ps4.setString(4, rs4.getString(4));
            ps4.executeUpdate();

        }   
        rs4.close();
        rs4.close();


        c2.close();
        s2.close();

    }
    public static void crearTablaEstudiantes(Connection c) throws SQLException {
        s = c.createStatement();
        String crearTabla = "CREATE TABLE Estudiantes (id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(50), edad INT)";
        s.execute(crearTabla);
    }
    


    //Atributos statico
    public static LinkedList<Estudiante> estudiantes = new LinkedList<>();
    public static HashMap<String, LinkedList<String> > prestamos = new HashMap<>(); 
    public static Scanner num = new Scanner(System.in);
    public static Scanner texto = new Scanner(System.in);
    public static Connection c;
    public static Statement s;

    //Main
    public static void main(String[] args) throws SQLException {
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
        
        // crearTablaLibro(c);
        // crearTablaPrestamo(c);
        // crearLibro();
        //buscarLibro("mio");
        //prestamo();
        //devolucion();
        // mostrarPrestamos();
        // eliminarLibro();
        //---------------------------------------------------------------------------------------------------------
        //Ejercicio 3
        //buscarAlumnoMasPrestamo();
        // crearTablaProfesores(c);
        // insertarUnProfesor();
        // crearTablaHoraria(c);
        backup();
        c.close();
        s.close();
    }
    
}
