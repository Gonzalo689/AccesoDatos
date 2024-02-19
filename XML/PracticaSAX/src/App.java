import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class App extends DefaultHandler {
    private List<Library> libraries;
    private Library library;
    private Book book ;
    private String dato;
    private Connection c;
    private PreparedStatement s;

    public App (){
        libraries = new LinkedList<>();
        book = new Book();
    }

    @Override
    public void startDocument(){
       
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        // Inicializar nuevo libro
        if (qName.trim().equalsIgnoreCase("book")) {
            book = new Book();
            book.setId(attributes.getValue("id"));
        }
        // Inicializar nueva libreria
        if (qName.trim().equalsIgnoreCase("library")) {
            library = new Library();
            library.setId(Integer.parseInt(attributes.getValue("id")));
        }
        
        // Guardar el nombre de la etiqueta
        dato= qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String text3 = "";
        String text = new String(ch, start, length);
        //System.out.println(dato);
        if(text.trim().length()>0){
            
            System.out.println(dato);
            String[] text2 = text.split("\n");
            for (String  c : text2) {
                if (!c.isEmpty()) {
                    text3 += c.trim() +" ";
                }
            }
            datosLibreria(dato,text3);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        if (qName.trim().equals("library")) {
            libraries.add(library);
        }
        if (qName.trim().equalsIgnoreCase("book")) {
            library.addbook(book);
        }
    }

    @Override
    public void endDocument(){}

    public void datosLibreria(String datoNombre,String valor){
		switch (datoNombre.trim().toLowerCase()) {
            case "address":
                library.setAddress(valor);
            case "author":
				book.setAuthor(valor);
				break;    
			case "title":
                book.setTitle(valor);
				break;	
			case "genre":
				book.setGenre(valor);
				break;
            case "price":
                book.setPrice(Double.parseDouble(valor));
				break;
            case "publish_date":
                book.setPublish_date(valor);
				break;
            case "description":
                if (book.getDescription() != null) 
                    book.setDescription(book.getDescription() + " " + valor  );
                else   
                    book.setDescription(valor);
				break;
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
		for (Library library : libraries) {
			insertLibrary(library);
		}
		for (Library library : libraries) {
			for (int i = 0; i < library.sizebooks(); i++) {
				insertBooks(library.getbook(i), library.getId());
			}
		}
		

	}
	private void insertLibrary(Library lib) {
		try {
            c.setAutoCommit(false);
            s = c.prepareStatement("INSERT INTO Librerias VALUES (?,?)");
			s.setInt(1, lib.getId());
			s.setString(2, lib.getAddress());
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
	private void insertBooks(Book lib , int libreriaId) {
		try {
            c.setAutoCommit(false);
            s = c.prepareStatement("INSERT INTO Libros VALUES (?,?,?,?,?,?,?,?)");
			s.setString(1, lib.getId());
			s.setString(2, lib.getAuthor());
			s.setString(3, lib.getTitle());
			s.setString(4, lib.getGenre());
			s.setDouble(5, lib.getPrice());
			s.setString(6, lib.getPublish_date());
			s.setString(7, lib.getDescription());
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


    public static void main(String[] args) {
       try{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        App app = new App();
        parser.parse("libraries.xml", app);

        app.crearConexion();
        app.crearTablas();
        app.insertarDatos();
        app.cerrarConexion();

       } catch(Exception e){
            e.printStackTrace();
       }
    }
}