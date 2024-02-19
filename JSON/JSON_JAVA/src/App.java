import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class App extends DefaultHandler {
    private BufferedWriter bw;
    private List<Library> libraries;
    private Library library;
    private Book book ;
    private String dato;

    public App () {
        libraries = new LinkedList<>();
        book = new Book();
    }

    @Override
    public void startDocument(){
       
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
        if (qName.trim().equalsIgnoreCase("book")) {
            book = new Book();
            book.setId(attributes.getValue("id"));
        }
        if (qName.trim().equalsIgnoreCase("library")) {
            library = new Library();
            library.setId(Integer.parseInt(attributes.getValue("id")));
        }
        
        dato= qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String text3 = "";
        String text = new String(ch, start, length);
        if(text.trim().length()>0){
            
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
    public void converToJson(String file_name){
        try {
            bw = new BufferedWriter(new FileWriter(file_name));
        
            bw.write("{");
            writeTabs(1);
            bw.write("\"libraries\": {");
            writeTabs(2);
            bw.write("\"library\": [");
            
            
            for (int j = 0; j < libraries.size(); j++) {
                
               
                writeTabs(3);
                bw.write("{");
                writeTabs(4);
                bw.write("\"id\" : " + libraries.get(j).getId() + "," ); 
                writeTabs(4);   
                bw.write(" \"address\" : \"" + libraries.get(j).getAddress() + "\"");
                if (libraries.get(j).sizebooks() > 0) {
                    bw.write(",");
                    writeTabs(4);
                    bw.write("\"books\" : [");
                }

                for (int i = 0; i < libraries.get(j).sizebooks(); i++) {
                    writeTabs(5);
                    bw.write(" {");
                    writeTabs(5);
                    bw.write(" \"id\" : \"" + libraries.get(j).getbook(i).getId() + "\"," );    
                    writeTabs(5);
                    bw.write("\"author\" : \"" + libraries.get(j).getbook(i).getAuthor() + "\",");
                    writeTabs(5);
                    bw.write("\"title\" : \"" + libraries.get(j).getbook(i).getTitle() + "\",");
                    writeTabs(5);
                    bw.write("\"genre\" : \"" + libraries.get(j).getbook(i).getGenre() + "\",");
                    writeTabs(5);
                    bw.write("\"price\" : " + libraries.get(j).getbook(i).getPrice()+ ",");
                    writeTabs(5);
                    bw.write(" \"publish_date\" : \"" + libraries.get(j).getbook(i).getPublish_date() + "\",");
                    writeTabs(5);
                    bw.write(" \"description\" : \"" + libraries.get(j).getbook(i).getDescription() + "\"");
                    if (i < libraries.get(j).sizebooks() - 1) {
                        writeTabs(5);
                        bw.write(" },");

                    }else{
                        writeTabs(5);
                        bw.write(" }");
                        writeTabs(5);
                        bw.write("]");
                    }
                    
                }   
                if (j < libraries.size() - 1) {
                    writeTabs(4);
                    bw.write("},");
                }
                else {
                    writeTabs(4);
                    bw.write("}");
                }
            
            }
            writeTabs(3);
            bw.write("]");
            writeTabs(2);
            bw.write("}");
            writeTabs(1);
            bw.write("}");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeTabs(int tabs) throws IOException {
        bw.write("\n");
        for (int i = 0; i < tabs; i++) {
            bw.write("\t");
        }
    }


    public static void main(String[] args) {
        App app = new App(); 
        try{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(args[0], app);
        app.converToJson(args[1]);
        
       } catch(Exception e){
            e.printStackTrace();
       }
    }
}
