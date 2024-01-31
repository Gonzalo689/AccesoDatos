import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MetaData {
    public Connection c = crearConexion("w3schools");
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

    public boolean comprobarTabla(String talba) throws SQLException{
        String query = "SHOW TABLES";
        s = c.createStatement();
        ResultSet rs = s.executeQuery(query); 
        while (rs.next()) {
            if (rs.getString(1).equals(talba)) {
                return true;
            }
        }
        rs.close();
        s.close();
        return false;
    }
    
    public void mostrarTabla(String tabla)throws SQLException{
        if (comprobarTabla(tabla)) {
            String query = "Select * FROM "+tabla;
            s = c.createStatement();
            ResultSet rs = s.executeQuery(query); 
            String nombreTabla = rs.getMetaData().getTableName(1);
            int  numeroColumnas =  rs.getMetaData().getColumnCount();
            
            System.out.println(nombreTabla + ".\n");
            System.out.println("número de columnas:  " + numeroColumnas+".\n");
            String columnas = "Columnas:"  ;
            for (int i = 1; i <= numeroColumnas-1; i++) {
                columnas += rs.getMetaData().getColumnName(i) + ",";
            }
            columnas += rs.getMetaData().getColumnName(numeroColumnas)+".\n";
            System.out.println(columnas + "\n");
            
            int count =0;
            while (rs.next()) {
                System.out.print("Fila " + count + ":( ");
                for (int i = 1; i <= numeroColumnas-1; i++) {
                    System.out.print(rs.getString(i) + ",");
                }
                System.out.println();
            }

        }else{
            System.out.println("la tabla no existe");
        }
    }


    public static void main(String[] args) throws SQLException {
        MetaData m = new MetaData();
        m.mostrarTabla("orders");
        m.cerrarConexion();
    }
    
}
