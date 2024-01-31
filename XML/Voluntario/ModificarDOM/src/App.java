import java.io.File;
import java.util.LinkedList;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class App {
    private LinkedList<Libreria> librerias = new LinkedList<>();
	private Libro l;
    private Libreria libreria;
    private final int EXTRA_PRICE = 10;


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

    public void crearXMLModificado() {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("libraries");
            doc.appendChild(rootElement);
            //Mover un libro de la libreria 2 a la libreria 3
            moveBooks();

            for (Libreria libreria : librerias) {
                libraryXML(doc, rootElement, libreria);
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("librariesMod.xml"));

            transformer.transform(source, result);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
    public void libraryXML(Document doc, Element rootElement, Libreria libreria) {
        
        Element element= crearElementoAttr(doc, rootElement, "library", "id", libreria.getId() + "");
        deleteBooks(libreria); // Borrar libros con precio menor a 5
        aniadirLibroExtra(libreria); // aniadir un libro extra a la libreria con id 1
        crearElemento(doc, element, "address", libreria.getCalle());
        
        bookXML(doc, element, libreria);
        
    }
    public void bookXML(Document doc, Element elemenLibrary, Libreria libreria) {
  
        int numBooks = libreria.sizeLibros();
        if (numBooks>0) {
            Element elementBooks = crearElemento(doc, elemenLibrary, "books", null);
            
            for (int i = 0; i < numBooks; i++) {
                Libro b = libreria.getLibroIndex(i);
                String bId = b.getId();
                Element elementBook = crearElementoAttr(doc, elementBooks, "book", "id", bId + "");
                String author = b.getAutor();
                if (author != null) {
                    crearElemento(doc, elementBook, "author", author);
                }
                String title = b.getTitulo();
                if (title != null) {
                    crearElemento(doc, elementBook, "title", title);
                }
                String genre = b.getGenero();
                if (genre != null) {
                    crearElemento(doc, elementBook, "genre", genre);
                }
                Double price = b.getPrecio();
                if (price != 0.0) {
                    // Comprobar si el ultimo caracter es par
                    Double lastCaracter = Double.valueOf(bId.charAt(bId.length() - 1));
                    // Aumentar el precio si es par
                    if(lastCaracter %2 == 0)
                        price += EXTRA_PRICE;
                    crearElemento(doc, elementBook, "price", String.valueOf(price));
                }
                String publish_date = b.getFechaPubli();
                if (publish_date != null) {
                    crearElemento(doc, elementBook, "publish_date", publish_date);
                }
                String description = b.getDescripcion();
                if (description != null) {
                    crearElemento(doc, elementBook, "description", description);
                }
                
            }
        }
        
    }
    public Element crearElemento(Document doc, Element rootElement, String nombre, String valor) {
        Element element = doc.createElement(nombre);
        rootElement.appendChild(element);
        if (valor == null) {
            return element;
        }
        element.setTextContent(valor);
        return element;

    }
    public Element crearElementoAttr(Document doc, Element rootElement, String nombre,String attrName, String attrValue) {
        Element element = doc.createElement(nombre);
        rootElement.appendChild(element);
        Attr attr = doc.createAttribute(attrName);
        attr.setValue(attrValue);
        element.setAttributeNode(attr);
        return element;
    }

    public void aniadirLibroExtra(Libreria libreria){
        if (libreria.getId() == 1) {
            l = new Libro();
            l.setId("BK113");
            libreria.addLibro(l);
        }
    }
    // Eliminar un libro cuando el precio sea menor a 5
    public void deleteBooks(Libreria libreria){
        int i = 0;
        while (i < libreria.sizeLibros()) {
            Libro b = libreria.getLibroIndex(i);
            if(b.getPrecio() < 5.0 ) {
                libreria.removeLibro(b);
            }else
                i++;
        }    
    }
    //Mover un libro de la libreria 2 a la libreria 3
    public void moveBooks(){
        for (Libreria libreria : librerias) {
            if (libreria.getId() == 2 ) {
                l= new Libro();
                l = libreria.getLibroIndex(0);
                libreria.removeLibro(l);
            }
            if (libreria.getId() == 3) {
                libreria.addLibro(l);
            }
        }
    }


    public static void main(String[] args) throws Exception {
        App app = new App();
        app.readXML("libraries.xml");
		app.verLibrerias();
        app.crearXMLModificado();
        
        app.verLibrerias();
    }
}
