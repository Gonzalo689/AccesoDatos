import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class App {
    private Connection c;
    private PreparedStatement s;
    private String db ="";
    public void crearConexion(String bbdd)  {
        db = bbdd;
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

    public void crearXml() {
        try {
            s = c.prepareStatement("SHOW TABLES");
            s.execute();
            ResultSet rs = s.getResultSet();
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = null;
            while (rs.next()) {
                String tabla = rs.getString(1);
                String tablaReducida = tabla.substring(0, tabla.length() - 2);
                doc = docBuilder.newDocument();
                Element rootElement = doc.createElement(tabla);
                doc.appendChild(rootElement);
                s = c.prepareStatement("SELECT * FROM " + tabla);            
                s.executeQuery();
                ResultSet rs2 = s.getResultSet();
                int columnas = rs2.getMetaData().getColumnCount();
                while (rs2.next()) {
                    Element elemento1 = doc.createElement(tablaReducida);
                    rootElement.appendChild(elemento1);
                    for (int i = 1; i < columnas+1; i++) {
                        String nombreColumna = rs2.getMetaData().getColumnName(i);
                        Element elemento2 = doc.createElement(nombreColumna);
                        elemento2.setTextContent(rs2.getString(i));
                        if (rs2.getString(i)!=null) {
                            elemento1.appendChild(elemento2);
                        }
                    }
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(db + ".xml"));
            transformer.transform(source, result);
            
          } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
          } catch (TransformerException tfe) {
            tfe.printStackTrace();
          }catch (SQLException sqlException) {
            sqlException.printStackTrace();
          }
        
    }


  public static void main(String argv[]) {
    App app = new App();
    app.crearConexion("accesodatossql");
    app.crearXml();
    app.cerrarConexion();
  }
}