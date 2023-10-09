import java.util.Random;

public class Cifrado {
    public static int[] generarArrayAleatorio(int longitud, int min, int max) {
        Random rand = new Random(Long.MAX_VALUE);
        int[] numeros = new int[longitud];

        for (int i = 0; i < longitud; i++) 
            numeros[i] = rand.nextInt(max - min + 1) + min;

        return numeros;
    }
    public static void main(String[] args)  {
        int[] numerosAleatorios = generarArrayAleatorio(255, 32, 126); 
        
        for (int i = 32; i < 126; i++) {
            System.out.print(numerosAleatorios[i] + " ");
        }
        String a = "Hola muy buenas";
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
            for (int j = 32; j < 126 ; j++) {
                if (numerosAleatorios[j] == ascii) {
                    descodificado += (char) j;
                    System.out.print(j);
                    break;
                }
            }
        }
        System.out.println(descodificado);
        
    }

}
