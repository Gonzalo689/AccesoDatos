import java.util.LinkedList;
import java.util.Random;

public class FicheroCifrado {
    public static int[] generarArrayAleatorio(int longitud, int min, int max) {
        Random rand = new Random(Long.MAX_VALUE);
        int[] numeros = new int[longitud];
        int numerosAleatorios;
        LinkedList<Integer> numerosFaltan = new LinkedList<>();
        
        for (int i = 0; i < longitud; i++) {
            if(i < min || i > max)
               numeros[i] = rand.nextInt(max - min + 1) + min; 
            else{
                numerosAleatorios = rand.nextInt(max - min + 1) + min;
                if(!numerosFaltan.contains(numerosAleatorios)){
                    numerosFaltan.add(numerosAleatorios);
                    numeros[i] = numerosAleatorios;
                }else
                    i--;
            }
        }
        return numeros;
    }    
    public static void main(String[] args)  {
        int[] numerosAleatorios = generarArrayAleatorio(255, 32, 126); 

        String a = "AHola muy buenaz";
        String codificado = "";
        for (int i = 0; i < a.length(); i++) {
            int ascii = a.charAt(i);
            int asciiAleatorio = numerosAleatorios[ascii];
            codificado += (char) asciiAleatorio;
        }

        System.out.println(codificado);
        
        String descodificado="";
        for (int i = 0; i < codificado.length(); i++) {
            int ascii = codificado.charAt(i);
            for (int j = 32; j <= 126 ; j++) {
                if (numerosAleatorios[j] == ascii) {
                    descodificado += (char) j;
                    break;
                }
            }
        }
        System.out.println(descodificado);
    }
}
