import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public Connection c = crearConexion();
    public PreparedStatement ps = null;
    public String baseDatos;

    private String escogerBaseDeDatos() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Inserte base de datos para crear el XML");
        baseDatos = sc.nextLine();
        System.out.println("Base de datos seleccionada: " + baseDatos.trim().toLowerCase());
        return baseDatos.trim().toLowerCase();
    }

    public Connection crearConexion()  {
        String db = escogerBaseDeDatos();
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
    public void cerrarConexion() {
        try {
            if (c!=null) 
                c.close();
            if (ps!=null) 
                ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crearStringXML() throws SQLException {
        String xml = "<?xml version='1.0' encoding='UTF-8'?>\n";
        xml += "<"+baseDatos+">\n";
        ps = c.prepareStatement("SHOW TABLES");
        ps.execute();
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            String tabla = rs.getString(1);
            xml += "\t<"+tabla+">\n";
            String tablaReducida = tabla.substring(0, tabla.length() - 2);

            ps = c.prepareStatement("SELECT * FROM " + tabla);            
            ps.executeQuery();

            ResultSet rs2 = ps.getResultSet();
            int columnas = rs2.getMetaData().getColumnCount();
            while (rs2.next()) {
                xml += "\t\t<"+tablaReducida+">\n";
                for (int i = 1; i < columnas+1; i++) {
                    String nombreColumna = rs2.getMetaData().getColumnName(i);
                    xml += "\t\t\t<"+nombreColumna+">"+rs2.getString(i)+"</"+nombreColumna+">\n";
                }
                xml += "\t\t</"+tablaReducida+">\n";
            }
            xml += "\t</"+tabla+">\n";
        }
        xml += "</"+baseDatos+">\n";
        crearXML(xml);
        
    }
    public void crearXML(String xml) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(baseDatos+".xml"));
            bw.write(xml);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        App a = new App();
        a.crearStringXML();
        a.cerrarConexion();
    }
}
