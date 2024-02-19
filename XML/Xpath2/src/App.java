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

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.parseXML("books.xml");
        //Selecciona la categoría de los libros que tengan 3 o más autores
        app.queryXML("/bookstore/book[count(author) >= 3]/@category");
        System.out.println("---------------------------");
        // Selecciona el título de los libros cuyo precio sea mayor a 30 y cuya categoría sea web
        app.queryXML("/bookstore/book[price > 30 and @category = 'web']/title");
        System.out.println("---------------------------");
        // Selecciona el último autor del libro cuyo título sea 'XQuery Kick Start'
        app.queryXML("/bookstore/book[title = 'XQuery Kick Start']/author[last()] ");
        System.out.println("---------------------------");
        // Selecciona el lenguaje de los libros cuya categoría sea distinta de 'web'
        app.queryXML("/bookstore/book[@category != 'web']/title/@lang");
        System.out.println("---------------------------");
        // Selecciona el año de los libros que tengan cubierta (cover)
        app.queryXML("/bookstore/book[@cover]/year");
        System.out.println("---------------------------");

    }
}
