

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;

import java.lang.reflect.Type;


public class App {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private final String COLLECTION_NAME = "Pokedex";
    private final String DATABASE_NAME = "prueba";

    private List<Pokemon> pokemons;

    public App() {
        System.out.println("Connected successfully to server");
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase(DATABASE_NAME);
    }
    public void close() {
        mongoClient.close();
    }
    

    public void gson() {
        
        FindIterable<Document> iterable = database.getCollection(COLLECTION_NAME).find();
       
        String json = "";
        for (Document doc : iterable) {
            json += doc.toJson() +"," ;
            //System.out.println(json);
        }

        Gson gson = new Gson();
        Type tipoListaPokemon = new TypeToken<ArrayList<Pokemon>>(){}.getType();
        pokemons = gson.fromJson("[" + json + "]", tipoListaPokemon);
        for (Pokemon pokemon : pokemons) {
                System.out.println(pokemon);
        }    
        
    }
    public void ejercicio1() {

        System.out.println("Número total de pokemons.");
        System.out.println(pokemons.size());

    }
    public void ejercicio2() {
        List<String> types = new ArrayList<>();
        System.out.println("Listado de los distintos tipos de pokemon que existen.");
        for (Pokemon pokemon : pokemons) {
            if (pokemon != null) 
                for (String type : pokemon.getType()) {
                    if (!types.contains(type)) {
                        types.add(type);
                    }
                }
        }
        for (String type : types) {
            System.out.print(type + ", ");
        }
        System.out.println("");
    }
    public void ejercicio3() {
        HashMap<String, Integer> types = new HashMap<>();
        System.out.println("El tipo de pokemon que más se repite.");
        for (Pokemon pokemon : pokemons) {
            if (pokemon != null) 
                for (String type : pokemon.getType()) {
                    if (types.containsKey(type)) {
                        types.put(type, types.get(type) + 1);
                    } else {
                        types.put(type, 1);
                    }
                }
        }
        int max = 0;
        String maxType = "";
        for (String type : types.keySet()) {
            if (types.get(type) > max) {
                max = types.get(type);
                maxType = type;
            }
        }
        System.out.println(maxType);
    }
    public void ejercicio4() {
        System.out.println("Las evoluciones del pokemon 133 (Eevee).");
        for (Pokemon pokemon : pokemons) {
            if (pokemon != null ) 
                if (pokemon.getId() == 133) {
                    for (Evolucion pokemon2 : pokemon.getNext_evolution()) {
                        System.out.println(pokemon2);
                    }
                }
        }
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.gson();
        app.ejercicio1();
        app.ejercicio2();
        app.ejercicio3();
        app.ejercicio4();
        app.close();        
    }
}
