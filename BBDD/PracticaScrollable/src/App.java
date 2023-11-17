
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public Connection c = crearConexion("W3schools");
    public Statement s = null;

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
    public void cerrarConexion(){
        try {
            s.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void mostrarProductoPrecio() throws SQLException {
        s = c.createStatement();
    }

    public static void main(String[] args) throws Exception {
        App a = new App();

        a.cerrarConexion();
    }
}
