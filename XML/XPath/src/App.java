import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import org.w3c.dom.*;

public class App {

    public Document doc;
    public void parseXML(String fichero) {
        File file = new File(fichero);
    	try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void queryXML(String query) {
        try {
            XPathExpression expr = XPathFactory.newInstance().newXPath().compile(query);
            NodeList result = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            for (int i = 0; i < result.getLength(); i++) {
                System.out.println("Reult: " + result.item(i).getTextContent());
            }
        } catch(Exception e) {
            e.printStackTrace();
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
                System.out.print("<" + node.getNodeName());
				NamedNodeMap attributes = node.getAttributes();
				for(int i=0; i<attributes.getLength();i++)
				{
					Node attribute = attributes.item(i);
					System.out.print("@" + attribute.getNodeName() + "[" + attribute.getNodeValue() + "]");
				}
				System.out.println(">");
				break;
			case Node.TEXT_NODE:
				String text = node.getNodeValue();
				if(text.trim().length()>0) {
					System.out.println(node.getNodeName() + "[" + node.getNodeValue() + "]");
				}
				break;
		}
        NodeList children = node.getChildNodes();
        for(int i=0; i<children.getLength();i++) {
            showNode(children.item(i), level+1);
        }
        
    }
    


    public static void main(String[] args) throws Exception {
        App app = new App();

        app.parseXML("libraries.xml");
        app.queryXML("/libraries/library[address='C/ Falsa, 123']/books/book/@id");

        System.out.println("---------------EJERCICIO 1---------------------");
        app.parseXML("plant_catalog.xml");
        // Obtener el precio de todas las plantas.
        System.out.println("Precios de todas las plantas: ");
        app.queryXML("//PRICE");
        // Obtener el "common" de la ultima planta
        System.out.println("Common de la ultima planta: ");
        app.queryXML("/CATALOG/PLANT[position()=last()]/COMMON");
        // Obtener el precio de las plantas cuyo precio sea mayor a 5 e inferior a 10.
        System.out.println("Precios de las plantas cuyo precio sea mayor a 5 e inferior a 10: ");
        app.queryXML("//PRICE[.>5 and .<10]");
        // Obtener el precio de las plantas cuya zona sea 4
        System.out.println("Precios de las plantas cuya zona sea 4: "); 
        app.queryXML("//PRICE[../ZONE = '4']");

        System.out.println("---------------EJERCICIO 2---------------------");        
        XPathExpression expr = null;
        NodeList result = null;
        
        expr = XPathFactory.newInstance().newXPath().compile("//*");
        result = (NodeList) expr.evaluate(app.doc, XPathConstants.NODESET); // NodeList
    
        for (int i = 0; i < result.getLength(); i++) {
            Node node = result.item(i);
            app.showNode(node, 0);
        }

        System.out.println("---------------EJERCICIO 3---------------------");
        app.parseXML("libraries.xml");
        
        String titulo = "/libraries/library/books/book/title";

        expr = XPathFactory.newInstance().newXPath().compile(titulo);
        Node result2 = (Node) expr.evaluate(app.doc, XPathConstants.NODE); // Node

        System.out.println("El titulo del libro: " + result2.getTextContent());

        expr = XPathFactory.newInstance().newXPath().compile(titulo);
        String result3 = (String) expr.evaluate(app.doc, XPathConstants.STRING); // String 
        System.out.println("El titulo del libro: " + result3);
    }

    
}
