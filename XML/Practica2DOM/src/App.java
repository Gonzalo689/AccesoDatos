import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class App {
    private LinkedList<Libreria> librerias = new LinkedList<>();
	private Libro l;
    private Libreria libreria;
	private Connection c;
    private PreparedStatement s;

	public  void verLibrerias() {
		for (Libreria libr : librerias) {
			System.out.println(libr);
			System.out.println("------------------------------------------------");
		}
	}
	public void showNode(Node node, int level) throws Exception
	{
		for(int i=0; i<level;i++) {
			System.out.print(" ");
		}
		switch(node.getNodeType()) {
			case Node.DOCUMENT_NODE:
				Document doc = (Document) node;
				System.out.println("Documento DOM. Versión: " + doc.getXmlVersion() + " . Codificación: " + doc.getXmlEncoding());
				break;
			case Node.ELEMENT_NODE:
				//Crear Libreria
				if (node.getParentNode().getNodeName().equals("libraries")) {
                    libreria = new Libreria();
                    librerias.add(libreria);
				}
				if (node.getParentNode().getNodeName().equals("books")) {
					l = new Libro();	
					libreria.addLibro(l);
				}
				
				System.out.print("<" + node.getNodeName());
				NamedNodeMap attributes = node.getAttributes();
				
				for(int i=0; i<attributes.getLength();i++)
				{
					Node attribute = attributes.item(i);
					System.out.print("@" + attribute.getNodeName() + "[" + attribute.getNodeValue() + "]");
					// cargarDatos
					datosLibreria(attribute.getNodeName(), attribute.getNodeValue());
				}
				System.out.println(">");

				
				break;
			case Node.TEXT_NODE:
				String text = node.getNodeValue();
				if(text.trim().length()>0) {
					System.out.println(node.getNodeName() + "[" + node.getNodeValue() + "]");
					datosLibreria( node.getParentNode().getNodeName() , node.getNodeValue());
				}
				break;
		}
		NodeList childNodes = node.getChildNodes();
		for(int i=0; i<childNodes.getLength(); i++) {
			showNode(childNodes.item(i), level+1);
		}
	}
    public void datosLibreria(String datoNombre,String valor){
		switch (datoNombre.trim().toLowerCase()) {
            case "address":
                libreria.setCalle(valor);
            	break;
			case "id":
				try {
					libreria.setId(Integer.parseInt(valor));
				} catch (Exception e) {
					l.setId(valor);
				}
				break;
            case "author":
				l.setAutor(valor);
				break;    
			case "title":
				l.setTitulo(valor);
				break;	
			case "genre":
				l.setGenero(valor);
				break;
            case "price":
				l.setPrecio(Double.parseDouble(valor));
				break;
            case "publish_date":
				l.setFechaPubli(valor);;
				break;
            case "description":
				l.setDescripcion(valor);;
				break;
		}
	}

    public void readXML(String fichero) {
        File file = new File(fichero);
    	try {
    		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		  Document doc = dBuilder.parse(file);
    		  showNode(doc,0);
    		} catch(Exception e) {
    		  e.printStackTrace();
    		}
    }

	public void crearConexion()  {
        String db = "librerias";
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
			s = c.prepareStatement("DROP TABLE IF EXISTS Libros");
            s.execute();
            s = c.prepareStatement("DROP TABLE IF EXISTS Librerias");
            s.execute();
            s = c.prepareStatement("CREATE TABLE Librerias (id INT PRIMARY KEY , calle VARCHAR(255))");
            s.execute();
            s = c.prepareStatement("CREATE TABLE Libros (id VARCHAR(255) PRIMARY KEY , autor VARCHAR(255), titulo VARCHAR(255), genero VARCHAR(255), precio DOUBLE,fechaPubli VARCHAR(255),descripcion VARCHAR(255)" + 
					", libreria_id INT, FOREIGN KEY (libreria_id) REFERENCES Librerias(id))");
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
	public void insertarDatos() {
		for (Libreria libreria : librerias) {
			insertarLibrerias(libreria);
		}
		for (Libreria libreria : librerias) {
			for (int i = 0; i < libreria.sizeLibros(); i++) {
				insertarLibros(libreria.getLibroIndex(i), libreria.getId());
			}
		}
		

	}
	private void insertarLibrerias(Libreria lib) {
		try {
            c.setAutoCommit(false);
            s = c.prepareStatement("INSERT INTO Librerias VALUES (?,?)");
			s.setInt(1, lib.getId());
			s.setString(2, lib.getCalle());
            s.executeUpdate();
            c.commit();
        } catch (Exception e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
            e.printStackTrace();
        }

	}
	private void insertarLibros(Libro lib , int libreriaId) {
		try {
            c.setAutoCommit(false);
            s = c.prepareStatement("INSERT INTO Libros VALUES (?,?,?,?,?,?,?,?)");
			s.setString(1, lib.getId());
			s.setString(2, lib.getAutor());
			s.setString(3, lib.getTitulo());
			s.setString(4, lib.getGenero());
			s.setDouble(5, lib.getPrecio());
			s.setString(6, lib.getFechaPubli());
			s.setString(7, lib.getDescripcion());
			s.setInt(8, libreriaId);
			
            s.executeUpdate();
            c.commit();
        } catch (Exception e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
            e.printStackTrace();
        }
	}

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.readXML("libraries.xml");
		app.verLibrerias();
		app.crearConexion();
		app.crearTablas();
		app.insertarDatos();
		app.cerrarConexion();
    }
}
