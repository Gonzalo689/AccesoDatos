
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class App {
    public Connection c = crearConexion("W3schools");
    public Statement s = null;

    /**
     * @param baseDatos nombre de la base de datos
     * @return la conexion a la base de datos indicada
     */
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
    /**
     * Método para cerrar tanto la conexion a la base de datos como el Statement 
     */
    public void cerrarConexion(){
        try {
            s.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Método que sirve para ver una query del final hacia atras hasta el principio, con el afterLast() muevo el puntero al final
     * y con el .previous() voy iterando hacia atras
     * @param rs ResultSet de la query que se desea ver
     * @throws SQLException
     */
    public void mostrarTuplasInverso(ResultSet rs) throws SQLException{
        rs.afterLast();  
        int numColumns = rs.getMetaData().getColumnCount(); 
        while (rs.previous()) { 
            for (int i = 1; i <= numColumns -1; i++) {
                System.out.print(rs.getString(i) + " - ");
            }
            System.out.print(rs.getString(numColumns));
            System.out.println();
        }
        rs.close(); 
        System.out.println("--------------------------------------------");
    }

    public void mostrarProductoPrecio() throws SQLException {
        System.out.println("Mostrar el nombre de los productos y su precio cuyo nombre empiece por 'T' y cuyo precio sea inferior a 10.");
        s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = s.executeQuery("SELECT ProductName, Price FROM products " +
                                "WHERE ProductName LIKE 'T%' and Price < 10" );
        mostrarTuplasInverso(rs);
        
    }
    public void mostrarProveedoresUsaIta()throws SQLException{
        System.out.println("Mostrar todos los proveedores que sean de USA o Italia");
         s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = s.executeQuery("SELECT * FROM suppliers " +
                                "WHERE Country = 'Italy' or Country = 'USA'" );
        mostrarTuplasInverso(rs);
        
    }
    public void mostrarClientesNa() throws SQLException {
        System.out.println("Mostrar toda la información sobre los clientes cuyo país termine en 'n' y cuyo nombre contenga una 'a'.");
        s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = s.executeQuery("SELECT * FROM customers " +
                                "WHERE CustomerName LIKE '%a%' and Country like '%n'" );
        mostrarTuplasInverso(rs);
        
    }
    /**
     * Fúncion para sacar la mitad de un numero y si no es un numero entero me da el siguiente numero
     * @param tupla
     * @return
     */
    public int numMedio(int tupla){
        int mitad = tupla / 2; 
        return tupla % 2 == 0 ? mitad : mitad + 1;
    }
    /**
     * Método para mostrar la primera tupla la ultima y la del medio
     * @param rs query que deseo que se ejecute
     * @throws SQLException
     */
    public void mostraTuplas(ResultSet rs)throws SQLException{
        int numColumns = rs.getMetaData().getColumnCount();
        rs.absolute(1);
        System.out.println("Primera tupla ");
        for (int index = 1; index <= numColumns -1; index++) {
            System.out.print(rs.getString(index) + " - ");
            
        }
        System.out.println(rs.getString(numColumns) + "\n");
        rs.last();
        System.out.println("Ultima tupla");
        for (int index = 1; index <= numColumns -1; index++) {
            System.out.print(rs.getString(index) + " - ");
            
        }
        System.out.println(rs.getString(numColumns) + "\n");
        int mitad = numMedio(rs.getRow());
        rs.absolute(mitad);
        System.out.println("Tupla del Medio");
        for (int index = 1; index <= numColumns -1; index++) {
            System.out.print(rs.getString(index) + " - ");
            
        }
        System.out.println(rs.getString(numColumns) );
        rs.close();
        System.out.println("--------------------------------------------");
    }
    public void mostrarProductoMediaPrecio() throws SQLException {
        System.out.println("Mostrar el ID de la categoría y la media de los precios de los productos de esta categoría. El campo media deberá mostrarse como MEDIA (un alias)");
        s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = s.executeQuery("SELECT CategoryID, avg(Price) AS 'MEDIA' " +
                                "FROM products GROUP BY CategoryID" );
        mostraTuplas(rs);
    }
    public void mostrarProductoCategoria() throws SQLException {
        System.out.println("Mostrar los ids de las categorías para los que hay productos asociados");
        s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = s.executeQuery("SELECT distinct CategoryID " +
                                "FROM products WHERE CategoryID IS NOT NULL" );
        mostraTuplas(rs);
        
    }
   
    public void mostrarPedidosJulio1996() throws SQLException {
        System.out.println("Mostrar el listado de pedidos que se han hecho en el mes de Julio del año 1996.");
        s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = s.executeQuery("SELECT * FROM orders " +
                                "WHERE YEAR(OrderDate) = 1996 AND MONTH(OrderDate) = 07" );
        mostraTuplas(rs);
        
    }
    /**
     * Método que muestra  las 3 tuplas del medio y la tupla que nos envien por parametro, con el .last() pongo el puntero al final
     * y con gracias a ello puedo sacar con .getRow() todas las tuplas que tiene la query, con el primer if veo si hay menos de 3 tuplas para que empieze 
     * desde el principio y si no ira una posición hacia atras para leer las 3 filas correspondientes.
     * @param rs query que deseo que se ejecute
     * @param pos numero de la tupla, la cual deseo saber los datos
     * @throws SQLException
     */
    public void mostrarTuplas3(ResultSet rs , int pos) throws SQLException {
        rs.last();
        int cont = 0;
        int tuplastotal = rs.getRow();
        int mitad = numMedio(tuplastotal);
        int numColumns = rs.getMetaData().getColumnCount();
        rs.absolute(mitad);
        if (tuplastotal <= 3) {
            rs.first();
        }else
            rs.relative(-1);
        while (rs.next() && cont != 3) {
            for (int index = 1; index <= numColumns -1; index++) {
                System.out.print(rs.getString(index) + " - ");
            }
            System.out.println(rs.getString(numColumns) );
            cont++;
        }
        System.out.println("Tupla con la posicion elegida");
        if(pos <= tuplastotal && pos > 0 ){
            rs.absolute(pos);
            for (int index = 1; index <= numColumns -1; index++) {
                System.out.print(rs.getString(index) + " - ");
            }
            System.out.println(rs.getString(numColumns) );
        }else{
            System.out.println("Error para mostrar esa tupla indicada");
        }
        rs.close();
        System.out.println("--------------------------------------------");
    }
    public void mostrarProductoMedioPais(int pos) throws SQLException {
        System.out.println("Listar el precio medio de los productos agrupados por el país del proveedor.");
        s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = s.executeQuery("SELECT s.Country, AVG(p.price) FROM products p "+
                    "INNER JOIN suppliers s ON p.SupplierID = s.SupplierID GROUP BY s.Country" );

        mostrarTuplas3(rs,pos);
    }

    public void mostrarProductoTop10(int pos) throws SQLException {
        System.out.println("Obtener los 10 productos más vendidos del año 1996");
        s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = s.executeQuery("SELECT p.* FROM  products p INNER JOIN order_details od on od.ProductID = p.ProductID "+
            "INNER JOIN orders o ON o.OrderID = od.OrderID WHERE YEAR(OrderDate) = 1997 GROUP BY p.ProductID LIMIT 10" );

        mostrarTuplas3(rs,pos);
    }
    
    public void mostrarPedidos300(int pos) throws SQLException {
        System.out.println("Listar todos los pedidos cuyo precio sea mayor a 300€");
        s = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = s.executeQuery("SELECT o.* ,  SUM(p.Price) FROM orders o JOIN order_details od ON o.OrderID = od.OrderID "+
       "JOIN products p ON od.ProductID = p.ProductID GROUP BY o.OrderID HAVING SUM(p.Price) > 300" );

        mostrarTuplas3(rs,pos);

    }
    

    public static void main(String[] args) throws Exception {
        App a = new App();
        // 1º
        a.mostrarProductoPrecio(); 
        // 2º
        a.mostrarProveedoresUsaIta();
        // 3º
        a.mostrarClientesNa();
        // 4º
        a.mostrarProductoMediaPrecio();
        // 5º
        a.mostrarProductoCategoria();
        // 6º
        a.mostrarPedidosJulio1996();
        // 7º
        a.mostrarProductoMedioPais(10);
        // 8º
        a.mostrarProductoTop10(1);
        // 9º
        a.mostrarPedidos300(2);

        a.cerrarConexion();
    }
}
