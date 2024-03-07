import java.io.BufferedWriter;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class App {
    private LinkedList<Libro> libros = new LinkedList<>();
	private Libro l = new Libro();
    private BufferedWriter bw = null;

    public void ensenarLibros() {
        System.out.println("\nNumero de libros: " + libros.size());
        System.out.println("Precio medio: " + getPrecioMedio());
        distintosGeneros();
        System.out.println("\nLibros por anios: ");
        librosAnios();

    }
    public double getPrecioMedio() {
        double precioTotal = 0;
        for (Libro l : libros) {
            precioTotal += l.getPrecio();
        }
        return precioTotal / libros.size();
    }
    public void distintosGeneros() {
        List<String> listgeneros = new LinkedList<>();
        for (Libro l : libros) {
            if (!listgeneros.contains(l.getGenero())) {
                listgeneros.add(l.getGenero());
            }
        }
        System.out.println("Tipo de Generos: " );
        for (String generos : listgeneros) {
            System.out.print(generos+",");
        }
    }
    public void librosAnios() {
        HashMap<String, Integer> listanios = new HashMap<>();
        for (Libro l : libros) {
            String[] anio = l.getFechaPubli().split("-");
            if (listanios.containsKey(anio[0])) {
                listanios.put(anio[0], listanios.get(anio[0]) + 1);
            } else {
                listanios.put(anio[0], 1);
            }
        }
        for (String anios : listanios.keySet()) {
            System.out.print(listanios.get(anios) + " del " + anios + " | ");
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
                bw.write("Documento DOM. Versión: " + doc.getXmlVersion() + " . Codificación: " + doc.getXmlEncoding() + "\n");
				break;
			case Node.ELEMENT_NODE:
				//Crear Libro
				if (node.getParentNode().getNodeName().equals("catalog")) {
					l = new Libro();
					libros.add(l);
				}
				System.out.print("<" + node.getNodeName());
                bw.write("<" + node.getNodeName());
				NamedNodeMap attributes = node.getAttributes();
				for(int i=0; i<attributes.getLength();i++)
				{
					Node attribute = attributes.item(i);
					System.out.print("@" + attribute.getNodeName() + "[" + attribute.getNodeValue() + "]");
                    bw.write("@" + attribute.getNodeName() + "[" + attribute.getNodeValue() + "]");
					// cargarDatos
					datosLibro( attribute.getNodeName(), attribute.getNodeValue());
				}
				System.out.println(">");
                bw.write(">\n");
				break;
			case Node.TEXT_NODE:
				String text = node.getNodeValue();
				if(text.trim().length()>0) {
					System.out.println(node.getNodeName() + "[" + node.getNodeValue() + "]");
                    bw.write(node.getNodeName() + "[" + node.getNodeValue() + "]\n");
					datosLibro( node.getParentNode().getNodeName() , node.getNodeValue());
				}
				break;
		}
		NodeList childNodes = node.getChildNodes();
		for(int i=0; i<childNodes.getLength(); i++) {
			showNode(childNodes.item(i), level+1);
		}
	}
    public  void datosLibro(String datoNombre,String valor){
		switch (datoNombre.trim().toLowerCase()) {
			case "id":
				l.setId(valor);
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
				l.setFechaPubli(valor);
			break;
            case "description":
				l.setDescripcion(valor);
			break;
		}
	}

    public void readXML(String fichero) {
        File file = new File(fichero);
    	try {
    		  bw = new BufferedWriter(new java.io.FileWriter("fichero.txt"));
    		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		  Document doc = dBuilder.parse(file);
    		  showNode(doc,0);
    		  bw.close();
    		} catch(Exception e) {
    		  e.printStackTrace();
    		}
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.readXML("books.xml");
        app.ensenarLibros();
    }
}
