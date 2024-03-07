package AppEx;


import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;


public class App3 {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private final String COLLECTION_NAME = "Pokedex";
    public App3() {
        System.out.println("Connected successfully to server");
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("prueba");
    }
    public void close() {
        mongoClient.close();
    }
 
    public void ejercicio1() {
        System.out.println("Seleccionar todos los podemos de tipo veneno (Poison).");
        Bson filter = Filters.eq("type", "Poison");
        FindIterable<Document> iterable = database.getCollection(COLLECTION_NAME).find(filter);
        for (Document doc : iterable) {
            System.out.println(doc.toJson());
        }
    }
    public void ejercicio2() {
        System.out.println("Seleccionar 1 pokemon cuyo huevo eclosione a los 2 km.");
        Bson filter =  Filters.eq("egg", "2 km"); 
        FindIterable<Document> iterable = database.getCollection(COLLECTION_NAME).find(filter).limit(1);
        for (Document doc : iterable) {
            System.out.println(doc.toJson());
        }
    }
    public void ejercicio3() {
        System.out.println("Mostrar todos los pokemon de tipo Fuego cuyo id sea mayor que 70. Mostrar sólo nombre, id y tipo.");
        Bson filter =  Filters.and(Filters.gt("_id", 70), Filters.eq("type", "Fire"));
        Bson projection = Projections.fields(Projections.include("name", "_id", "type"));
        FindIterable<Document> iterable = database.getCollection(COLLECTION_NAME).find(filter).projection(projection);
        for (Document doc : iterable) {
            System.out.println(doc.toJson());
        }
    }
    public void ejercicio4() {
        System.out.println("Mostrar los pokemon cuyas debilidades contengan Poison, Ice o ambas.");

        Bson filter =  Filters.or(Filters.in("weaknesses", "Poison", "Ice"));
        FindIterable<Document> iterable = database.getCollection(COLLECTION_NAME).find(filter);
        for (Document doc : iterable) {
            System.out.println(doc.toJson());
        }
    }
    public void ejercicio5() {
        System.out.println("Actualizar los pokemon de tipo Poison para añadir un campo nuevo llamado: esPoison: true.");

        Bson filter =  Filters.eq("type", "Poison");
        Bson update = Updates.set("esPoison", true);
        database.getCollection(COLLECTION_NAME).updateMany(filter, update);
    }
    public void ejercicio6() {
        System.out.println("Eliminar todos los pokemon que no tengan huevo.");

        Bson filter =  Filters.eq("egg", "Not in Eggs");
        database.getCollection(COLLECTION_NAME).deleteMany(filter);
        
    }
    public void metodoTest(){
        System.out.println("Metodo Test");
        // Crear la colección test
        database.createCollection("test");

        // Listar las colecciones
        System.out.println("Collections : ");
        for (String collection : database.listCollectionNames()) {
            System.out.println(collection);
        }

        // Eliminar la colección test
        database.getCollection("test").drop();
        
        // Listar las colecciones
        System.out.println("Collections : ");
        for (String collection : database.listCollectionNames()) {
            System.out.println(collection);
        }
    }

    public static void main(String[] args) throws Exception {
        App3 app = new App3();

        app.ejercicio1();
        System.out.println("------------------------");
        app.ejercicio2();
        System.out.println("------------------------");
        app.ejercicio3();
        System.out.println("------------------------");
        app.ejercicio4();
        System.out.println("------------------------");
        app.ejercicio5();
        app.ejercicio6();
        System.out.println("------------------------");
        app.metodoTest();
        app.close();

    }
}
