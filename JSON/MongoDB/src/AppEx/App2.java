package AppEx;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class App2 {
    MongoClient mongoClient;
    MongoDatabase database;

    public App2() {
        System.out.println("Connected successfully to server");
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("prueba");
    }
    public void close() {
        mongoClient.close();
    }
    public void prueba() {
        System.out.println("Collections in database: ");
        for (String collection : database.listCollectionNames()) {
            System.out.println(collection);
        }
    }

    public void ejercicio1() {
        System.out.println("Seleccionar los 10 primeros pokemon que tengan 2 evoluciones siguientes. Ordenar la salida por nombre del pokemon Ascendente (Z a la A).");
        Bson filter = Filters.size("next_evolution", 2);
        Bson sort = Sorts.descending("name");
        FindIterable<Document> iterable = database.getCollection("Pokedex").find(filter).sort(sort).limit(10);
        for (Document doc : iterable) {
            System.out.println(doc.toJson());
        }
    }
    public void ejercicio2() {
        System.out.println("Seleccionar el segundo pokemon cuyo huevo eclosione a los 2 km.");
        Bson filter =  Filters.eq("egg", "2 km"); 
        FindIterable<Document> iterable = database.getCollection("Pokedex").find(filter).skip(1).limit(1);
        for (Document doc : iterable) {
            System.out.println(doc.toJson());
        }
    }
    public void ejercicio3() {
        System.out.println("Mostrar todos los pokemon cuyo id sea mayor a 50 y menor que 60.");
        Bson filter =  Filters.and(Filters.gt("_id", 50), Filters.lt("_id", 60));
        FindIterable<Document> iterable = database.getCollection("Pokedex").find(filter);
        for (Document doc : iterable) {
            System.out.println(doc.toJson());
        }
    }
    public void ejercicio4() {
        System.out.println("Mostrar todos los pokemon que sean una evolución intermedia (es decir, que tenga evolución previa y evolución siguiente). "+
        "Se deberá mostrar sólo el nombre de ese pokemon, de la evolución previa y de la evolución siguiente.");

        Bson filter =  Filters.and(Filters.exists("prev_evolution", true), Filters.exists("next_evolution", true));
        Bson projection = Projections.fields(Projections.include("name", "prev_evolution.name", "next_evolution.name"), Projections.excludeId()); ;
        FindIterable<Document> iterable = database.getCollection("Pokedex").find(filter).projection(projection);
        for (Document doc : iterable) {
            System.out.println(doc.toJson());
        }
    }
    public void ejercicio5() {
        System.out.println("Mostrar los pokemon que tengan el campo candy_count y que 'Grass' NO esté entre sus debilidades.");

        Bson filter =  Filters.and(Filters.exists("candy_count", true), Filters.nin("weaknesses", "Grass"));
        
        FindIterable<Document> iterable = database.getCollection("Pokedex").find(filter);
        for (Document doc : iterable) {
            System.out.println(doc.toJson());
        }
    }

    public static void main(String[] args) throws Exception {
        App2 app = new App2();
        app.prueba();
        System.out.println("------------------------");
        app.ejercicio1();
        System.out.println("------------------------");
        app.ejercicio2();
        System.out.println("------------------------");
        app.ejercicio3();
        System.out.println("------------------------");
        app.ejercicio4();
        System.out.println("------------------------");
        app.ejercicio5();
        app.close();

        
    }
}
