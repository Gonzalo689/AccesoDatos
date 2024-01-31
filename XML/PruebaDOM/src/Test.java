

import java.io.File;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

public class Test 
{
	public static LinkedList<Empleado> empleados = new LinkedList<>();
	public static Empleado p = new Empleado();
	
	public static void showNode(Node node, int level)
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
				//Crear Empleado
				if (node.getParentNode().getNodeName().equals("employees")) {
					p = new Empleado();
					empleados.add(p);
				}
				System.out.print("<" + node.getNodeName());
				NamedNodeMap attributes = node.getAttributes();
				for(int i=0; i<attributes.getLength();i++)
				{
					Node attribute = attributes.item(i);
					System.out.print("@" + attribute.getNodeName() + "[" + attribute.getNodeValue() + "]");
					// cargarDatos
					datosEmpreado(p, attribute.getNodeName(), attribute.getNodeValue());
				}
				
				System.out.println(">");
				break;
			case Node.TEXT_NODE:
				String text = node.getNodeValue();
				if(text.trim().length()>0) {
					System.out.println(node.getNodeName() + "[" + node.getNodeValue() + "]");
					datosEmpreado(p, node.getParentNode().getNodeName() , node.getNodeValue());
				}
				break;
		}
		NodeList childNodes = node.getChildNodes();
		for(int i=0; i<childNodes.getLength(); i++) {
			showNode(childNodes.item(i), level+1);
		}
	}
	public static void datosEmpreado(Empleado p, String datoNombre,String valor){
		switch (datoNombre.trim().toLowerCase()) {
			case "id":
				p.setId(Integer.parseInt(valor));
				break;
			case "firstname":
				p.setNombre(valor);
			break;	
			case "lastname":
				p.setApellidos(valor);
			break;
		}
	}
	
    public static void main( String[] args ) 
    {
    	File file = new File("XMLFile.xml");
    	try {
    		  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    		  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    		  Document doc = dBuilder.parse(file);
    		  showNode(doc,0);
    		  
    		} catch(Exception e) {
    		  e.printStackTrace();
    		}
		
		for (Empleado p : empleados) {
			System.out.println(p);
		}
    }
}