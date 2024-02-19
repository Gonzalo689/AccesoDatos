import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class App2 {
    public static void main(String[] args) throws Exception {
        String json = args[0];
        // Empleado
        Gson gson = new Gson();
        BufferedReader br = new BufferedReader(new FileReader(json));
        Empleado mascota = gson.fromJson(br, Empleado.class);
        System.out.println(mascota);   
        
        //String json = args[0];
        // Lista de personas
        //Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Persona>>(){}.getType();
        ArrayList<Persona> personas = gson.fromJson(new BufferedReader(new FileReader(json)), type);
        for (Persona persona : personas) {
            System.out.println(persona);
        }
        
    }
}
