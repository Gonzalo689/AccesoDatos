import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Main {
    ArrayList<Persona> personas;

    public void crearLista(String json) {
        Gson gson = new Gson();
            Type tipoListaPersona = new TypeToken<ArrayList<Persona>>(){}.getType();
            try {
                personas = gson.fromJson(new BufferedReader(new FileReader(json)), tipoListaPersona);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    public void listadoPersonas() {
        for (Persona persona : personas) {
            System.out.println(persona.getName() + ", ");
        }
    }
    public void personaMasGasto() {
        double max = 0;
        Persona maxPersona = null;
        for (Persona persona : personas) {
            double total = 0;
            for (int i = 0; i < persona.sizeWeek(); i++) {
                total = 0;
                total = persona.getWeek(i).priceWeek();
            }
            if (total > max) {
                max = total;
                maxPersona = persona;
            }
        }
        System.out.println("Persona que mas gasta es " + maxPersona.getName() + "con un total de " + max);
    }

    public void gastoMedio() {
        double total = 0;
        for (Persona persona : personas) {
            for (int i = 0; i < persona.sizeWeek(); i++) {
                total += persona.getWeek(i).priceWeek();
            }
        }
        System.out.println("El gasto medio es de " + total / personas.size());
    }
    
    public void facturaMasCara(){
        double max = 0;
        Persona maxPersona = null;
        String factura = "";
        for (Persona persona : personas) {
            for (int i = 0; i < persona.sizeWeek(); i++) {
                for (int j = 0; j < persona.getWeek(i).sizeExpense(); j++) {
                    double price = persona.getWeek(i).getExpense(j).getPrice();
                    if (price>max) {
                        factura = persona.getWeek(i).getExpense(j).getProduct();
                        max = price;
                        maxPersona = persona;
                    }
                }
            }
        }
        System.out.println("Persona que mas gasta es " + maxPersona.getName() + " con la factura siguiente:  " + factura + " : "  + max);
    }



    public static void main(String[] args) throws Exception {
        try {
            String jsonFile = args[0];
            Main app = new Main(); 
            app.crearLista(jsonFile);   
            app.listadoPersonas();
            app.personaMasGasto();
            app.gastoMedio();
            app.facturaMasCara();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
    }
}
